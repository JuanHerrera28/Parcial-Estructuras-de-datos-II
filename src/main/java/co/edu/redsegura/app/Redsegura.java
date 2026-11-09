/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package co.edu.redsegura.app;

import co.edu.redsegura.excepciones.VertexNotFoundException;
import co.edu.redsegura.grafos.BreadthFirstSearch;
import co.edu.redsegura.grafos.DepthFirstSearch;
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
DepthFirstSearch dfs = new DepthFirstSearch(g);

System.out.println("DFS desde S02: " + dfs.order("S02"));
for (LinkedList<String> c : dfs.getCycles()) {
    System.out.println("  ciclo: " + c);
}
System.out.println("Hay ciclos? " + dfs.hasCycle());
System.out.println("Componentes debiles: " + dfs.countWeakComponents());
System.out.println(dfs.weakComponents());

try {
    dfs.order("S99");
} catch (VertexNotFoundException e) {
    System.out.println("Q7 controlado: " + e.getMessage());
}
   
}}
