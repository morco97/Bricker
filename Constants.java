package bricker;

/**
 * Class for the constants of the game.
 */
public class Constants {
    /**
     * Default constructor for the constants class.
     */
    public Constants(){}

    /**
     * The size of the ball.
     */
    public final static int BALL_SIZE = 20;

    /**
     * The width of the games window.
     */
    public final static int WINDOW_WIDTH = 900;

    /**
     * The length of the games window.
     */
    public final static int WINDOW_LENGTH = 600;

    /**
     * The thickness of the games walls.
     */
    public final static int WALL_THICKNESS = 15;

    /**
     * The width of the games paddle.
     */
    public final static int PADDLE_WIDTH = 100;

    /**
     * The height of the games paddle.
     */
    public final static int PADDLE_HEIGHT = 15;

    /**
     * The height of the games brick.
     */
    public final static float BRICK_HEIGHT = 15;

    /**
     * The size of the heart on the screen.
     */
    public static final float HEART_SIZE = 35;

    /**
     * Extra space between bricks.
     */
    public static final float BRICK_SPACER = 1.5f;

    /**
     * The height of the paddle on the screen.
     */
    public final static int PADDLE_DISTANCE_FROM_END = 30;

    /**
     * The default amount of lines that contain bricks.
     */
    public final static int DEFAULT_AMOUNT_OF_BRICK_LINES = 7;

    /**
     * The default amount of bricks in line.
     */
    public final static int DEFAULT_AMOUNT_OF_BRICKS_PER_LINE = 8;

    /**
     * The distance between each brick.
     */
    public final static int BUFFER_BETWEEN_BRICKS = 10;

    /**
     * The distance between each heart image.
     */
    public static final int HEART_BUFFER_SIZE = 15;

    /**
     * Extra space between the walls and the bricks.
     */
    public static final int WALL_SPACER = 1;

    /**
     * Represents diagonal direction change.
     */
    public final static int DIAGONAL_DIRECTION = -1;

    /**
     * Represents the middle of the screen.
     */
    public final static float SCREEN_CENTER = 0.5f;

    /**
     * The ratio between the size of the main ball and the size of the packs.
     */
    public static final float PACK_TO_BALL_SIZE_RATIO = 0.75f;

    /**
     * The size of the numeric life display.
     */
    public static final float NUMERIC_LIFE_SIZE = 50;

    // ------------------------------GAME-VARIABLES------------------------------------------
    /**
     * The allowed number of paddles in a single moment.
     */
    public static final int ALLOWED_NUMBER_OF_PADDLES = 2;
    /**
     * The default number of lives.
     */
    public final static int DEFAULT_NUMBER_OF_LIVES = 3;

    /**
     * The speed of the ball.
     */
    public final static int BALL_SPEED = 300;  // 200;

    /**
     * The maximal number of lives a user can have in a specific moment.
     */
    public static final int MAXIMAL_NUMBER_LIVES = 4;

    /**
     * The amount of collision the extra paddle have before it disappears.
     */
    public static final double EXTRA_PADDLE_COLLISION_LIMIT = 4;

    /**
     * The speed of the paddle
     */
    public static final float PADDLE_MOVEMENT_SPEED = 550; // 300;

    /**
     * After this many collisions, calibrate the camera.
     */
    public static final int NUMBER_OF_COLLISIONS_TO_CALIBRATE_CAMERA = 4;

    //-------------------------PROMPTS----------------------------------------
    /**
     * Prompt that appears if the user lost the game.
     */
    public final static String GAME_OVER_PROMPT = "You lose! Play Again?";

    /**
     * Prompt that appears if the user won in the game.
     */
    public static final String WON_PROMPT = "You win! Play again?";

    // -----------------------PATHS-----------------------------------------------
    /**
     * Path to the ball's sound.
     */
    public final static String BALL_SOUND_PATH = "assets/blop_cut_silenced.wav";

    /**
     * Path to the balls image.
     */
    public final static String BALL_IMAGE_PATH =  "assets/ball.png";

    /**
     * Path to the paddles image.
     */
    public final static String PADDLE_IMAGE_PATH = "assets/paddle.png";

    /**
     * Path to the background image.
     */
    public final static String BACKGROUND_IMAGE_PATH = "assets/DARK_BG2_small.jpeg"; // "assets/daniel.jpeg";

    /**
     * Path to the brick image.
     */
    public final static String BRICK_IMAGE_PATH = "assets/brick.png";

    /**
     * Path to the heart image.
     */
    public static final String LIFE_IMAGE_PATH = "assets/heart.png";

    /**
     * Path to the packs image.
     */
    public static final String MOCK_BALL_IMAGE_PATH = "assets/mockBall.png";

    //----------------------------------TAGS------------------------------------------
    /**
     * Tag for the main ball.
     */
    public static final String MAIN_BALL_TAG = "main ball";

    /**
     * Tag for the main paddle.
     */
    public static final String MAIN_PADDLE_TAG = "main paddle";

    /**
     * Tag for the extra packs.
     */
    public static final String PACK_BALL_TAG = "pack ball tag";

    /**
     * Tag for the falling heart image.
     */
    public static final String FALLING_HEART_TAG = "falling heart";

    /**
     * Tag for the basic strategy.
     */
    public static final String BASIC_STRATEGY_TAG = "basic strategy";

    /**
     * Tag for the extra ball strategy.
     */
    public static final String EXTRA_BALL_STRATEGY_TAG = "extra ball strategy";

    /**
     * Tag for the double strategy.
     */
    public static final String DOUBLE_STRATEGY_TAG = "double strategy";

    /**
     * Tag for the extra paddle strategy.
     */
    public static final String EXTRA_PADDLE_STRATEGY_TAG = "extra paddle strategy";

    /**
     * Tag for the extra heart strategy.
     */
    public static final String EXTRA_HEART_STRATEGY_TAG = "extra heart strategy";

    /**
     * Tag for the change in camera strategy.
     */
    public static final String CHANGE_CAMERA_STRATEGY_TAG = "change camera strategy";
}
