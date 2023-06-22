/**
 * This package contains classes related to graph operations.
 * It provides functionality for creating, graphs.
 */
package GrafoPack;

/**
 * Class that represents a vertex of a graph
 */
public class VerticeSuper {

    /**
     * Element of the vertex
     */
    int elemento;

    /**
     * Creates a new vertex
     * 
     * @param elementoNovo Element that this vertex will have
     */
    VerticeSuper(int elementoNovo) {
        this.elemento = elementoNovo;
    }

    /**
     * Get the information of the vertex
     * 
     * @return Element of the vertex
     */
    int GetVerticeInfo() {
        return this.elemento;
    }
}
