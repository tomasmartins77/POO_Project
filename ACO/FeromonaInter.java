/**
 * The ACO package contains classes and interfaces related to Ant Colony Optimization (ACO) algorithm.
 * ACO is an algorithm inspired by the foraging behavior of ants that can be used to solve optimization problems in
 * path finding and graph traversal.
 *
 */
package ACO;

/**
 * Interface that represents the pheromone
 */
public interface FeromonaInter {

    /**
     * Computes the evaporation of pheromone
     */
    void evaporationOfPheromone();

    /**
     * get the pheromone level
     * 
     * @return the pheromone level
     */
    float getPheromone();

}
