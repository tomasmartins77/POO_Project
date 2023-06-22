/**
 * The LeitorDeArgs package provides classes for reading and parsing command-line arguments.
 * It allows developers to easily handle command-line arguments passed to a Java application.
 */
package LeitorDeArgs;

import java.util.*;
import GrafoPack.GrafoInterface;

/**
 * This class is responsible for choosing the strategy to be used to read the
 * arguments
 */
public class stratChooser {

    /**
     * The strategy to be used
     */
    private stratsInterface FinalStrategy;

    /**
     * The strategies available
     */
    private final HashMap<String, stratsInterface> strategies;

    /**
     * Constructor for the strategy chooser
     */
    public stratChooser() {
        strategies = new HashMap<>();
    }

    /**
     * Adds a strategy to the strategy chooser
     * 
     * @param stratString the name of the strategy
     * @param strat       the strategy
     */
    public void setStrategy(String stratString, stratsInterface strat) {
        strategies.put(stratString, strat);
    }

    /**
     * Reads the arguments
     * 
     * @param args the arguments
     */
    public void readArgs(String[] args) {
        try {
            FinalStrategy = strategies.get(args[0]);
            FinalStrategy.readArgs(args);
        } catch (Exception e) {
            System.out.print(e);
        }
    }

    /**
     * Gets the graph
     * 
     * @return the graph
     */
    public GrafoInterface getGrafo() {
        return FinalStrategy.getGrafo();
    }

    /**
     * Gets the constants
     * 
     * @return the constants
     */
    public ArrayList<Number> getConstantes() {
        return FinalStrategy.getConstants();
    }

    /**
     * Verifies if the input is an integer
     * 
     * @param input the input to be verified
     * @return the input as int if it is an integer
     */
    static int VerifyInt(String input) {
        try {
            if (Integer.parseInt(input) < 0) {
                System.out.println("Negative number input");
                System.exit(0);
            }
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Some input value is not Integer as should be");
            System.exit(0);
        }
        return Integer.parseInt(input);
    }

    /**
     * Verifies if the input is a float
     * 
     * @param input the input to be verified
     * @return the input as float if it is a float
     */
    static float VerifyFloat(String input) {
        try {
            if (input.contains(",")) {
                input = input.replace(",", ".");
            }
            if (Float.parseFloat(input) < 0) {
                System.out.println("Negative number input");
                System.exit(0);
            }
        } catch (NumberFormatException e) {
            System.out.println("Some input value is not Float as should be");
            System.exit(0);
        }
        return Float.parseFloat(input);
    }

}