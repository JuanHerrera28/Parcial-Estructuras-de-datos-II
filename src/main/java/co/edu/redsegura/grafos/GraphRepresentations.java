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
    
    
    /**
     * Matriz de adyacencia ponderada. Filas = origen, columnas = destino,
     * en el orden de inserción de los vértices. La diagonal vale 0 y
     * Double.POSITIVE_INFINITY indica que no hay conexión directa.
     * Una conexión no dirigida llena [i][j] y [j][i]; una dirigida, solo [i][j].
     */
    
    public LinkedList<LinkedList<Double>> adjacencyMatrix(){
        LinkedList<vertex> vertices = graph.getvertices();
        int n = vertices.size();
        LinkedList<LinkedList<Double>> matrix = new LinkedList<>();
        
        for(int i = 0; i < n; i++){
            LinkedList<Double> row = new LinkedList<>();
            for(int j = 0; j < n; j++){
                row.add(i == j ? 0.0:Double.POSITIVE_INFINITY);
            }
            matrix.add(row);
        }
        
        for(Edge e : graph.getEdges()){
            int i = indexOf(vertices, e.getOrigin());
            int j = indexOf(vertices, e.getDestination());
            
            setMin(matrix, i ,j, e.getWeight());
            if(!e.isDirected()){
                setMin(matrix, j, i, e.getWeight());
            }
        }
            return matrix;
        }
     /** @return la matriz de adyacencia lista para imprimir */
    public String adjacencyMatrixToString(){
        LinkedList<vertex> vertices = graph.getvertices();
        LinkedList<LinkedList<Double>> matrix = adjacencyMatrix();
        StringBuilder sb = new StringBuilder("MATRIX DE ADYACENCIA PONDERADA\n");
        
        sb.append(String.format("%-5s", ""));
        for(vertex v : vertices){
         sb.append(String.format("%5s", v.getId()));
        }
        sb.append("\n");
        
        Iterator<vertex> it = vertices.iterator();
        for(LinkedList<Double> row : matrix){
            sb.append(String.format("%-5s", it.next().getId()));
            
            for(double value : row){
                sb.append(String.format("%5s", formatWeight(value)));
            }
            sb.append("\n");
        }
        return sb.toString();
        
        
    }
    
    private void setMin(LinkedList<LinkedList<Double>> matrix, int i, int j, double weight){
        if( weight < matrix.get(i).get(j)){
            matrix.get(i).set(j, weight);
        }
    }
    
    private int indexOf(LinkedList<vertex> vertices, String id){
        int index = 0;
        for(vertex v : vertices) {
            if(v.getId().equals(id)){
                return index;
            }index ++;
        } return -1;
                
            }
    
   private String formatWeight(double value) {
        if (Double.isInfinite(value)) {
            return "INF";
        }
        return String.valueOf((int) value);
    }
   
   
   /**
     * Matriz de incidencia: filas = vértices, columnas = conexiones, ambos en
     * orden de inserción. En una conexión dirigida el origen vale -1 y el destino +1;
     * en una no dirigida ambos extremos valen +1. El resto de celdas vale 0.
     */
   
   public LinkedList<LinkedList<Integer>> incidenceMatrix(){
       LinkedList<vertex> vertices = graph.getvertices();
       LinkedList<Edge> edges = graph.getEdges();
       LinkedList<LinkedList<Integer>> matrix = new LinkedList<>();
       
       
       for (vertex v : vertices) {
            LinkedList<Integer> row = new LinkedList<>();
            for (Edge e : edges) {
                int value = 0;
                if (e.isDirected()) {
                    if (e.getOrigin().equals(v.getId())) {
                        value = -1;
                    } else if (e.getDestination().equals(v.getId())) {
                        value = 1;
                    }
                } else if (e.getOrigin().equals(v.getId())
                        || e.getDestination().equals(v.getId())) {
                    value = 1;
                }
                row.add(value);
            }
            matrix.add(row);
        }
        return matrix;
       }
    /** @return la matriz de incidencia lista para imprimir */
   
   public String incidenceMatrixToString(){
        LinkedList<vertex> vertices = graph.getvertices();
        LinkedList<Edge> edges = graph.getEdges();
        LinkedList<LinkedList<Integer>> matrix = incidenceMatrix();
   
        StringBuilder sb = new StringBuilder("MATRIX DE INCIDENCIA\n");
        sb.append(String.format("%-5s", ""));
        for (Edge e : edges) {
            sb.append(String.format("%5s", e.getId()));
        }
        sb.append("\n");
        
        
        Iterator<vertex> it = vertices.iterator();
        for (LinkedList<Integer> row : matrix) {
            sb.append(String.format("%-5s", it.next().getId()));
            for (int value : row) {
                sb.append(String.format("%5d", value));
            }
            sb.append("\n");
        } return sb.toString();  
   }
    }    

