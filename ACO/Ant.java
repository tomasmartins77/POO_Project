/**
 * The ACO package contains classes and interfaces related to Ant Colony Optimization (ACO) algorithm.
 * ACO is an algorithm inspired by the foraging behavior of ants that can be used to solve optimization problems in
 * path finding and graph traversal.
 *
 */
package ACO;

import java.util.*;

/**
 * Class that represents one ant, connected to the ant colony through
 * generalization
 *
 */
class Ant implements AntInterface {

    /**
     * The path followed by the ant.
     */
    private final ArrayList<Integer> path;

    /**
     * The ant colony to which the ant belongs.
     */
    private final AntColony colony;

    /**
     * The current node index of the ant.
     */
    private int currentNode;

    /**
     * Indicates whether the ant has reached the end of its path.
     */
    private int PathEnded;

    /**
     * The cost of the ant's path.
     */
    private int PathCost;

    /**
     * Constructor of the Ant class
     * 
     * @param Antcolony - AntColony object that contains all the information that
     *                  the ant needs
     */
    Ant(AntColony Antcolony) {
        this.colony = Antcolony;
        this.path = new ArrayList<>();
        addToList(path, colony.getNestNode());
        this.currentNode = colony.getNestNode();
        this.PathEnded = 0;
    }

    /**
     * get the weight of the current path
     * 
     * @return int - the weight of the current path
     */
    public int PathCost() {
        return this.PathCost;
    }

    /**
     * Get the path of the ant
     * 
     * @return ArrayList - the path of the ant
     */
    public ArrayList<Integer> getPath() {
        return this.path;
    }

    /**
     * Calculate the normalized probabilities to go to the adjacent nodes
     * 
     * @return Hashtable - the normalized probabilities to go to
     *         another Node (Integer - Node, Float - Probability)
     */
    private Hashtable<Integer, Float> getNormalizedProbabilities() {
        Hashtable<Integer, Float> probability = new Hashtable<>();
        float sum = 0;
        float Ci = 0;
        float Cijk;

        Hashtable<Integer, Integer> weights = getPossibleWeights(); // get the weights of the adjacent nodes that the
        if (weights.isEmpty()) { // ant can go to
            return null;
        }

        Hashtable<Integer, Feromona> pheromone = getPossiblePheromones(); // get the pheromones of the adjacent nodes
                                                                          // that
        if (pheromone.isEmpty()) { // the ant can go to
            return null;
        }
        // Compute Ci
        for (Map.Entry<Integer, Integer> entry : weights.entrySet()) {
            Ci += ((colony.getAlpha() + pheromone.get(entry.getKey()).getPheromone())
                    / (colony.getBeta() + entry.getValue()));
        }
        // Compute Cijk and the sum of all Cijk
        for (Map.Entry<Integer, Integer> entry : weights.entrySet()) {
            Cijk = ((colony.getAlpha() + pheromone.get(entry.getKey()).getPheromone())
                    / (colony.getBeta() + entry.getValue()));
            probability.put(entry.getKey(), Cijk / Ci);
            sum += Cijk / Ci;
        }
        // Normalize the probabilities
        for (Map.Entry<Integer, Float> entry : probability.entrySet()) {
            probability.put(entry.getKey(), entry.getValue() / sum);
        }

        return probability;
    }

    /**
     * Choose the next node to go to
     * 
     * @param probability - Hashtable with the probabilities to go
     *                    to another Node
     * @return int - the node that the ant will go to next
     */
    private int chooseNode(Hashtable<Integer, Float> probability) {
        Random rand = new Random();
        float node = rand.nextFloat();
        float partialSum = 0;

        for (Map.Entry<Integer, Float> entry : probability.entrySet()) {
            partialSum += entry.getValue();
            if (node < partialSum) {
                return entry.getKey();
            }
        }
        return -1;
    }

    /**
     * The algoritm that makes the ant move to the next node
     * 
     * @return int - the cost of the ant's path
     */
    @Override
    public int move() {
        Random rand = new Random();
        int newNode;
        int previousNode = currentNode;
        int flag = 0;
        // Get the normalized probabilities
        Hashtable<Integer, Float> NormalizedProbabilities = getNormalizedProbabilities();
        // If the ant can't go to any node, it is in a loop and will go to a random node
        if (NormalizedProbabilities == null) {
            Hashtable<Integer, Integer> possibleWeights = getWeights();
            Set<Integer> keys = possibleWeights.keySet();

            newNode = (int) keys.toArray()[rand.nextInt(keys.size())];
            if (!checkIfEndedPath(newNode)) {
                removeLoop(newNode);
            }
            flag = 1;
        } else { // is not in a loop and will choose a node based on the normalized probabilities
            newNode = chooseNode(NormalizedProbabilities);
        }
        // If the ant has ended the path, it will set the pheromones and return the cost
        // of the path
        if (checkIfEndedPath(newNode)) {
            setPheromones(path);
            PathEnded = 1;
            currentNode = newNode;
            this.PathCost = sumOfWeightsPath();
            return getCost(currentNode, previousNode);
        }
        if (flag == 0) {
            addToList(this.path, newNode);
        }
        currentNode = newNode;

        return getCost(currentNode, previousNode);
    }

