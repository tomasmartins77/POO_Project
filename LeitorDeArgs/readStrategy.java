/**
 * The LeitorDeArgs package provides classes for reading and parsing command-line arguments.
 * It allows developers to easily handle command-line arguments passed to a Java application.
 */
package LeitorDeArgs;

import GrafoPack.Grafo;
import GrafoPack.GrafoInterface;

import java.util.*;

import static LeitorDeArgs.stratChooser.VerifyFloat;
import static LeitorDeArgs.stratChooser.VerifyInt;

/**
 * Class that represents the strategy that reads from a file
 */
public class readStrategy implements stratsInterface {

    /**
     * the input parameters
     */
    private final ArrayList<Number> constantes;

    /**
     * the graph
     */
    private Grafo grafo;

    /**
     * Constructor the strategy that reads from the command line
     */
    public readStrategy() {
        constantes = new ArrayList<>();
    }

    /**
     * Reads the input parameters from the command line
     *
     * @param args the input parameters
     */
    @Override
    public void readArgs(String[] args) {

        Random rand = new Random();

        if (args.length != 12) {

            System.out.println("Invalid number of arguments");
            System.exit(0);
        } else {
            constantes.add(VerifyInt(args[1]));

            if ((int) constantes.get(0) < 3) { // Minimum number of nodes is 3
                System.out.println("Invalid number of nodes");
                System.exit(0);
            }

            constantes.add(VerifyInt(args[2]));

            constantes.add(VerifyInt(args[3]));

            constantes.add(VerifyFloat(args[4]));

            constantes.add(VerifyFloat(args[5]));
            constantes.add(VerifyFloat(args[6]));
            constantes.add(VerifyFloat(args[7]));
            constantes.add(VerifyFloat(args[8]));
            constantes.add(VerifyFloat(args[9]));
            constantes.add(VerifyInt(args[10]));
            constantes.add(VerifyFloat(args[11]));


        }
        int max = (Integer) constantes.get(0) * ((Integer) constantes.get(0) - 1);
        int min = (Integer) constantes.get(0);
        grafo = new Grafo((Integer) constantes.get(0), rand.nextInt(max - min + 1) + min, (Integer) constantes.get(1));
        printInputs();
        constantes.remove(0);
        constantes.remove(0);
    }

    public GrafoInterface getGrafo() {

        return grafo;

    }

    /**
     * Returns the input parameters
     *
     * @return the input parameters
     */
    public ArrayList<Number> getConstants() {
        return constantes;
    }

    /**
     * Prints the input parameters
     */
    private void printInputs() {
        System.out.println("Input parameters: ");
        System.out.println("\t" + constantes.get(0) + ": Number of nodes in the graph");
        System.out.println("\t" + constantes.get(2) + ": The nest node");
        System.out.println("\t" + constantes.get(3) + ": alpha, ant move event");
        System.out.println("\t" + constantes.get(4) + ": beta, ant move event");
        System.out.println("\t" + constantes.get(5) + ": delta, ant move event");
        System.out.println("\t" + constantes.get(6) + ": eta, pheromone evaporation event");
        System.out.println("\t" + constantes.get(7) + ": rho, pheromone evaporation event");
        System.out.println("\t" + constantes.get(8) + ": pheromone level");
        System.out.println("\t" + constantes.get(9) + ": ant colony size");
        System.out.println("\t" + constantes.get(10) + ": final instant");
    }
}
