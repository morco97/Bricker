package bricker.brick_strategies;

import bricker.Constants;
import bricker.gameobjects.Ball;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.Sound;
import danogl.gui.SoundReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import java.util.Random;

/**
 * Class for the extra ball strategy.
 */
public class ExtraBallStrategy implements CollisionStrategy {
    private final Random random;
    private final GameObjectCollection gameObjectCollection;
    private final ImageReader imageReader;
    private final SoundReader soundReader;

    /**
     * Constructor for the extra ball strategy.
     * @param gameObjectCollection The game's game object collection.
     * @param imageReader The game's image reader.
     * @param soundReader The game's sound reader.
     */
    public ExtraBallStrategy(GameObjectCollection gameObjectCollection, ImageReader imageReader,
                             SoundReader soundReader) {
        this.random = new Random();
        this.gameObjectCollection = gameObjectCollection;
        this.imageReader = imageReader;
        this.soundReader = soundReader;
    }

    /**
     * Method that creates a small ball called a pack.
     * @return The pack.
     */
    private Ball createBall(){
        Renderable ballImage = imageReader.readImage(Constants.MOCK_BALL_IMAGE_PATH, true);
        Sound collisionSound = soundReader.readSound(Constants.BALL_SOUND_PATH);
        Ball ball = new Ball(Vector2.ZERO, new Vector2(
                Constants.PACK_TO_BALL_SIZE_RATIO*Constants.BALL_SIZE,
                Constants.PACK_TO_BALL_SIZE_RATIO*Constants.BALL_SIZE),
                ballImage, collisionSound);
        double angle = random.nextDouble() * Math.PI;
        float velocityX = (float)Math.cos(angle) * Constants.BALL_SPEED;
        float velocityY = (float)Math.sin(angle) * Constants.BALL_SPEED;
        ball.setVelocity(new Vector2(velocityX, velocityY));

        ball.setTag(Constants.PACK_BALL_TAG);
        return ball;
    }

    /**
     * Sets the behavior when colliding.
     * @param object1 The object that had instance of the class.
     * @param object2 The other object in the collision.
     */
    @Override
    public void onCollision(GameObject object1, GameObject object2) {
        gameObjectCollection.removeGameObject(object1, Layer.DEFAULT);
        Ball[] balls = {createBall(), createBall()};
        for (Ball ball: balls){
            ball.setCenter(object1.getCenter());
            gameObjectCollection.addGameObject(ball, Layer.DEFAULT);
        }
    }

    /**
     * Getter method for the strategy tag.
     * @return String representing the tag of the strategy.
     */
    @Override
    public String getStrategyTag() {
        return Constants.EXTRA_BALL_STRATEGY_TAG;
    }
}
