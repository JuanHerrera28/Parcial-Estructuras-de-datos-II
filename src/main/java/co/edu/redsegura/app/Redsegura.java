/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package co.edu.redsegura.app;

import co.edu.redsegura.excepciones.VertexNotFoundException;
import co.edu.redsegura.grafos.BreadthFirstSearch;
import co.edu.redsegura.grafos.DepthFirstSearch;
import co.edu.redsegura.grafos.DijkstraSolver;
import co.edu.redsegura.grafos.GraphRepresentations;
import co.edu.redsegura.grafos.MixedGraph;
import co.edu.redsegura.modelo.Edge;
import co.edu.redsegura.modelo.vertex;
import co.edu.redsegura.nucleo.SincelejoDataFactory;
import java.util.LinkedList;

/**
 *
 * @author juand
 */
public class Redsegura {

    public static void main(String[] args) {
        MixedGraph g = SincelejoDataFactory.createGraph();
        DijkstraSolver dijkstra = new DijkstraSolver(g);

        System.out.println("--- Q5: Ruta Optima de S02 a S08 ---");
        System.out.println("Camino: " + dijkstra.getShortestPath("S02", "S08"));
        System.out.println("Costo total (minutos): " + dijkstra.getShortestDistance("S02", "S08"));

        System.out.println("\n--- Q6: Ruta S02 a S08 con R16 bloqueada ---");
        g.setEdgeBlocked("R16", true); 
        System.out.println("Nuevo Camino: " + dijkstra.getShortestPath("S02", "S08"));
        System.out.println("Nuevo Costo (minutos): " + dijkstra.getShortestDistance("S02", "S08"));
    }
}