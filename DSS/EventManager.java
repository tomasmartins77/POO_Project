/**
 * The DSS package contains classes and interfaces related to Discrete Stochastic Simulation (DSS).
 * DSS is a simulation method that is used to analyze the behavior of a system over time.
 *
 */
package DSS;

import ACO.AntInterface;
import ACO.Events.*;
import ACO.AntColonyInterface;
import ACO.FeromonaInter;
import java.util.*;

/**
 * Class that represents the event manager
 */
public class EventManager implements EventSimulation, EventForObserver, EventForSwarm, EventForEvap {

    /**
     * The maximum time of simulation
     */
    private final double timelimit;

    /**
     * The number of events of evaporation
     */
    private int eevents;
    /**
     * The number of events of ant move
     */
    private int mevents;
    /**
     * The number of events of observer
     */
    private int oevents;
    /**
     * The time constant
     */
    private final double timeconstant;
    /**
     * The priority queue of events
     */
    private Queue<EventTypes> PEC;
    /**
     * The best path of the simulation
     */
    private final ArrayList<Integer>[] Bestpath;
    /**
     * The best price of the simulation
     */
    private final int[] BestPrice = new int[6];
    /**
     * All the pheromones created
     */
    private final ArrayList<FeromonaInter> TodasAsFeromonasCriadas;
    /**
     * The ant colony
     */
    private final AntColonyInterface Colonia;
    /**
     * The observer
     */
    private final float delta;

    /**
     * The constructor of the event manager
     *
     * @param colonia      the ant colony
     * @param maxTime      the maximum time of simulation
     * @param timeconstant the time constant
     * @param delta        the delta
     */
    public EventManager(AntColonyInterface colonia, double maxTime, double timeconstant, float delta) {
        PEC = new PriorityQueue<>();
        this.timeconstant = timeconstant;
        this.Colonia = colonia;
        this.timelimit = maxTime;

        TodasAsFeromonasCriadas = new ArrayList<>();
        this.mevents = 0;
        this.eevents = 0;
        this.oevents = 0;
        this.delta = delta;
        this.Bestpath = new ArrayList[6];

    }

    /**
     * Add to the Priority Queue the events of the simulation
     *
     * @param timestamp the time of the event
     * @param id1       the id of the first ant
     * @param id2       the id of the second ant
     */
    public void addQueueNewEvent(double timestamp, int id1, int id2) {
        Random rand = new Random();
        FeromonaInter aux1 = Colonia.getPheromones().get(id1).get(id2);

        for (FeromonaInter x : this.TodasAsFeromonasCriadas) {
            if (x == aux1) {
                return;
            }
        }
        TodasAsFeromonasCriadas.add(aux1);
        EventTypes aux = new EvaporationEvent(timestamp + (-timeconstant) * Math.log(1 - rand.nextDouble()), aux1,
                timeconstant, this);
        PEC.add(aux);
    }

    /**
     * Set path to a better path discovered
     *
     * @param flag       what path will be changed
     * @param path       the path to be altered
     * @param TotalPrice the total price of the path
     */
    public void alterarPath(int flag, ArrayList<Integer> path, int TotalPrice) {

        for (int l = 0; l < 6; l++) {
            if (this.Bestpath[l] != null)
                if (this.Bestpath[l].equals(path))
                    return;
        }

        for (int i = flag; i < 6; i++) {

            if (this.BestPrice[i] == 0 || this.BestPrice[i] > TotalPrice) {

                if (this.Bestpath[i] != null) {
                    alterarPath(i, this.Bestpath[i], BestPrice[i]);
                }
                try {
                    this.Bestpath[i] = (ArrayList<Integer>) path.clone();
                } catch (Exception e) {
                    System.out.println("Erro no clone");
                }
                this.BestPrice[i] = TotalPrice;

                break;
            }
        }

    }

    /**
     * Print the results of the simulation
     *
     * @param PresentTime the present time
     */
    public void print(double PresentTime) {
        int auxInit;
        System.out.println("Observation number:" + oevents);
        System.out.println("Present instant: " + PresentTime);
        System.out.println("Number of move events: " + mevents);
        System.out.println("Number of evaporation events: " + eevents);
        System.out.println("Top candidate cycles:");
        for (int i = 1; i < 6; i++)
            if (Bestpath[i] != null) {
                System.out.print("{" + (auxInit = Bestpath[i].get(0)));
                for (Integer aux : Bestpath[i]) {
                    if (aux != auxInit)
                        System.out.print("," + aux);
                }
                System.out.print("}" + " : " + BestPrice[i]);
                System.out.println();
            }

        if (Bestpath[0] != null) {
            System.out.print("Best Hamiltonian cycle: {" + (auxInit = Bestpath[0].get(0)));
            for (Integer aux : Bestpath[0]) {
                if (aux != auxInit)
                    System.out.print("," + aux);
            }
            System.out.print("}" + " : " + BestPrice[0]);
            System.out.println();
        } else {
            System.out.println("Best Hamiltonian cycle: {}");
        }
    }

    /**
     * Increase the number of events of ant move
     */
    public void changeAntEventNumber() {
        this.mevents++;
    }

    /**
     * Increase the number of events of evaporation
     */
    public void changeEvapEventNumber() {
        this.eevents++;
    }

    /**
     * Increase the number of events of observer
     */
    public void changeObservationNumber() {
        this.oevents++;
    }

    /**
     * Simulate the events
     *
     * @param PQueueSize the size of the priority queue
     */
    public void simular(int PQueueSize) {
        double Timestamp = 0;
        this.GenerateQueue(PQueueSize);
        EventTypes aux;
        while (Timestamp <= this.timelimit) {
            if (!PEC.isEmpty()) {

                aux = PEC.poll();
                Timestamp = aux.getTime();
                aux.execute();
                if (aux.getTime() <= this.timelimit) {
                    PEC.add(aux);
                }
            } else
                break;

        }

    }

    /**
     * Generate the events of the simulation
     *
     * @param QueueSize the size of the priority queue
     */
    void GenerateQueue(int QueueSize) {
        PEC = new PriorityQueue<>(QueueSize);
        EventTypes aux;
        EventTypes aux1 = new ObservationEvent(timelimit / 20, this);

        PEC.add(aux1);

        for (AntInterface x : Colonia.getAnts()) {

            aux = new AntMove(0, x, this, delta);
            PEC.add(aux);

        }

    }
}