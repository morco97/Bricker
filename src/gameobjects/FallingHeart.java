package bricker.gameobjects;

import bricker.Constants;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * Class of the game's falling heart object.
 */
public class FallingHeart extends GameObject {
    private final Counter livesCounter;
    private final GameObjectCollection gameObjectCollection;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     */
    public FallingHeart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                        GameObjectCollection gameObjectCollection, Counter livesCounter) {
        super(topLeftCorner, dimensions, renderable);
        this.gameObjectCollection = gameObjectCollection;
        this.livesCounter = livesCounter;
    }

    /**
     * Setting which objects should the instance of the class hit.
     * @param other The other GameObject.
     * @return boolean value of the other game object.
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        return other.getTag().equals(Constants.MAIN_PADDLE_TAG);
    }

    /**
     * Setting the behavior when colliding something with this instance.
     * @param other The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     *                  A reasonable elastic behavior can be achieved with:
     *                  setVelocity(getVelocity().flipped(collision.getNormal()));
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        if (livesCounter.value() < Constants.MAXIMAL_NUMBER_LIVES) {
            super.onCollisionEnter(other, collision);
            gameObjectCollection.removeGameObject(this);
            livesCounter.increment();
        }
        else{
            super.onCollisionEnter(other, collision);
            gameObjectCollection.removeGameObject(this);
        }
    }

}
