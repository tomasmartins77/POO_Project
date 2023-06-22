/**
 * The ACO package contains classes and interfaces related to Ant Colony Optimization (ACO) algorithm.
 * ACO is an algorithm inspired by the foraging behavior of ants that can be used to solve optimization problems in
 * path finding and graph traversal.
 *
 */
package ACO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import GrafoPack.*;

/**
 * Class that represents the ant colony
 * Implements the AntColonyInterface
 */
public class AntColony implements AntColonyInterface {
    /**
     * The colony of ants use in the Ant Colony Optimization algorithm.
     */
    private final ArrayList<Ant> colony;

    /**
     * The node index representing the nest in the graph.
     */
    private final int nest_node;

    /**
     * The parameter controlling the influence of pheromone trails in the ACO algorithm.
     */
    private final float beta;

    /**
     * The parameter controlling the influence of weight information in the ACO algorithm.
     */
    private final float alpha;

    /**
     * The parameter controlling the influence of pheromone updates in the ACO algorithm.
     */
    private final float gamma;

    /**
     * The size of the ant colony.
     */
    private final int ant_colony_size;

    /**
     * The graph interface used in the ACO algorithm.
     */
    private final GrafoInterface Grafo;

    /**
     * The pheromone levels on the edges of the graph.
     */
    private final HashMap<Integer, Hashtable<Integer, Feromona>> pheromones;

    /**
     * The total weights of the graph edges.
     */
    private final int totalWeights;

    /**
     * The evaporation rate of pheromones in the ACO algorithm.
     */
    private final float ro;

    /**
     * The total number of vertices in the graph.
     */
    private final int totalVertex;

    /**
     * @param Graph           - graph to be used
     * @param nest_node       - node to be used as nest
     * @param alpha           - value for calculating the probability of choosing a
     *                        node
     * @param beta            - value for calculating the probability of choosing a
     *                        node
     * @param gamma           - value for calculating the level of pheromone
     * @param ant_colony_size - number of ants in the colony
     * @param ro              - value for decreasing pheromones
     */
    public AntColony(GrafoInterface Graph, int nest_node, float alpha, float beta, float gamma,
            int ant_colony_size, float ro) {
        this.Grafo = Graph;
        this.nest_node = nest_node;
        this.beta = beta;
        this.alpha = alpha;
        this.gamma = gamma;
        this.ant_colony_size = ant_colony_size;
        this.colony = new ArrayList<>();
        this.ro = ro;
        this.totalWeights = Grafo.totalEdgesSum();
        this.totalVertex = Grafo.totalVertex();
        this.pheromones = new HashMap<>();
        initializePheromones(); // initialize the pheromones for each edge of the graph with 0
        createAnts(); // create all the ants
    }

    /**
     * Initialize the pheromones for each edge in the graph
     */
    private void initializePheromones() {

        for (int i = 1; i <= totalVertex; i++) {
            Hashtable<Integer, Feromona> pheromonesFromNode = new Hashtable<>();
            pheromones.put(i, pheromonesFromNode);
        }

        for (int i = 1; i <= totalVertex; i++) {
            Hashtable<Integer, Integer> possibleNodes = getWeightsFromNode(i);
            for (int j = 1; j <= totalVertex; j++) {
                if (possibleNodes.containsKey(j)) {
                    if (pheromones.get(j).get(i) == null) {
                        Feromona aux = new Feromona(totalWeights, gamma, ro);
                        pheromones.get(i).put(j, aux);
                        pheromones.get(j).put(i, aux);
                    }
                }
            }
        }
    }

    /**
     *  get the pheromone map
     *  @return the pheromone map
     */
    public HashMap<Integer, Hashtable<Integer, Feromona>> getPheromones() {
        return this.pheromones;
    }

    /**
     * Set the level of pheromones in the edges that the ant took
     * 
     * @param path - path that the ant took
     */
    void setPheromones(ArrayList<Integer> path) {
        int currentNode;
        int nextNode;
        int sumOfWeights = sumOfWeightsPath(path);

        for (int i = 0; i < path.size() - 1; i++) {
            currentNode = path.get(i);
            nextNode = path.get(i + 1);

            setPheromone(currentNode, nextNode, sumOfWeights);
        }

        setPheromone(path.get(path.size() - 1), nest_node, sumOfWeights);
    }

    /**
     * Get the sum of weights of a path
     * 
     * @param path - path to calculate the sum of weights
     * @return the sum of weights of the path
     */
    int sumOfWeightsPath(ArrayList<Integer> path) {
        int sum = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            sum += getCost(path.get(i), path.get(i + 1));
        }
        sum += getCost(path.get(path.size() - 1), this.nest_node);
        return sum;
    }

    /**
     * Set the level of pheromones in the edge between two nodes
     * 
     * @param currentNode  - current node
     * @param nextNode     - next node
     * @param sumOfWeights - sum of weights of the path
     */
    private void setPheromone(int currentNode, int nextNode, int sumOfWeights) {
        pheromones.get(currentNode).get(nextNode).setPheromone(sumOfWeights);
        pheromones.get(nextNode).get(currentNode).setPheromone(sumOfWeights);
    }

    /**
     * Get the ants in the colony
     * 
     * @return the ants in the colony
     */
    public ArrayList<Ant> getAnts() {
        return colony;
    }

    /**
     * Create all ants in the colony
     */
    private void createAnts() {
        Ant ant;
        for (int i = 0; i < ant_colony_size; i++) {
            ant = new Ant(this);
            colony.add(ant);
        }
    }

    /**
     * Get the cost between two nodes in the graph
     * 
     * @param node1 - first node
     * @param node2 - second node
     * @return the cost of the edge between node1 and node2
     */
    int getCost(int node1, int node2) {             // é para estar visibilidade=Package ???
        return Grafo.GetCusto(node1, node2);
    }

    /**
     * Get the leve of pheromone in edge of node
     * 
     * @param node - node to get the pheromones from
     * @return the pheromones from the node
     */
    Hashtable<Integer, Feromona> getPheromonesFromNode(int node) {
        return pheromones.get(node);
    }

    /**
     * Get the weights for the adjacent nodes of a node
     * 
     * @param node - node to get the weights from
     * @return the weights from the adjacent nodes of the node
     */
    Hashtable<Integer, Integer> getWeightsFromNode(int node) {
        return Grafo.getEdges(node);
    }

    /**
     * Get the alpha value
     *
     * @return the alpha value
     */
    float getAlpha() {
        return alpha;
    }

    /**
     * Get the beta value
     *
     * @return the beta value
     */
    float getBeta() {
        return beta;
    }

    /**
     * Get the nest node
     *
     * @return the nest node
     */
    int getNestNode() {
        return nest_node;
    }

    /**
     * Get the total vertex of the graph
     *
     * @return the total vertex of the graph
     */
    int getTotalVertex() {
        return totalVertex;
    }

}
