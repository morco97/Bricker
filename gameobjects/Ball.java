package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * Class for the game's ball object.
 */
public class Ball extends GameObject {
    private final Sound collitionSound;
    private final Counter collisionCounter;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     */
    public Ball(Vector2 topLeftCorner, Vector2 dimensions,
                Renderable renderable, Sound collitionSound) {
        super(topLeftCorner, dimensions, renderable);
        this.collitionSound = collitionSound;
        this.collisionCounter = new Counter(0);
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
        super.onCollisionEnter(other, collision);
        Vector2 newVel = getVelocity().flipped(collision.getNormal());
        setVelocity(newVel);
        collitionSound.play();
        collisionCounter.increment();
    }

    /**
     * Getter function for the balls collision counter.
     * @return The number of collision since it was made.
     */
    public int getCollisionCounter(){
        return collisionCounter.value();
    }

    /**
     * Setter function for the balls collision counter, sets its value to zero.
     */
    public void setCollisionCounter() {
        collisionCounter.reset();
    }
}
