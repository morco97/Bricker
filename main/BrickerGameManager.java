package bricker.main;

import bricker.Constants;
import bricker.brick_strategies.*;
import bricker.gameobjects.*;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

/**
 * A class that manages the game bricker.
 */
public class BrickerGameManager extends GameManager {
    private Ball ball;
    private Vector2 windowDimensions;
    private WindowController windowController;
    private final int rowsWithBricks;
    private final int bricksPerRow;
    private Counter livesCounter;
    private Counter previousLives;
    private GameObject numericLives;
    private Counter totalBricksCounter;
    private Counter paddleCounter;
    private ImageReader imageReader;
    private SoundReader soundReader;
    private UserInputListener inputListener;
    private final GameObject[] heartsArray;

    /**
     * Constructor.
     * @param windowTitle The title of the window.
     * @param windowDimensions The dimensions of the window.
     */
    BrickerGameManager(String windowTitle,
                       Vector2 windowDimensions){
        super(windowTitle, windowDimensions);
        this.rowsWithBricks = Constants.DEFAULT_AMOUNT_OF_BRICK_LINES;
        this.bricksPerRow = Constants.DEFAULT_AMOUNT_OF_BRICKS_PER_LINE;
        this.livesCounter = new Counter(Constants.DEFAULT_NUMBER_OF_LIVES);
        this.previousLives = new Counter(Constants.DEFAULT_NUMBER_OF_LIVES);
        this.totalBricksCounter = new Counter(rowsWithBricks*bricksPerRow);
        this.paddleCounter = new Counter(0);
        this.heartsArray = new GameObject[Constants.MAXIMAL_NUMBER_LIVES];
    }

    /**
     * Constructor.
     * @param windowTitle The title of the window.
     * @param windowDimensions The dimensions of the window.
     * @param numberOfBrickLines The number of brick lines.
     * @param numberOfBricksPerLine The number of bricks per line.
     */
    BrickerGameManager(String windowTitle,
                       Vector2 windowDimensions,
                       int numberOfBrickLines,
                       int numberOfBricksPerLine) {
        super(windowTitle, windowDimensions);
        this.rowsWithBricks = numberOfBrickLines;
        this.bricksPerRow = numberOfBricksPerLine;
        this.livesCounter = new Counter(Constants.DEFAULT_NUMBER_OF_LIVES);
        this.previousLives = new Counter(Constants.DEFAULT_NUMBER_OF_LIVES);
        this.totalBricksCounter = new Counter(rowsWithBricks*bricksPerRow);
        this.paddleCounter = new Counter(0);
        this.heartsArray = new GameObject[Constants.MAXIMAL_NUMBER_LIVES];
    }

    /**
     * Initializing a game.
     * @param imageReader Contains a single method: readImage, which reads an image from disk.
     *                 See its documentation for help.
     * @param soundReader Contains a single method: readSound, which reads a wav file from
     *                    disk. See its documentation for help.
     * @param inputListener Contains a single method: isKeyPressed, which returns whether
     *                      a given key is currently pressed by the user or not. See its
     *                      documentation.
     * @param windowController Contains an array of helpful, self-explanatory methods
     *                         concerning the window.
     */
    @Override
    public void initializeGame(ImageReader imageReader,
                               SoundReader soundReader,
                               UserInputListener inputListener,
                               WindowController windowController) {
        super.initializeGame(imageReader, soundReader,
                inputListener, windowController);

        this.livesCounter = new Counter(Constants.DEFAULT_NUMBER_OF_LIVES);
        this.previousLives = new Counter(Constants.DEFAULT_NUMBER_OF_LIVES);
        this.totalBricksCounter = new Counter(rowsWithBricks*bricksPerRow);
        this.windowController = windowController;
        this.windowDimensions = windowController.getWindowDimensions();
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.inputListener = inputListener;
        this.paddleCounter = new Counter(0);

        addBall();
        ball.setTag(Constants.MAIN_BALL_TAG);
        addPaddle();
        addWalls();
        addBackground();
        addBricks();
        createHearts(imageReader);
        displayHearts();
        this.numericLives = createNumericLives();
        gameObjects().addGameObject(numericLives, Layer.BACKGROUND);
    }

    /**
     *
     * @param deltaTime The time, in seconds, that passed since the last invocation
     *                  of this method (i.e., since the last frame). This is useful
     *                  for either accumulating the total time that passed since some
     *                  event, or for physics integration (i.e., multiply this by
     *                  the acceleration to get an estimate of the added velocity or
     *                  by the velocity to get an estimate of the difference in position).
     *                  additionally it is checking for the end of the game.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        checkForBallDropped();
        checkWinLose();
        updateNumericLivesDisplay();
        checkForChangeInLifeValue();
        checkIfPressedW();
        calibrateCamera();
    }

    /**
     * The main method, runs the game.
     * @param args Standard way of writing.
     */
    public static void main(String[] args){
        if (args.length == 2){
            new BrickerGameManager("Bricker",
                    new Vector2(Constants.WINDOW_WIDTH, Constants.WINDOW_LENGTH),
                    Integer.parseInt(args[0]),
                    Integer.parseInt(args[1])).run();
        }
        else{
            new BrickerGameManager("Bricker",
                    new Vector2(Constants.WINDOW_WIDTH, Constants.WINDOW_LENGTH)).run();
        }
    }

