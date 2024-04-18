package bricker.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import bricker.Constants;

/**
 * Class for the basic collision strategy.
 */
public class BasicCollisionStrategy implements CollisionStrategy{
    private final GameObjectCollection gameObjectCollection;

    /**
     * Constructor for the basic collision strategy.
     * @param gameObjectCollection The game's game object collection.
     */
    public BasicCollisionStrategy(GameObjectCollection gameObjectCollection){
        this.gameObjectCollection = gameObjectCollection;
    }

    /**
     * Sets the behavior when colliding.
     * @param objectOne The object that had instance of the class.
     * @param objectTwo The other object in the collision.
     */
    @Override
    public void onCollision(GameObject objectOne,
                            GameObject objectTwo) {
        gameObjectCollection.removeGameObject(objectOne, Layer.DEFAULT);
    }

    /**
     * Getter method for the strategy tag.
     * @return String representing the tag of the strategy.
     */
    @Override
    public String getStrategyTag() {
        return Constants.BASIC_STRATEGY_TAG;
    }
}
