/**
 * The ACO package contains classes and interfaces related to Ant Colony Optimization (ACO) algorithm.
 * ACO is an algorithm inspired by the foraging behavior of ants that can be used to solve optimization problems in
 * path finding and graph traversal.
 *
 */
package ACO;

import java.util.ArrayList;

/**
 * Interface that represents the ant
 */
public interface AntInterface {

    /**
     * An ant chooses the next node to visit based on the pheromone levels and the
     * distance between the nodes
     * 
     * @return the cost of the path
     */
    int move();

    /**
     * resets the path of the ant
     */
    void resetPath();

    /**
     * gets the cost of the path
     * 
     * @return the cost of the path
     */
    int PathCost();

    /**
     * get the path of the ant
     * 
     * @return the path
     */
    ArrayList<Integer> getPath();

    /**
     * checks if the ant has ended the path
     * 
     * @return true if the ant has ended the path, false if not
     */
    boolean checkIfEndedPath();
}
