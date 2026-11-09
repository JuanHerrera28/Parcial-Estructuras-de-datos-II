/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.redsegura.grafos;
import co.edu.redsegura.modelo.Edge;
import co.edu.redsegura.modelo.vertex;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Representaciones de un grafo mixto: lista de adyacencia,
 * matriz de adyacencia ponderada y matriz de incidencia.
 * Todas usan el mismo orden de vértices y de conexiones: el de inserción.
 * Muestran la estructura completa del grafo, incluidas las conexiones bloqueadas.
 */

public class GraphRepresentations {
    private final MixedGraph graph;
    
    public GraphRepresentations(MixedGraph graph){
        if(graph == null){
            throw new IllegalArgumentException("El grafo no puede ser nulo");
        }
        
        this.graph = graph;
       }
        /**
     * Lista de adyacencia: una fila por vértice (en orden de inserción) con
     * los vecinos alcanzables. Una conexión dirigida aparece solo en la fila
     * de su origen; una no dirigida, en la de sus dos extremos.
     */
    
    
    public LinkedList<LinkedList<Neighbor>> adjacencyList(){
        LinkedList<vertex> vertices = graph.getvertices();
        LinkedList<Edge> edges = graph.getEdges();
        LinkedList<LinkedList<Neighbor>> list = new LinkedList<>();
        
        
        for (vertex v : vertices){
            LinkedList<Neighbor> row = new LinkedList<>();
            for (Edge e : edges){
                if(e.getOrigin().equals(v.getId())){
                    row.add(new Neighbor(e.getDestination(),e.getWeight(),e.getId()));
                    }else if (!e.isDirected() && e.getDestination().equals(v.getId())){
                        row.add(new Neighbor(e.getOrigin(),e.getWeight(),e.getId()));
                        }
            }
            list.add(row);
        }
        return list;
    }
    /** @return la lista de adyacencia lista para imprimir */
    public String adjacencyListToString(){
        LinkedList<LinkedList<Neighbor>> list = adjacencyList();
        Iterator<vertex> it = graph.getvertices().iterator();
        StringBuilder sb = new StringBuilder("Lista de Adyacencia\n");
        
        for(LinkedList<Neighbor> row : list){
            vertex v = it.next();
           sb.append(v.getId()).append(" -> ").append(row).append("\n");
        }
        return sb.toString();
        
    } 
}