    /**
     * Adding background image to the game.
     */
    private void addBackground(){
        Renderable backgroundRenderable = imageReader.readImage(
                Constants.BACKGROUND_IMAGE_PATH,
                true);
        GameObject background = new GameObject(Vector2.ZERO,
                windowDimensions,
                backgroundRenderable);
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects().addGameObject(background, Layer.BACKGROUND);
    }

    /**
     * Adding the walls of the game.
     */
    private void addWalls(){
        Vector2[] wallStartingPositions = {Vector2.LEFT, Vector2.ZERO,
                new Vector2(windowDimensions.x() - Constants.WALL_THICKNESS, 0)};
        Vector2[] wallDimension = {new Vector2(windowDimensions.x(), Constants.WALL_THICKNESS),
                new Vector2(Constants.WALL_THICKNESS, windowDimensions.y()),
                new Vector2(Constants.WALL_THICKNESS, windowDimensions.y())};
        for (int i = 0; i < wallDimension.length; i++) {
            GameObject wall = new GameObject(wallStartingPositions[i], wallDimension[i], null);
            gameObjects().addGameObject(wall, Layer.STATIC_OBJECTS);
        }
    }

    /**
     * Adding a new ball to the screen.
     */
    private void addBall(){

        Renderable ballRenderer = imageReader.readImage(Constants.BALL_IMAGE_PATH,
                true);
        Sound collitionSound = soundReader.readSound(
                Constants.BALL_SOUND_PATH);
        Ball ball = new Ball(Vector2.ZERO, new Vector2(Constants.BALL_SIZE, Constants.BALL_SIZE),
                            ballRenderer, collitionSound);
        ball.setCenter(windowDimensions.mult(Constants.SCREEN_CENTER));
        setRandomVelocity(ball);
        gameObjects().addGameObject(ball);
        this.ball = ball;
    }

    /**
     * Assigns a ball object a random velocity.
     * @param object A GameObject instance.
     */
    private void setRandomVelocity(GameObject object) {
        float ballVelX = Constants.BALL_SPEED;
        float ballVelY = Constants.BALL_SPEED;
        Random rand = new Random();
        if (rand.nextBoolean())
            ballVelX *= Constants.DIAGONAL_DIRECTION;
        if (rand.nextBoolean())
            ballVelY *= Constants.DIAGONAL_DIRECTION;
        object.setVelocity(new Vector2(ballVelX, ballVelY));
    }

    /**
     * Adding a new paddle to the game.
     */
    private void addPaddle(){
        Renderable paddleImage = imageReader.readImage(Constants.PADDLE_IMAGE_PATH,
                true);
        GameObject userPaddle = new Paddle(
                Vector2.ZERO,
                new Vector2(Constants.PADDLE_WIDTH, Constants.PADDLE_HEIGHT),
                paddleImage,
                inputListener,
                windowDimensions, Double.POSITIVE_INFINITY, gameObjects(), paddleCounter);
        userPaddle.setCenter(new Vector2(windowDimensions.x() / 2,
                windowDimensions.y() - Constants.PADDLE_DISTANCE_FROM_END));
        userPaddle.setTag(Constants.MAIN_PADDLE_TAG);
        gameObjects().addGameObject(userPaddle, Layer.DEFAULT);
    }

    /**
     * Loading the game with all bricks.
     */
    private void addBricks() {
        StrategiesFactory strategiesFactory = new StrategiesFactory(this, gameObjects(), imageReader,
                inputListener, windowController, soundReader, livesCounter, paddleCounter);
        Renderable brickImage = imageReader.readImage(Constants.BRICK_IMAGE_PATH, true);
        int brickWidth = (int) ((windowDimensions.x() -
                2* ( Constants.WALL_THICKNESS + Constants.BUFFER_BETWEEN_BRICKS)) /
                                (bricksPerRow + Constants.BRICK_SPACER));
        float totalBrickWidth = brickWidth * bricksPerRow;
        float totalMarginWidth = windowDimensions.x() - totalBrickWidth;
        float marginX = totalMarginWidth / (bricksPerRow + Constants.WALL_SPACER);
        float marginY = Constants.BUFFER_BETWEEN_BRICKS;
        for(int i = 0; i<rowsWithBricks; i++){
            for(int j =0; j<bricksPerRow;j++){

                CollisionStrategy collisionStrategy = strategiesFactory.build();

                float xPosition = (marginX + j * (brickWidth + marginX));
                float yPosition =Constants.WALL_THICKNESS + (marginY + i *
                                                            (Constants.BRICK_HEIGHT + marginY));
                Vector2 brickPosition = new Vector2(xPosition, yPosition);

                GameObject brick = new Brick(brickPosition, new Vector2(brickWidth, Constants.BRICK_HEIGHT),
                        brickImage, collisionStrategy, totalBricksCounter);
                gameObjects().addGameObject(brick, Layer.DEFAULT);
            }
        }
    }

