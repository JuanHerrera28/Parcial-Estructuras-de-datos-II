/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.redsegura.grafos;

/**
 *
 * @author juand
 */
public class Neighbor {
    private final String vertexId;
    private final double weight;
    private final String edgeId;
    
    
    
    public Neighbor(String vertexId, double weight, String edgeId){
        this.vertexId = vertexId;
        this.weight = weight;
        this.edgeId = edgeId;
    }

    public String getVertexId() {
        return vertexId;
    }

    public double getWeight() {
        return weight;
    }

    public String getEdgeId() {
        return edgeId;
    }
    
    @Override
    public String toString(){
        return vertexId + " (" + weight + " min, " + edgeId + ")";
    }
}
