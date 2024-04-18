package bricker.brick_strategies;

import danogl.GameManager;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.util.Counter;
import java.util.Random;

/**
 * Factory that handles the brick strategies.
 */
public class StrategiesFactory {
    private static GameObjectCollection gameObjectCollection;
    private final GameManager gameManager;
    private final ImageReader imageReader;
    private final UserInputListener inputListener;
    private final WindowController windowController;
    private final SoundReader soundReader;
    private final Counter livesCounter;
    private final Counter paddleCounter;

    /**
     * Constructor for the factory.
     * @param gameManager The current game manager.
     * @param gameObjectCollection The game's gameObjectCollection.
     * @param imageReader The game's image reader.
     * @param inputListener The game's input listener.
     * @param windowController The game's window controller.
     * @param soundReader The game's sound reader.
     * @param livesCounter The game's lives counter.
     * @param paddleCounter The game's paddle counter.
     */
    public StrategiesFactory(GameManager gameManager, GameObjectCollection gameObjectCollection,
                             ImageReader imageReader, UserInputListener inputListener,
                             WindowController windowController, SoundReader soundReader,
                             Counter livesCounter, Counter paddleCounter){

        StrategiesFactory.gameObjectCollection = gameObjectCollection;
        this.gameManager = gameManager;
        this.imageReader = imageReader;
        this.inputListener = inputListener;
        this.windowController = windowController;
        this.soundReader = soundReader;
        this.livesCounter = livesCounter;
        this.paddleCounter = paddleCounter;
    }

    /**
     * Generates a collision strategy in a probabilistic way.
     * @return The generated collision strategy.
     */
    public CollisionStrategy build(){
        Random random = new Random();

        // setting the strategies.
        CollisionStrategy basic = new BasicCollisionStrategy(gameObjectCollection);
        CollisionStrategy extraHeart = new ExtraHeartStrategy(gameObjectCollection,
                imageReader, livesCounter);
        CollisionStrategy extraPaddle = new ExtraPaddleStrategy(gameObjectCollection, imageReader,
                windowController, inputListener, paddleCounter);
        CollisionStrategy extraBall = new ExtraBallStrategy(gameObjectCollection, imageReader,
                soundReader);
        CollisionStrategy cameraChange = new ChangeCameraStrategy(gameObjectCollection,
                windowController, gameManager);

        // basic double strategy with all the potential special strategies.
        CollisionStrategy doubleStrategy = new DoubleStrategy(new CollisionStrategy[]
                {extraHeart, extraPaddle, extraBall}); // cameraChange

        // making an array so that the double strategy could contain it-self.
        CollisionStrategy[] specialStrategies = {extraHeart, extraPaddle, extraBall, doubleStrategy};

        CollisionStrategy[] strategies = {
                extraHeart,
                extraPaddle,
                extraBall,
                cameraChange,
                new DoubleStrategy(specialStrategies)
        };

        if (random.nextBoolean()){
            return strategies[random.nextInt(strategies.length)];
        }
        return basic;
    }
}
