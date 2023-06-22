/**
 * The DSS package contains classes and interfaces related to Discrete Stochastic Simulation (DSS).
 * DSS is a simulation method that is used to analyze the behavior of a system over time.
 *
 */
package DSS;

/**
 * Class that represents the types of events that can happen
 */
public abstract class EventTypes implements Comparable<EventTypes> {

    /**
     * Time of the event
     */
    public double timestamp;

    /**
     * Time of the event
     * 
     * @param time time of the event
     */
    public EventTypes(double time) {
        this.timestamp = time;
    }

    /**
     * Execute the event
     */
    public abstract void execute();

    /**
     * get the current time
     * 
     * @return the time of the event
     */
    double getTime() {

        return this.timestamp;

    }

    /**
     * set the new time
     * 
     * @param newTime new time of the event
     */
    public abstract void setTime(double newTime);

    /**
     * Compare the timestamp of the object with the timestamp of the object to be
     * compared
     * 
     * @param aux the object to be compared.
     * @return 1 if the timestamp of the object is bigger than the timestamp of the
     *         object to be compared, -1 otherwise
     */
    @Override
    public int compareTo(EventTypes aux) {
        if (this.timestamp > aux.timestamp) {
            return 1;
        } else if (this.timestamp < aux.timestamp) {
            return -1;
        } else {
            return 0;
        }
    }

}