    /**
     * Get the cost of the path of the ant
     *
     * @return int - the cost of the path of the ant
     */
    private int sumOfWeightsPath() {
        return colony.sumOfWeightsPath(path);
    }

    /**
     * Get the confirmation if the ant ended the Hamiltonian cycle for the event
     * 
     * @return boolean - true if the ant has ended the path, false otherwise
     */
    public boolean checkIfEndedPath() {

        return this.PathEnded == 1;

    }

    /**
     * Get the confirmation if the ant ended the Hamiltonian cycle for the ant
     *
     * @param node - the node that the ant is in
     * @return boolean - true if the ant has ended the path, false otherwise
     */
    private boolean checkIfEndedPath(int node) {
        return getSize(path) == colony.getTotalVertex() && node == colony.getNestNode();
    }

    /**
     * Reset the path of the ant, the ant can now start a new cycle
     *
     */
    @Override
    public void resetPath() {
        path.clear();
        path.add(colony.getNestNode());
        currentNode = colony.getNestNode();
        PathCost = 0;
        PathEnded = 0;
    }

    /**
     * Set the pheromones in the path that the and finished
     * 
     * @param path ArrayList with the path of the ant where the
     *             pheremones will be added
     */
    private void setPheromones(ArrayList<Integer> path) {
        colony.setPheromones(path);
    }

    /**
     * Remove loops that the ant might has done in the graph trying to find the
     * Hamiltonian cycle, sees the first node that is repeated and removes all the
     * nodes after that
     * 
     * @param nodeToRemove - int with the node to remove from the path
     */
    private void removeLoop(int nodeToRemove) {
        int flag = 0;
        for (int i = 0; i < getSize(path); i++) {
            if (nodeToRemove == getFromList(path, i) && flag == 0) {
                flag = 1;
            } else if (flag == 1) {
                removeFromList(path, i);
                i--;
            }
        }
    }

    /**
     * Get the weights of the adjacent nodes that are not in the path
     * 
     * @return Hashtable with the weights of the possible adjacent
     *         nodes
     */
    private Hashtable<Integer, Integer> getPossibleWeights() {
        Hashtable<Integer, Integer> possibleWeights = getWeights();
        for (Integer integer : path) {
            possibleWeights.remove(integer);
        }
        return possibleWeights;
    }

    /**
     * Get the weights of the adjacent nodes
     * 
     * @return Hashtable with the weights of the adjacent nodes
     */
    private Hashtable<Integer, Integer> getWeights() { // não podemos juntar com o de cima??????
        return colony.getWeightsFromNode(currentNode);
    }

    /**
     * Get the pheromones in the nodes
     * 
     * @return Hashtable with level of the pheromones in the nodes
     */
    private Hashtable<Integer, Feromona> getPheromones() {
        return colony.getPheromonesFromNode(currentNode);
    }

    /**
     * Get the pheromones in the nodes that the ant can go to
     * 
     * @return Hashtable with the level of the pheromones in the nodes
     *         that the ant can go to
     */
    private Hashtable<Integer, Feromona> getPossiblePheromones() {
        Hashtable<Integer, Feromona> pheromones = getPheromones();

        Hashtable<Integer, Feromona> possiblePheromones = new Hashtable<>(pheromones);
        for (Integer integer : path) {
            possiblePheromones.remove(integer);
        }
        return possiblePheromones;
    }

    /**
     * Get the node in some position of the path
     * 
     * @param getFrom path of nodes that the ant has gone through
     * @param index   index of the node to get
     * @return int - the node that the ant has gone through
     */
    private int getFromList(ArrayList<Integer> getFrom, int index) {
        return getFrom.get(index);
    }

    /**
     * Remove a node from the path
     * 
     * @param listToRemove - ArrayList with the path of the ant
     * @param nodeToRemove - int with the node to remove from the path
     */
    private void removeFromList(ArrayList<Integer> listToRemove, int nodeToRemove) {
        listToRemove.remove(nodeToRemove);
    }

    /**
     * Add a node to the path
     * 
     * @param listToAdd - ArrayList with the path of the ant
     * @param nodeToAdd - int with the node to add to the path
     */
    private void addToList(ArrayList<Integer> listToAdd, int nodeToAdd) {
        listToAdd.add(nodeToAdd);
    }

    /**
     * gets the size of the path
     * 
     * @param listToCheck - ArrayList with the path of the ant
     * @return int - the size of the path
     */
    private int getSize(ArrayList<Integer> listToCheck) {
        return listToCheck.size();
    }

    /**
     * Get the cost between two nodes (a and b)
     * 
     * @param a - int with the node to check
     * @param b - int with the node to check
     * @return int - the cost between the nodes
     */
    private int getCost(int a, int b) {
        return colony.getCost(a, b);
    }

}
