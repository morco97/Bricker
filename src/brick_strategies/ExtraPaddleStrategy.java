package bricker.brick_strategies;

import bricker.Constants;
import bricker.gameobjects.Paddle;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * Class of the strategy that generates extra paddle.
 */
public class ExtraPaddleStrategy implements CollisionStrategy{
    private Paddle paddle;
    private final GameObjectCollection gameObjectCollection;
    private final ImageReader imageReader;
    private final WindowController windowController;
    private final UserInputListener inputListener;
    private final Counter paddleCounter;

    /**
     * Constructor for the extra paddle strategy.
     * @param gameObjectCollection The game's gameObjectCollection.
     * @param imageReader The game's image reader.
     * @param windowController The game's window controller.
     * @param inputListener The game's input listener.
     * @param paddleCounter The game's paddle counter
     */
    public ExtraPaddleStrategy(GameObjectCollection gameObjectCollection, ImageReader imageReader,
                               WindowController windowController,
                               UserInputListener inputListener, Counter paddleCounter) {
        this.gameObjectCollection = gameObjectCollection;
        this.imageReader = imageReader;
        this.windowController = windowController;
        this.inputListener = inputListener;
        this.paddleCounter = paddleCounter;
    }

    /**
     * Sets the behavior when colliding.
     * @param objectOne The object that had instance of the class.
     * @param objectTwo The other object in the collision.
     */
    @Override
    public void onCollision(GameObject objectOne, GameObject objectTwo) {
        createPaddle();
        if(paddle.getPaddleCounter().value() > Constants.ALLOWED_NUMBER_OF_PADDLES){
            gameObjectCollection.removeGameObject(objectOne, Layer.DEFAULT);
            paddle.getPaddleCounter().decrement();
        }
        else{
            gameObjectCollection.addGameObject(paddle, Layer.DEFAULT);
            gameObjectCollection.removeGameObject(objectOne, Layer.DEFAULT);
        }
    }

    /**
     * Getter for the strategy tag.
     * @return String representing the tag of the strategy.
     */
    @Override
    public String getStrategyTag() {
        return Constants.EXTRA_PADDLE_STRATEGY_TAG;
    }

    /**
     * Creating another paddle.
     */
    private void createPaddle(){
        Vector2 windowDimensions = windowController.getWindowDimensions();
        Renderable paddleRenderable = imageReader.readImage(Constants.PADDLE_IMAGE_PATH, true);

        this.paddle = new Paddle(new Vector2(
                windowDimensions.x() / 2, windowDimensions.y() / 2),
                new Vector2(Constants.PADDLE_WIDTH, Constants.PADDLE_HEIGHT), paddleRenderable,
                inputListener, windowDimensions, Constants.EXTRA_PADDLE_COLLISION_LIMIT,
                gameObjectCollection, paddleCounter);
    }
}