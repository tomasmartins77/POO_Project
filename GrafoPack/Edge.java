/**
 * This package contains classes related to graph operations.
 * It provides functionality for creating, graphs.
 */
package GrafoPack;

/**
 * Class that represents a pointer to a vertex in a graph.
 */
class Edge extends VerticeSuper {

    /**
     * Weight of the pointer
     */
    private final int custo;

    /**
     * Creates a new pointer to a vertex
     * 
     * @param elementoLig - element to which the pointer points
     * @param weight      - weight of the pointer
     */
    Edge(int elementoLig, int weight) {

        super(elementoLig);
        this.custo = weight;

    }

    /**
     * Get of the weight of the pointer
     * 
     * @return weight of the pointer
     */
    int getCusto() {
        return this.custo;
    }

}
