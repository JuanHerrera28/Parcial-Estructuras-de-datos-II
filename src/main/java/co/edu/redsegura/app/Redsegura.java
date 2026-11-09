/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package co.edu.redsegura.app;

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
GraphRepresentations rep = new GraphRepresentations(g);
System.out.println(rep.adjacencyListToString());
        
        System.out.println(rep.adjacencyMatrixToString());
        System.out.println(rep.incidenceMatrixToString());
       
    }
    
   
}
