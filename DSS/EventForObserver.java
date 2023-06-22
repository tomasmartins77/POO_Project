/**
 * The DSS package contains classes and interfaces related to Discrete Stochastic Simulation (DSS).
 * DSS is a simulation method that is used to analyze the behavior of a system over time.
 *
 */
package DSS;

/**
 * Interface that represents the event
 */
public interface EventForObserver {

    /**
     * Method that prints the event
     *
     * @param PresentTime time that the event will be executed
     */
    void print(double PresentTime);

    /**
     * Changes the observation event number
     */
    void changeObservationNumber();
}
