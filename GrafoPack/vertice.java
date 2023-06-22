/**
 * This package contains classes related to graph operations.
 * It provides functionality for creating, graphs.
 */
package GrafoPack;

import java.util.ArrayList;

/**
 * Class that represents a vertex of a graph
 */
class vertice extends VerticeSuper {

    /**
     * List of pointers of this vertex
     */
    private final ArrayList<Edge> ListaEdges;

    /**
     * Creates a new vertex
     * 
     * @param elementoNovo Element that this vertex will have
     */
    public vertice(int elementoNovo) {
        super(elementoNovo);
        this.ListaEdges = new ArrayList<>();
    }

    /**
     * Creates a new pointer to a vertex
     * 
     * @param id    ID of the vertex
     * @param custo Cost of the pointer
     */
    void NovaLigacao(int id, int custo) {
        Edge NewPointer = new Edge(id, custo);
        ListaEdges.add(NewPointer);
    }

    /**
     * Checks if a vertex is connected to this vertex
     * 
     * @param novo Vertex to be checked
     * @return True if the vertex is connected to this vertex
     */
    boolean checkLig(VerticeSuper novo) {
        for (Edge x : ListaEdges) {
            if (x.GetVerticeInfo() == novo.GetVerticeInfo()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the information of the vertex
     * 
     * @return Element of the vertex
     */
    public int GetVerticeInfo() {
        return this.elemento;
    }

    /**
     * Get the list of pointers of this vertex
     * 
     * @return List of pointers of this vertex
     */
    ArrayList<Edge> getPonteiros() {
        return this.ListaEdges;
    }

    /**
     * Get the cost of a pointer to a vertex
     * 
     * @param Vertice Vertex to be checked
     * @return Cost of the pointer to the vertex
     */
    int GetCustoLig(VerticeSuper Vertice) {
        for (Edge x : this.ListaEdges) {
            if (x.GetVerticeInfo() == Vertice.GetVerticeInfo()) {
                return x.getCusto();
            }
        }
        return 0;
    }
}