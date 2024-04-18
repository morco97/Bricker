package bricker.brick_strategies;

import danogl.GameObject;

/**
 * Interface that represent the strategies for the brick object.
 */
public interface CollisionStrategy {

    /**
     * Sets the behavior when colliding.
     * @param objectOne The object that had instance of the class.
     * @param objectTwo The other object in the collision.
     */
    void onCollision(GameObject objectOne,
                     GameObject objectTwo);

    /**
     * Getter method for the strategy tag.
     * @return String representing the tag of the strategy.
     */
    String getStrategyTag();
}
