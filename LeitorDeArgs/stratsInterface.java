/**
 * The LeitorDeArgs package provides classes for reading and parsing command-line arguments.
 * It allows developers to easily handle command-line arguments passed to a Java application.
 */
package LeitorDeArgs;

import GrafoPack.GrafoInterface;
import java.util.ArrayList;

/**
 * This interface is responsible for reading the arguments
 */
public interface stratsInterface {

    /**
     * Reads the arguments
     * @param args the arguments
     */
    void readArgs(String[] args);

    /**
     * Gets the graph
     * @return the graph
     */
    GrafoInterface getGrafo();

    /**
     * Gets the constants
     * @return the constants
     */
    ArrayList<Number> getConstants();
}