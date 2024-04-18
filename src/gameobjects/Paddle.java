package bricker.gameobjects;

import bricker.Constants;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import java.awt.event.KeyEvent;

/**
 * Class that represents the games Paddle object.
 */
public class Paddle extends GameObject {
    private final UserInputListener inputListener;
    private final Vector2 screenSize;
    private final Counter collisionCounter;
    private final double  collisionLimit;
    private final GameObjectCollection gameObjectCollection;
    private final Counter paddleCounter;


    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     * @param inputListener The listener of the users keyboard.
     */
    public Paddle(Vector2 topLeftCorner, Vector2 dimensions,
                  Renderable renderable, UserInputListener inputListener,
                  Vector2 screenSize, double collisionLimit,
                  GameObjectCollection gameObjectCollection, Counter paddleCounter) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
        this.screenSize = screenSize;
        this.collisionLimit = collisionLimit;
        this.collisionCounter = new Counter(0);
        this.gameObjectCollection = gameObjectCollection;
        this.paddleCounter = paddleCounter;
        this.paddleCounter.increment();
    }

    /**
     *
     * @param deltaTime The time elapsed, in seconds, since the last frame. Can
     *                  be used to determine a new position/velocity by multiplying
     *                  this delta with the velocity/acceleration respectively
     *                  and adding to the position/velocity:
     *                  velocity += deltaTime*acceleration
     *                  pos += deltaTime*velocity
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        Vector2 movementDir = Vector2.ZERO;
        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT)){
            movementDir = movementDir.add(Vector2.LEFT);
        }
        if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT)){
            movementDir = movementDir.add(Vector2.RIGHT);
        }
        setVelocity(movementDir.mult(Constants.PADDLE_MOVEMENT_SPEED));
        handlePaddleOutOfScreen();
    }

    /**
     *
     * @param other The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     *                  A reasonable elastic behavior can be achieved with:
     *                  setVelocity(getVelocity().flipped(collision.getNormal()));
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        collisionCounter.increment();
        if (collisionCounter.value() == collisionLimit){
            gameObjectCollection.removeGameObject(this);
            paddleCounter.decrement();
        }
    }

    /**
     * Checks if a collision is with specific objects.
     * @param other The other GameObject.
     * @return True only if it is the matching objects
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        if (this.getTag().equals("main paddle")){
            return other.getTag().equals(Constants.MAIN_BALL_TAG) ||
                    other.getTag().equals(Constants.PACK_BALL_TAG) ||
                    other.getTag().equals(Constants.FALLING_HEART_TAG);
        }
        return other.getTag().equals(Constants.MAIN_BALL_TAG) ||
                other.getTag().equals(Constants.PACK_BALL_TAG);
    }

    /**
     * Getter method to the hit counter of the paddle.
     * @return The number of hits the paddle made.
     */
    public Counter getPaddleCounter(){
        return paddleCounter;
    }

    /**
     * Function to handle the scenario of the paddle leaving the screen.
     */
    private void handlePaddleOutOfScreen(){
        // right size
        if (getTopLeftCorner().x() >= screenSize.x() - getDimensions().x()){
            setTopLeftCorner(new Vector2(screenSize.x() - getDimensions().x(),
                    getTopLeftCorner().y()));
        }
        //left side
        else if (getTopLeftCorner().x() <= Vector2.ZERO.x()){
            setTopLeftCorner(new Vector2(Vector2.ZERO.x(), getTopLeftCorner().y())  );
        }
    }
}
