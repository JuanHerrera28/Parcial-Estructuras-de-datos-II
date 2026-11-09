/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package co.edu.redsegura.app;

import co.edu.redsegura.grafos.BreadthFirstSearch;
import co.edu.redsegura.grafos.GraphRepresentations;
import co.edu.redsegura.grafos.MixedGraph;
import co.edu.redsegura.modelo.Edge;
import co.edu.redsegura.modelo.vertex;
import co.edu.redsegura.nucleo.SincelejoDataFactory;

/**
 *
 * @author juand
 */
public class Redsegura {

    public static void main(String[] args) {
    

MixedGraph g = SincelejoDataFactory.createGraph();

BreadthFirstSearch bfs = new BreadthFirstSearch(g);

System.out.println(bfs.order("S02"));
System.out.println(bfs.treeToString("S02"));
    }
    
   
}
