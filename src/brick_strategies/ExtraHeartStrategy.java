package bricker.brick_strategies;

import bricker.Constants;
import bricker.gameobjects.FallingHeart;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * Class for the extra heart strategy.
 */
public class ExtraHeartStrategy implements CollisionStrategy{
    private final GameObjectCollection gameObjectCollection;
    private final ImageReader imageReader;
    private final Counter lifeCounter;

    /**
     * Constructor for the extra heart strategy.
     * @param gameObjectCollection The game's game object collection.
     * @param imageReader The game's image reader.
     * @param lifeCounter The game's life counter.
     */
    public ExtraHeartStrategy(GameObjectCollection gameObjectCollection,
                              ImageReader imageReader, Counter lifeCounter){
        this.lifeCounter = lifeCounter;
        this.gameObjectCollection = gameObjectCollection;
        this.imageReader = imageReader;
    }

    /**
     * Sets the behavior when colliding.
     * @param objectOne The object that had instance of the class.
     * @param objectTwo The other object in the collision.
     */
    @Override
    public void onCollision(GameObject objectOne, GameObject objectTwo) {
        if (lifeCounter.value() < Constants.MAXIMAL_NUMBER_LIVES){
            gameObjectCollection.removeGameObject(objectOne);
            addFallingHeart(objectOne.getTopLeftCorner());
        }
        else{
            gameObjectCollection.removeGameObject(objectOne);
        }
    }

    /**
     * Getter method for the strategy tag.
     * @return String representing the tag of the strategy.
     */
    @Override
    public String getStrategyTag() {
        return Constants.EXTRA_HEART_STRATEGY_TAG;
    }

    /**
     * Method that adds a falling heart object in a specific location.
     * @param location The location to locate the falling heart.
     */
    private void addFallingHeart(Vector2 location){
        Renderable heartImageRenderable = imageReader.readImage(Constants.LIFE_IMAGE_PATH,
                true);
        FallingHeart fallingHeart = new FallingHeart(location, new Vector2(Constants.HEART_SIZE,
                Constants.HEART_SIZE), heartImageRenderable, gameObjectCollection, lifeCounter);
        fallingHeart.setTag(Constants.FALLING_HEART_TAG);
        fallingHeart.setVelocity(new Vector2(0, 100));
        gameObjectCollection.addGameObject(fallingHeart, Layer.DEFAULT);
    }
}
