/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.redsegura.grafos;

import co.edu.redsegura.excepciones.VertexNotFoundException;
import co.edu.redsegura.modelo.vertex;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * Solucionador de caminos mínimos usando Dijkstra.
 * Usa PriorityQueue para los vértices a explorar y LinkedList para las estructuras internas.
 */
   public class DijkstraSolver {

    private static class NodeRecord implements Comparable<NodeRecord> {
        final String vertexId;
        final double distance;

        NodeRecord(String vertexId, double distance) {
            this.vertexId = vertexId;
            this.distance = distance;
        }

        @Override
        public int compareTo(NodeRecord other) {
            return Double.compare(this.distance, other.distance);
        }
    }

    private static class RouteEntry {
        String vertexId;
        double distance;
        String previous;

        RouteEntry(String vertexId, double distance, String previous) {
            this.vertexId = vertexId;
            this.distance = distance;
            this.previous = previous;
        }
    }

    private final MixedGraph graph;

    public DijkstraSolver(MixedGraph graph) {
        if (graph == null) {
            throw new IllegalArgumentException("El grafo no puede ser nulo");
        }
        this.graph = graph;
    }

    private LinkedList<RouteEntry> calculatePaths(String startId) {
        if (!graph.hasvertex(startId)) {
            throw new VertexNotFoundException(startId);
        }

        LinkedList<RouteEntry> routeTable = new LinkedList<>();
        
        for (vertex v : graph.getvertices()) {
            if (v.getId().equals(startId)) {
                routeTable.add(new RouteEntry(v.getId(), 0.0, null));
            } else {
                routeTable.add(new RouteEntry(v.getId(), Double.POSITIVE_INFINITY, null));
            }
        }

        PriorityQueue<NodeRecord> pq = new PriorityQueue<>();
        pq.offer(new NodeRecord(startId, 0.0));
        
        LinkedList<String> settled = new LinkedList<>();

        while (!pq.isEmpty()) {
            NodeRecord current = pq.poll();
            String u = current.vertexId;

            if (settled.contains(u)) {
                continue;
            }
            settled.add(u);

            double distU = getDistance(routeTable, u);

            for (Neighbor n : graph.neighbors(u)) {
                String v = n.getVertexId();
                if (settled.contains(v)) {
                    continue;
                }

                double weight = n.getWeight();
                double newDist = distU + weight;

                if (newDist < getDistance(routeTable, v)) {
                    updateTable(routeTable, v, newDist, u);
                    pq.offer(new NodeRecord(v, newDist));
                }
            }
        }

        return routeTable;
    }

    public LinkedList<String> getShortestPath(String startId, String endId) {
        if (!graph.hasvertex(endId)) {
            throw new VertexNotFoundException(endId);
        }

        LinkedList<RouteEntry> table = calculatePaths(startId);
        LinkedList<String> path = new LinkedList<>();
        
        if (getDistance(table, endId) == Double.POSITIVE_INFINITY) {
            return path; 
        }

        String current = endId;
        while (current != null) {
            path.addFirst(current);
            current = getPrevious(table, current);
        }

        return path;
    }

    public double getShortestDistance(String startId, String endId) {
        LinkedList<RouteEntry> table = calculatePaths(startId);
        return getDistance(table, endId);
    }

    private double getDistance(LinkedList<RouteEntry> table, String vertexId) {
        for (RouteEntry entry : table) {
            if (entry.vertexId.equals(vertexId)) {
                return entry.distance;
            }
        }
        return Double.POSITIVE_INFINITY;
    }

    private String getPrevious(LinkedList<RouteEntry> table, String vertexId) {
        for (RouteEntry entry : table) {
            if (entry.vertexId.equals(vertexId)) {
                return entry.previous;
            }
        }
        return null;
    }

    private void updateTable(LinkedList<RouteEntry> table, String vertexId, double newDistance, String newPrevious) {
        for (RouteEntry entry : table) {
            if (entry.vertexId.equals(vertexId)) {
                entry.distance = newDistance;
                entry.previous = newPrevious;
                break;
            }
        }
    }
   }
