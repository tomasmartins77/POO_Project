/**
 * The LeitorDeArgs package provides classes for reading and parsing command-line arguments.
 * It allows developers to easily handle command-line arguments passed to a Java application.
 */
package LeitorDeArgs;

import GrafoPack.Grafo;
import GrafoPack.GrafoInterface;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import static LeitorDeArgs.stratChooser.VerifyFloat;
import static LeitorDeArgs.stratChooser.VerifyInt;

/**
 * Class that represents the strategy that reads from a file
 */
public class fileStrategy implements stratsInterface {

    /**
     * the graph
     */
    private Grafo grafo;
    /**
     * the input parameters
     */
    private final ArrayList<Number> constantes;

    /**
     * Constructor the strategy that reads from a file
     */
    public fileStrategy() {
        constantes = new ArrayList<>();
    }

    /**
     * Reads the input parameters from a file
     *
     * @param args the input parameters
     */
    @Override
    public void readArgs(String[] args) {

        try {
            String path = args[1];
            File file = new File(path); // Specify the path to your text file

            Scanner scanner = new Scanner(file);

            String inputLine = scanner.nextLine();
            String[] inputs = inputLine.split(" "); // split the line into an array of strings
            if (inputs.length != 10) {
                System.out.println("Invalid number of input values");
                System.exit(0);
            }
            constantes.add(VerifyInt(inputs[0]));
            if ((int) constantes.get(0) < 3) { // Minimum number of nodes is 3
                System.out.println("Invalid number of nodes");
                System.exit(0);
            }
            constantes.add(VerifyInt(inputs[1]));
            constantes.add(VerifyFloat(inputs[2]));
            constantes.add(VerifyFloat(inputs[3]));
            constantes.add(VerifyFloat(inputs[4]));
            constantes.add(VerifyFloat(inputs[5]));
            constantes.add(VerifyFloat(inputs[6]));
            constantes.add(VerifyFloat(inputs[7]));
            constantes.add(VerifyInt(inputs[8]));
            constantes.add(VerifyFloat(inputs[9]));

            int[][] matriz = new int[(int) constantes.get(0)][(int) constantes.get(0)];

            int j = 0, i;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] elements = line.split("[\\s\\t]+");
                for (i = 0; i < (int) constantes.get(0); i++) {
                    try {
                        matriz[j][i] = VerifyInt(elements[i]);
                    } catch (Exception e) {
                        System.out.println("Invalid file matrix format");
                        System.exit(0);
                    }
                }

                j++;

            }
            for (i = 0; i < (int) constantes.get(0); i++) {
                for (j = i; j < (int) constantes.get(0); j++) {
                    if (matriz[i][j] != matriz[j][i]) {
                        System.out.println("Invalid file matrix format");
                        for (int k = 0; k < (int) constantes.get(0); k++) {
                            for (int l = 0; l < (int) constantes.get(0); l++) {
                                System.out.print(matriz[k][l] + " ");
                            }
                            System.out.println();
                        }
                        System.exit(0);
                    }
                }
            }
            if (j != (int) constantes.get(0) || i != (int) constantes.get(0)) {
                System.out.println("Invalid number of nodes");
                System.exit(0);
            }
            grafo = new Grafo((int) constantes.get(0), matriz);
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
            System.exit(0);
        }
        printInputs();
        constantes.remove(0);
    }

    /**
     * get the graph
     * 
     * @return the grafo
     */
    public GrafoInterface getGrafo() {
        return grafo;
    }

    /**
     * get all the constants
     * 
     * @return the constants
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
        System.out.println("\t" + constantes.get(1) + ": The nest node");
        System.out.println("\t" + constantes.get(2) + ": alpha, ant move event");
        System.out.println("\t" + constantes.get(3) + ": beta, ant move event");
        System.out.println("\t" + constantes.get(4) + ": delta, ant move event");
        System.out.println("\t" + constantes.get(5) + ": eta, pheromone evaporation event");
        System.out.println("\t" + constantes.get(6) + ": rho, pheromone evaporation event");
        System.out.println("\t" + constantes.get(7) + ": pheromone level");
        System.out.println("\t" + constantes.get(8) + ": ant colony size");
        System.out.println("\t" + constantes.get(9) + ": final instant");
    }
}
