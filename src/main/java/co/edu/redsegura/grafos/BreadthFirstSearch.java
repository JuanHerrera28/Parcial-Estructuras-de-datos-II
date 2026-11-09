/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.redsegura.grafos;

import co.edu.redsegura.excepciones.VertexNotFoundException;
import java.util.LinkedList;
import java.util.Queue;

public class BreadthFirstSearch {
    /**
 * Búsqueda en anchura (BFS). Recorre el grafo por niveles usando una cola
 * y una lista de visitados. Respeta la orientación de las conexiones
 * y no usa las que estén bloqueadas.
 */
    
     /** Un lugar visitado: quién lo descubrió (su padre) y en qué nivel está. */
    public static class Visit{
        private final String vertexId;
        private final String parentId;
        private final int level;
        
        
        public Visit(String vertexId, String parentId, int level){
            this.vertexId = vertexId;
            this.parentId = parentId;
            this.level = level;
        }

        public String getVertexId() {
            return vertexId;
        }

        public String getParentId() {
            return parentId;
        }

        public int getLevel() {
            return level;
        }
    }
    
    private final MixedGraph graph;
    /**
     * @param graph grafo a recorrer (no puede ser nulo)
     * @throws IllegalArgumentException si el grafo es nulo
     */
    public BreadthFirstSearch(MixedGraph graph){
        if(graph == null){
            throw new IllegalArgumentException("El grafo no puede ser nulo");
        }
        this.graph = graph;
    }
    /**
     * Recorre el grafo desde un vértice y devuelve los lugares alcanzados
     * en el orden en que se visitaron, con su padre y su nivel.
     * Solo se alcanzan los vértices a los que se puede llegar respetando
     * la orientación; en un grafo desconectado, el resto no aparece.
     * @throws VertexNotFoundException si el vértice de origen no existe
     */
    
    
    public LinkedList<Visit> traverse (String startId){
        if(!graph.hasvertex(startId)){
             throw new VertexNotFoundException(startId);
        }
        
        LinkedList<Visit> order = new LinkedList<>();
        LinkedList<String> visited = new LinkedList<>();
        Queue<Visit> queue = new LinkedList<>();
        
        
        
        
        visited.add(startId);
        queue.offer(new Visit(startId, null, 0));
        
        while (!queue.isEmpty()){
            Visit current = queue.poll();
            order.add(current);
            
            
            for(Neighbor n : graph.neighbors(current.getVertexId())){
                if(!visited.contains(n.getVertexId())){
                    visited.add(n.getVertexId());
                    queue.offer(new Visit(n.getVertexId(),current.getVertexId(),current.getLevel() + 1));
                }
            
            }
        }
        
        return order;
        
    }
    
     /** @return solo los IDs de los vértices, en orden de visita */
        /** @return solo los IDs de los vértices, en orden de visita */
    public LinkedList<String> order(String startId) {
        LinkedList<String> ids = new LinkedList<>();
        for (Visit v : traverse(startId)) {
            ids.add(v.getVertexId());
        }
        return ids;
    }

    /** @return el árbol BFS (nivel, vértice y padre) listo para imprimir */
    public String treeToString(String startId) {
        StringBuilder sb = new StringBuilder("ARBOL BFS desde " + startId + "\n");
        for (Visit v : traverse(startId)) {
            sb.append("Nivel ").append(v.getLevel()).append(": ")
              .append(v.getVertexId())
              .append(v.getParentId() == null ? " (origen)" : " (padre: " + v.getParentId() + ")")
              .append("\n");
        }
        return sb.toString();
    }

    
    
}
