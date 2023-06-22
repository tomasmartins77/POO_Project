/**
 * The ACO package contains classes and interfaces related to Ant Colony Optimization (ACO) algorithm.
 * ACO is an algorithm inspired by the foraging behavior of ants that can be used to solve optimization problems in
 * path finding and graph traversal.
 *
 */
package ACO;

import java.util.*;

/**
 * Interface that represents the ant colony
 */
public interface AntColonyInterface {

    /**
     * returns the array of ants
     * 
     * @return the colony of ants
     */
    ArrayList<Ant> getAnts();

    /**
     * gets the map of the pheromones
     * 
     * @return the pheromone map
     */
    HashMap<Integer, Hashtable<Integer, Feromona>> getPheromones();
}
