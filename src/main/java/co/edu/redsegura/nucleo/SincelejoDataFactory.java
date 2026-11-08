/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.redsegura.nucleo;
import co.edu.redsegura.grafos.MixedGraph;
import co.edu.redsegura.modelo.Edge;
import co.edu.redsegura.modelo.vertex;


public class SincelejoDataFactory {
     private static final boolean DIRIGIDA = true;
    private static final boolean NO_DIRIGIDA = false;
    private SincelejoDataFactory(){
        //No se instancia
}
    
       /** @return un grafo nuevo con los datos originales del parcial */
    
    public static MixedGraph createGraph(){
    
        MixedGraph g = new MixedGraph();
        
        //Lugares (vertices)
        g.addVertex(new vertex("S01", "UAJS Sede C", "Universidad"));
        g.addVertex(new vertex("S02", "UAJS Sede E", "Universidad"));
        g.addVertex(new vertex("S03", "Universidad de Sucre", "Universidad"));
        g.addVertex(new vertex("S04", "CECAR", "Universidad"));
        g.addVertex(new vertex("S05", "Hospital Universitario", "Salud"));
        g.addVertex(new vertex("S06", "Clínica Las Américas", "Salud"));
        g.addVertex(new vertex("S07", "Estación Central SETP", "Transporte"));
        g.addVertex(new vertex("S08", "Terminal de Transporte", "Transporte"));
        g.addVertex(new vertex("S09", "Parque Santander", "Punto de encuentro"));
        g.addVertex(new vertex("S10", "Mercado de Sincelejo", "Comercio"));
        g.addVertex(new vertex("S11", "PEP Argelia", "Paradero"));
        g.addVertex(new vertex("S12", "Glorieta Sincelejito", "Movilidad"));
    
    
        
        //Conexiones de id, origen, destino, orientacion, minutos, etiqueta
        // Conexiones: id, origen, destino, orientación, minutos, etiqueta
        g.addEdge(new Edge("R01", "S01", "S07", DIRIGIDA, 4, "Corredor universitario"));
        g.addEdge(new Edge("R02", "S07", "S01", DIRIGIDA, 6, "Retorno por vía alterna"));
        g.addEdge(new Edge("R03", "S01", "S09", NO_DIRIGIDA, 5, "Corredor peatonal"));
        g.addEdge(new Edge("R04", "S02", "S01", NO_DIRIGIDA, 8, "Conexión entre sedes"));
        g.addEdge(new Edge("R05", "S02", "S06", DIRIGIDA, 10, "Ruta de atención"));
        g.addEdge(new Edge("R06", "S06", "S05", DIRIGIDA, 4, "Corredor clínico"));
        g.addEdge(new Edge("R07", "S05", "S09", NO_DIRIGIDA, 6, "Vía hospitalaria"));
        g.addEdge(new Edge("R08", "S09", "S10", NO_DIRIGIDA, 3, "Centro histórico"));
        g.addEdge(new Edge("R09", "S10", "S08", DIRIGIDA, 8, "Salida al terminal"));
        g.addEdge(new Edge("R10", "S08", "S10", DIRIGIDA, 9, "Ingreso desde terminal"));
        g.addEdge(new Edge("R11", "S03", "S12", NO_DIRIGIDA, 4, "Corredor Puerta Roja"));
        g.addEdge(new Edge("R12", "S12", "S09", NO_DIRIGIDA, 7, "Troncal al centro"));
        g.addEdge(new Edge("R13", "S04", "S11", NO_DIRIGIDA, 6, "Conexión universitaria"));
        g.addEdge(new Edge("R14", "S11", "S07", DIRIGIDA, 8, "Ruta SETP al centro"));
        g.addEdge(new Edge("R15", "S07", "S09", NO_DIRIGIDA, 3, "Transferencia central"));
        g.addEdge(new Edge("R16", "S07", "S08", DIRIGIDA, 12, "Ruta SETP al terminal"));
        g.addEdge(new Edge("R17", "S09", "S03", DIRIGIDA, 7, "Ruta hacia universidad"));
        g.addEdge(new Edge("R18", "S12", "S04", DIRIGIDA, 9, "Ruta hacia CECAR"));
    
        return g;
    }
    
}

