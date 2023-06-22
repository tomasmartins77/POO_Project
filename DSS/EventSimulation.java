/**
 * The DSS package contains classes and interfaces related to Discrete Stochastic Simulation (DSS).
 * DSS is a simulation method that is used to analyze the behavior of a system over time.
 *
 */
package DSS;

/**
 * Interface that represents the event
 */
public interface EventSimulation {

    /**
     * Method that simulates the event
     * @param PQueueSize size of the queue
     */
    void simular(int PQueueSize);

}
