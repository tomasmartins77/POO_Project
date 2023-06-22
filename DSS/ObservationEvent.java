/**
 * The DSS package contains classes and interfaces related to Discrete Stochastic Simulation (DSS).
 * DSS is a simulation method that is used to analyze the behavior of a system over time.
 *
 */
package DSS;

/**
 * Class that represents the event of show updates about the simulation
 */
class ObservationEvent extends EventTypes {

    /**
     * The event that will be executed
     */
    private final EventForObserver Evento;

    /**
     * The maximum time that the event will be executed
     */
    private final double maxTime;

    /**
     * Construtor to the observation event
     * 
     * @param time   time of the event
     * @param evento event that will be executed
     */
    ObservationEvent(double time, EventForObserver evento) {
        super(time);
        this.Evento = evento;
        this.maxTime = time * 20;
    }

    /**
     * Set the time of the next event
     * 
     * @param newTime new time of the event
     */
    public void setTime(double newTime) {

        this.timestamp = newTime;
    }

    /**
     * Execute the event
     */
    @Override
    public void execute() {
        Evento.changeObservationNumber();
        Evento.print(this.timestamp);
        this.setTime(timestamp + maxTime / 20);

    }

}
