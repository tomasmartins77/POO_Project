package PrincipalMain;

import DSS.*;
import ACO.*;
import GrafoPack.*;
import LeitorDeArgs.*;

import java.util.*;

/**
 * The main class of the program that runs everything
 * It uses the ACO package to manage the ant colony optimization algorithm
 * It uses the DSS package to manage the event simulation
 * It uses the GrafoPack package to manage the graph
 * It uses the LeitorDeArgs package to manage the command-line arguments
 */
public class Main {
    public static void main(String[] args) {

        GrafoInterface Grafo;
        AntColonyInterface colonia;
        EventSimulation Simulacao;
        ArrayList<Number> constantes;

        stratChooser strategies = new stratChooser();
        strategies.setStrategy("-f", new fileStrategy());
        strategies.setStrategy("-r", new readStrategy());

        strategies.readArgs(args);
        Grafo = strategies.getGrafo();
        Grafo.MostrarVerticeInfo();
        constantes = strategies.getConstantes();

        colonia = new AntColony(Grafo, (Integer) constantes.get(0),
                (Float) constantes.get(1), (Float) constantes.get(2), (Float) constantes.get(6),
                (Integer) constantes.get(7), (Float) constantes.get(5));

        Simulacao = new EventManager(colonia, (float) constantes.get(8), (float) constantes.get(4),
                (float) constantes.get(3));

        Simulacao.simular(Grafo.totalEdges() + 1 + (Integer) constantes.get(7));

    }
}