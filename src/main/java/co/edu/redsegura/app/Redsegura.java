/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package co.edu.redsegura.app;

import co.edu.redsegura.grafos.MixedGraph;
import co.edu.redsegura.modelo.Edge;
import co.edu.redsegura.modelo.vertex;

/**
 *
 * @author juand
 */
public class Redsegura {

    public static void main(String[] args) {
        MixedGraph g = new MixedGraph();
g.addVertex(new vertex("S01", "UAJS Sede C", "Universidad"));
g.addVertex(new vertex("S07", "Estación Central SETP", "Transporte"));
g.addVertex(new vertex("S09", "Parque Santander", "Punto de encuentro"));
g.addEdge(new Edge("R01", "S01", "S07", true, 4, "Corredor universitario"));
g.addEdge(new Edge("R03", "S01", "S09", false, 5, "Corredor peatonal"));

System.out.println("Vecinos de S01: " + g.neighbors("S01"));
System.out.println("Vecinos de S07: " + g.neighbors("S07"));
System.out.println("Vecinos de S09: " + g.neighbors("S09"));
        
        
       
    }
    
   
}
