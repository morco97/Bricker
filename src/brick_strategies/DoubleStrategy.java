package bricker.brick_strategies;

import bricker.Constants;
import danogl.GameObject;

import java.util.Random;

/**
 * Class for the double behavior strategy.
 */
public class DoubleStrategy implements CollisionStrategy{
    private final CollisionStrategy[] strategiesArray;
    private CollisionStrategy strategyOne;
    private CollisionStrategy strategyTwo;

    /**
     * Constructor for the double behavior strategy.
     * @param strategiesArray Array that hold all the strategies this strategy can hold.
     */
    public DoubleStrategy(CollisionStrategy[] strategiesArray){
        this.strategiesArray = strategiesArray;
        pickRandomStrategies();

    }

    /**
     * Sets the behavior when colliding.
     * @param objectOne The object that had instance of the class.
     * @param objectTwo The other object in the collision.
     */
    @Override
    public void onCollision(GameObject objectOne, GameObject objectTwo) {
        strategyOne.onCollision(objectOne, objectTwo);
        strategyTwo.onCollision(objectOne, objectTwo);
    }

    /**
     * Getter method for the strategy tag.
     * @return String representing the tag of the strategy.
     */
    @Override
    public String getStrategyTag() {
        return Constants.DOUBLE_STRATEGY_TAG;
    }

    /**
     * Method that picks two strategies randomly from the strategies it holds.
     */
    private void pickRandomStrategies(){
        Random random = new Random();
        CollisionStrategy strategyOne = strategiesArray[random.nextInt(strategiesArray.length)];
        CollisionStrategy strategyTwo = strategiesArray[random.nextInt(strategiesArray.length)];
        if ((strategyOne.getStrategyTag().equals(Constants.DOUBLE_STRATEGY_TAG)) &&
                strategyTwo.getStrategyTag().equals(Constants.DOUBLE_STRATEGY_TAG)){
            pickRandomStrategies();
        }
        else{
            this.strategyOne = strategyOne;
            this.strategyTwo = strategyTwo;
        }
    }
}
