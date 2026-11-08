/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.redsegura.grafos;
import co.edu.redsegura.excepciones.DuplicateIdException;
import co.edu.redsegura.excepciones.VertexNotFoundException;
import co.edu.redsegura.modelo.Edge;
import co.edu.redsegura.modelo.vertex;
import java.util.LinkedList;

/**
 * Grafo mixto: puede tener conexiones dirigidas (arcos) y no dirigidas (aristas),
 * todas con peso y etiqueta. Guarda los datos en listas enlazadas y mantiene
 * el orden de inserción, que usan las representaciones.
 */

public class MixedGraph {
    
    private final LinkedList<vertex> vertices = new LinkedList<>();
    private final LinkedList<Edge> edges = new LinkedList<>();
    
     /**
     * Agrega un vértice al grafo.
     * @throws IllegalArgumentException si el vértice es nulo
     * @throws DuplicateIdException     si ya existe un vértice con ese ID
     */
    
    public void addVertex(vertex Vertex){
        if (Vertex == null){
        throw new IllegalArgumentException("El vertice no puede ser nulo");
        }
        if (hasvertex(Vertex.getId())){
          throw new DuplicateIdException(Vertex.getId());
        }
        vertices.add(Vertex);
    }
    
    /**
     * Agrega una conexión al grafo.
     * @throws IllegalArgumentException si la conexión es nula
     * @throws DuplicateIdException     si ya existe una conexión con ese ID
     * @throws VertexNotFoundException  si el origen o el destino no existen
     */
    
    public void addEdge(Edge edge){
        if (edge == null){
            throw new IllegalArgumentException("La conexión no puede ser nula"); 
        }
        if (findEdge(edge.getId()) != null) {
            throw new DuplicateIdException(edge.getId());
        }
        if (!hasvertex(edge.getOrigin())){
            throw new VertexNotFoundException(edge.getOrigin());
        }
        if(!hasvertex(edge.getDestination())){
            throw new VertexNotFoundException(edge.getDestination());
        }
        edges.add(edge);
    
    }
    
    /** @return true si existe un vértice con ese ID */
      public boolean hasvertex(String id) {
        return findvertex(id) != null;
    }

    /**
     * @return el vértice con ese ID
     * @throws VertexNotFoundException si no existe
     */
      
    public vertex getvertex(String id){
        vertex v = findvertex(id);
        if (v==null){
            throw new VertexNotFoundException(id);
        }
        
        return v;
    }
    
    public Edge getEdge(String id) {
        Edge e = findEdge(id);
        if (e == null) {
            throw new IllegalArgumentException("La conexión no existe: " + id);
        }
        return e;
    }
    
    public LinkedList<vertex> getvertices(){
        return new LinkedList<>(vertices);
    }
    
    public LinkedList<Edge> getEdges(){
        return new LinkedList<>(edges);
    }
    
    public int VertexCount(){
        return vertices.size();
    }
    public int edgeCount(){
        return edges.size();
    }
    
     /**
     * Bloquea o desbloquea una conexión sin borrarla.
     * @throws IllegalArgumentException si la conexión no existe
     */
    
     public void setEdgeBlocked(String edgeId, boolean blocked) {
        getEdge(edgeId).setBlocked(blocked);
    }
    
     /**
     * Vecinos a los que se puede llegar desde un vértice, respetando la orientación:
     * una conexión dirigida solo se usa de origen a destino y una no dirigida
     * en ambos sentidos. Las conexiones bloqueadas se ignoran.
     * @throws VertexNotFoundException si el vértice no existe
     */
    public LinkedList<Neighbor> neighbors(String id){
        if (!hasvertex(id)){
         throw new VertexNotFoundException(id);
        }
        
        LinkedList<Neighbor> result = new LinkedList<>();
        for (Edge e : edges) {
            if (e.isBlocked()) {
                continue;
            }
            
            if (e.getOrigin().equals(id)){
                result.add(new Neighbor(e.getDestination(),e.getWeight(),e.getId()));
            }
            else if (!e.isDirected() && e.getDestination().equals(id)) {
                result.add(new Neighbor(e.getOrigin(), e.getWeight(), e.getId()));
            }   
            }
        return result;
    }
    
    private vertex findvertex(String id){
        for (vertex v : vertices){
            if(v.getId().equals(id)){
            return v;
            }
        }
        return null;
    }
    
    private Edge findEdge(String id){
        for(Edge e : edges){
            if(e.getId().equals(id)){
            return e;
            } 
        }
        
        return null;
    }
    
 
}
    
    

