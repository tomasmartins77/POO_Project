/**
 * The DSS package contains classes and interfaces related to Discrete Stochastic Simulation (DSS).
 * DSS is a simulation method that is used to analyze the behavior of a system over time.
 *
 */
package DSS;

import java.util.ArrayList;

/**
 * Interface that represents the event
 */
public interface EventForSwarm {

    /**
     * Method that changes the path
     * 
     * @param flag      where the path will be changed
     * @param path      path that will be changed
     * @param totalCost cost of the path
     */
    void alterarPath(int flag, ArrayList<Integer> path, int totalCost);

    /**
     * Method that adds a new event to the queue
     * 
     * @param timestamp time that the event will be executed
     * @param Id1       id of the first ant
     * @param Id2       id of the second ant
     */
    void addQueueNewEvent(double timestamp, int Id1, int Id2);

    /**
     * Method that changes the ant event number
     */
    void changeAntEventNumber();

}