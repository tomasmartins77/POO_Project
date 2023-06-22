/**
 * This package contains classes related to graph operations.
 * It provides functionality for creating, graphs.
 */
package GrafoPack;

import java.util.Hashtable;
import java.util.Random;
import java.util.ArrayList;

/**
 * Esta classe representa o grafo
 */
public class Grafo implements GrafoInterface {

    /**
     * max number of vertices
     */
    private final int MaxVertices;

    /**
     * array of vertices
     */
    private final vertice[] Vertices;

    /**
     * new vertices
     */
    private int verticenovo;

    /**
     * total number of edges
     */
    private int Totaledges;

    /**
     * total weight of the edges
     */
    private int somarEdges;

    /**
     * Create a graph with a number of vertices and edges
     * 
     * @param verticeNumero numero de vertices
     * @param edges         numero de edges
     * @param peso          peso maximo das edges
     */
    public Grafo(int verticeNumero, int edges, int peso) {
        this.MaxVertices = verticeNumero;
        this.Vertices = new vertice[this.MaxVertices];
        this.verticenovo = 0;
        this.Totaledges = 0;

        this.GenerateGraphWHamiltonCycle(peso, edges);
    }

    /**
     * Create a graph with the information of a matrix
     * 
     * @param verticeNumero numero de vertices
     * @param matriz        matriz de adjacencia
     */
    public Grafo(int verticeNumero, int[][] matriz) {
        this.MaxVertices = verticeNumero;
        this.Vertices = new vertice[verticeNumero];
        this.verticenovo = 0;

        for (int i = 0; i < this.MaxVertices; i++) {
            this.CriarVertice(i + 1);
        }
        for (int i = 0; i < this.MaxVertices; i++) {
            for (int j = i; j < this.MaxVertices; j++) { // diagonal superior
                if (matriz[i][j] != 0) {
                    this.AdicionarLiga(i + 1, j + 1, matriz[i][j]);
                }
            }
        }
    }

    /**
     * gets the total number of edges
     * 
     * @return return the total of edges
     */
    public int totalEdges() {
        return this.Totaledges;
    }

    /**
     * gets the edges of a vertex
     * 
     * @param vertice vertex to know
     * @return return all edges of a vertex
     */
    @Override
    public Hashtable<Integer, Integer> getEdges(int vertice) {
        Hashtable<Integer, Integer> edges = new Hashtable<>();
        ArrayList<Edge> edgesaux;
        vertice aux = this.Vertices[vertice - 1];
        edgesaux = aux.getPonteiros();

        for (Edge x : edgesaux) {
            edges.put(x.GetVerticeInfo(), x.getCusto());
        }

        return edges;

    }

    /**
     * Get the total of vertex
     * 
     * @return return all edges of a graph
     */
    @Override
    public int totalVertex() {
        return this.MaxVertices;
    }

    /**
     * Este metodo cria um vertice e guarda-o na estrutura dos grafos
     *
     * @param verticeInfo String que identifica o vertice (isto talvez se muda para
     *                    um inteiro)
     */
    private void CriarVertice(int verticeInfo) {

        this.Vertices[this.verticenovo] = new vertice(verticeInfo);
        this.verticenovo++;

    }

    /**
     * gets the total sum of all edges
     * 
     * @return return the sum of all edges
     */
    @Override
    public int totalEdgesSum() {

        return this.somarEdges;

    }

    /**
     * metodo que cria o graph com o hamilton cycle
     *
     * @param peso  peso maximo das edges
     * @param edges numero de edges do grafo
     */
    private void GenerateGraphWHamiltonCycle(int peso, int edges) {

        int aux, aux1, flag = 0;
        ArrayList<Integer> EdgeAux = new ArrayList<>();
        ArrayList<Integer> ligFeitas = new ArrayList<>();
        Random rand = new Random();

        for (int i = 0; i < this.MaxVertices; i++) {
            this.CriarVertice(i + 1);
            EdgeAux.add(i + 1);
        }

        ligFeitas.add(1);
        EdgeAux.remove(0);
        // hamiltonCycle
        for (int i = 1; i < this.MaxVertices; i++, edges--) {
            aux = rand.nextInt(EdgeAux.size());
            ligFeitas.add(EdgeAux.get(aux));
            this.AdicionarLiga(ligFeitas.get(i), ligFeitas.get(i - 1), rand.nextInt(peso) + 1);
            EdgeAux.remove(aux);

        }
        this.AdicionarLiga(ligFeitas.get(ligFeitas.size() - 1), ligFeitas.get(0), rand.nextInt(peso) + 1);

        // resto do grafo
        while (edges > 0) {
            aux = rand.nextInt(this.MaxVertices);
            aux1 = rand.nextInt(this.MaxVertices);
            flag++;
            if (aux != aux1 && !this.Vertices[aux].checkLig(this.Vertices[aux1])) {
                flag = 0;
                this.AdicionarLiga(this.Vertices[aux].GetVerticeInfo(), this.Vertices[aux1].GetVerticeInfo(),
                        rand.nextInt(peso) + 1);
                edges--;

            }
            if (flag == 50) {
                break;
            }
        }
    }

    /**
     * Criar uma ligacao entre vertices
     *
     * @param a     vertice 1 ident
     * @param b     vertice 2 ident
     * @param custo custo da ligacao
     */

    public void AdicionarLiga(int a, int b, int custo) {
        vertice v1 = null;
        vertice v2 = null;

        for (int i = 0; i < this.verticenovo; i++) {

            if (this.Vertices[i].GetVerticeInfo() == a) {
                v1 = this.Vertices[i];
            }

            if (this.Vertices[i].GetVerticeInfo() == b) {
                v2 = this.Vertices[i];
            }
        }
        if (v1 != null && v2 != null) {
            v1.NovaLigacao(b, custo);
            v2.NovaLigacao(a, custo);
        }
        this.somarEdges += custo;
        this.Totaledges += 1;
    }

    /**
     * Print da informacao sobre um vertice especifico
     *
     */
    public void MostrarVerticeInfo() {

        int[][] matriz = new int[MaxVertices][MaxVertices];
        System.out.println("With graph:");

        for (int i = 0; i < MaxVertices; i++) {

            for (int l = 0; l < MaxVertices; l++) {

                matriz[i][l] = Vertices[i].GetCustoLig(Vertices[l]);
                System.out.print(" " + matriz[i][l]);

            }
            System.out.println();

        }

    }

    /**
     * get the cost of an edge between two vertex
     * 
     * @param a vertice 1
     * @param b vertice 2
     * @return return the cost of an edge between two vertex
     */
    public int GetCusto(int a, int b) {
        return Vertices[a - 1].GetCustoLig(Vertices[b - 1]);
    }
}