/**
 * The ACO package contains classes and interfaces related to Ant Colony Optimization (ACO) algorithm.
 * ACO is an algorithm inspired by the foraging behavior of ants that can be used to solve optimization problems in
 * path finding and graph traversal.
 *
 */
package ACO;

/**
 * Class that represents the pheromone
 */
class Feromona implements FeromonaInter {

    /**
     * The pheromone level
     */
    private float pheromone;

    /**
     * The total number of weights
     */
    private final int totalWeights;

    /**
     * The value for calculating the level of pheromone
     */
    private final float gamma;

    /**
     * The value for decreasing pheromones
     */
    private final float ro;

    /**
     * @param totalWeights  total number of weights
     * @param gamma         value for calculating the level of pheromone
     * @param ro            value for decreasing pheromones
     */
    Feromona(int totalWeights, float gamma, float ro) {
        this.pheromone = 0;
        this.totalWeights = totalWeights;
        this.gamma = gamma;
        this.ro = ro;
    }

    /**
     * Set the pheromone level
     * 
     * @param sumOfWeights sum of all weights
     */
    void setPheromone(int sumOfWeights) {
        this.pheromone += gamma * totalWeights / sumOfWeights;
    }

    /**
     * @return the pheromone level
     */
    public float getPheromone() {
        return pheromone;
    }

    /**
     * Decrease the pheromone level
     */
    public void evaporationOfPheromone() {
        if (pheromone > 0) {
            this.pheromone -= ro;
        } else {
            this.pheromone = 0;
        }
    }
}