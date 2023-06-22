/**
 * The DSS package contains classes and interfaces related to Discrete Stochastic Simulation (DSS).
 * DSS is a simulation method that is used to analyze the behavior of a system over time.
 *
 */
package ACO.Events;

import ACO.FeromonaInter;
import DSS.EventForEvap;
import DSS.EventTypes;

import java.util.Random;

/**
 * Class that presents the Evaporation Event of the pheromones
 */
public class EvaporationEvent extends EventTypes {

    /**
     * The pheromone that will be evaporated
     */
    private final FeromonaInter feromonas;
    /**
     * The time constant of the evaporation
     */
    private final double timeConstant;

    /**
     * The event that will be executed
     */
    private final EventForEvap Simulate;

    /**
     * @param time        time that the event will be executed
     * @param newFeromona pheromone that will be evaporated
     * @param teta        time constant of the evaporation
     * @param eventMain   event that will be executed
     */
    public EvaporationEvent(double time, FeromonaInter newFeromona, double teta, EventForEvap eventMain) {

        super(time);
        this.feromonas = newFeromona;
        this.timeConstant = teta;
        this.Simulate = eventMain;

    }

    /**
     * @param newTime new time of the event execution
     */
    public void setTime(double newTime) {

        this.timestamp = newTime;
    }

    /**
     * Method that executes the event
     */
    @Override
    public void execute() {
        Random rand = new Random();
        this.setTime(timestamp + (-timeConstant) * Math.log(1 - rand.nextDouble()));

        if (feromonas.getPheromone() > 1) {
            feromonas.evaporationOfPheromone();
            Simulate.changeEvapEventNumber();
        }
    }

}