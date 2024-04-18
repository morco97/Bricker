package bricker.brick_strategies;

import bricker.Constants;
import bricker.gameobjects.Ball;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.WindowController;
import danogl.gui.rendering.Camera;
import danogl.util.Vector2;
import java.util.Objects;

/**
 * The ChangeCameraStrategy class is designed to manage camera behavior in response to
 * specific collision events within the game. It implements the CollisionStrategy interface,
 * indicating its use in handling collisions. This class is responsible for dynamically adjusting
 * the game camera based on interactions between game objects, specifically focusing on events
 * involving the main ball object.
 */
public class ChangeCameraStrategy implements CollisionStrategy {
    WindowController windowController;
    private final GameObjectCollection gameObjectCollection;
    private final GameManager gameManager;

    /**
     * Constructor for ChangeCameraStrategy.
     * Initializes a new instance of the ChangeCameraStrategy class,
     * setting up the necessary components for changing the camera based on collisions.
     *
     * @param gameObjectCollection The collection of game objects to be managed.
     * @param windowController Controls the window, used to get dimensions for camera settings.
     * @param gameManager Manages game state, including camera settings.
     */
    public ChangeCameraStrategy(GameObjectCollection gameObjectCollection,
                                WindowController windowController, GameManager gameManager) {
        this.gameObjectCollection = gameObjectCollection;
        this.windowController = windowController;
        this.gameManager = gameManager;
    }

    /**
     * Sets the camera to focus on a specific game object.
     * This method is called to update the game camera, focusing on the provided game object.
     *
     * @param gameObject The game object for the camera to focus on.
     */
    private void setCamera(GameObject gameObject) {
        if (gameManager.camera() == null) {
            gameManager.setCamera(new Camera(gameObject, Vector2.ZERO,
                    windowController.getWindowDimensions().mult(1.2f),
                    windowController.getWindowDimensions()));
        }
    }

    /**
     * Handles collision events between game objects.
     * This method is triggered upon a collision, removing the colliding object from the game,
     * setting the camera to focus on the main ball if it's involved in the collision,
     * and resetting the camera if the main ball's collision count exceeds 4.
     * @param object1 The first object involved in the collision.
     * @param object2 The second object involved in the collision.
     */
    @Override
    public void onCollision(GameObject object1, GameObject object2) {
        gameObjectCollection.removeGameObject(object1, Layer.DEFAULT);
        if (Objects.equals(object2.getTag(), Constants.MAIN_BALL_TAG) && gameManager.camera() == null) {
            Ball ball = (Ball) object2;
            setCamera(ball);
            ball.setCollisionCounter();
            }
        }

    /**
     * Getter method for the strategy tag.
     * @return String representing the tag of the strategy.
     */
    @Override
    public String getStrategyTag() {
        return Constants.CHANGE_CAMERA_STRATEGY_TAG;
    }
}