    /**
     * Function to allow the user to interact with the dialog.
     * @param prompt The prompt that is presented to the user.
     */
    private void displayDialogAndActivate(String prompt) {
        if (windowController.openYesNoDialog(prompt)) {
            windowController.resetGame();
        }
        else {
            windowController.closeWindow();
        }
    }

    /**
     * Checking if the user won or lost.
     */
    private void checkWinLose() {
        if (livesCounter.value() == 0) {
            displayDialogAndActivate(Constants.GAME_OVER_PROMPT);
        }
        else if (totalBricksCounter.value() == 0) {
            displayDialogAndActivate(Constants.WON_PROMPT);
        }
    }

    /**
     * Checking if the ball is out of the screen.
     */
    private void checkForBallDropped() {
        float ballHeight = ball.getCenter().y();
        if (ballHeight > windowDimensions.y()){
            livesCounter.decrement();
            updateNumericLivesDisplay();

            ball.setCenter(windowDimensions.mult(Constants.SCREEN_CENTER));
            setRandomVelocity(ball);
        }
    }

    /**
     * If w is pressed finish the game at win.
     */
    private void checkIfPressedW() {
        if (inputListener.isKeyPressed(KeyEvent.VK_W)){
            displayDialogAndActivate(Constants.WON_PROMPT);
        }
    }

    /**
     * Creating an array of hearts and placing it on the screen.
     * @param imageReader the game's image reader.
     */
     private void createHearts(ImageReader imageReader) {
         Renderable heartImageRenderable = imageReader.readImage(Constants.LIFE_IMAGE_PATH, true);
        for (int i = 0; i < Constants.MAXIMAL_NUMBER_LIVES; i++) {
            GameObject heart = new GameObject(new Vector2(windowDimensions.x()
                                - Constants.HEART_SIZE, windowDimensions.y() - Constants.HEART_SIZE).add(
                                new Vector2(- i *(Constants.HEART_BUFFER_SIZE + Constants.HEART_SIZE),
                                        0)),
                    new Vector2(Constants.HEART_SIZE, Constants.HEART_SIZE), heartImageRenderable);
            heartsArray[i] = heart;
        }
    }

    /**
     * checking for increase or decrease in life and updating the screen accordingly.
     */
    private void checkForChangeInLifeValue() {
         // decrease in life
         if (previousLives.value() > livesCounter.value()){
             gameObjects().removeGameObject(heartsArray[previousLives.value() - 1], Layer.BACKGROUND);
             previousLives.decrement();
         }
         // increase in life
         else if (previousLives.value() < livesCounter.value()){
             int start = previousLives.value();
             for (int i = start; i <livesCounter.value() ; i++) {
                gameObjects().addGameObject(heartsArray[i], Layer.BACKGROUND);
                previousLives.increment();
             }
         }
    }

    /**
     * Updating the numeric display according to the remaining number of lives.
     */
    private void updateNumericLivesDisplay() {
        gameObjects().removeGameObject(numericLives, Layer.BACKGROUND);
        numericLives = createNumericLives();
        gameObjects().addGameObject(numericLives, Layer.BACKGROUND);
    }

    /**
     * Display the initialized hearts on the screen.
     */
    private void displayHearts(){
        for (int i = 0; i < livesCounter.value(); i++) {
            gameObjects().addGameObject(heartsArray[i], Layer.BACKGROUND);
        }
    }

    /**
     * Creating the display for the numeric life.
     * @return The numeric life object.
     */
    private GameObject createNumericLives(){
        TextRenderable numericRenderer = new TextRenderable(Integer.toString(livesCounter.value()));
        if(livesCounter.value() >= 3){
            numericRenderer.setColor(Color.GREEN);
        }
        else if (livesCounter.value() == 2){
            numericRenderer.setColor(Color.YELLOW);
        }
        else{
            numericRenderer.setColor(Color.RED);
        }
        return new GameObject(new Vector2(
                windowDimensions.x() - (Constants.MAXIMAL_NUMBER_LIVES * (
                        Constants.HEART_SIZE + Constants.HEART_BUFFER_SIZE) + Constants.HEART_BUFFER_SIZE),
                windowDimensions.y() - Constants.NUMERIC_LIFE_SIZE), new Vector2(
                Constants.NUMERIC_LIFE_SIZE,Constants.NUMERIC_LIFE_SIZE),
                numericRenderer);
    }

    /**
     * Calibrating the game's camera after a number of collisions.
     */
    private void calibrateCamera(){
        if (ball.getCollisionCounter() >= Constants.NUMBER_OF_COLLISIONS_TO_CALIBRATE_CAMERA){
            setCamera(null);
        }
    }
}
