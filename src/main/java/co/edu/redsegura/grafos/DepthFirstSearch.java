/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.redsegura.grafos;



import co.edu.redsegura.excepciones.VertexNotFoundException;
import co.edu.redsegura.modelo.vertex;
import java.util.LinkedList;



/**
 * Búsqueda en profundidad (DFS). Respeta la orientación de las conexiones
 * y no usa las bloqueadas. Detecta ciclos con dos listas (en proceso y
 * terminados) y cuenta componentes débilmente conexos ignorando la orientación.
 */
public class DepthFirstSearch {

    /** Un lugar visitado: su padre en el árbol DFS y sus tiempos de entrada y salida. */
    public static class Visit {
        private final String vertexId;
        private final String parentId;  // null en la raíz
        private final int discovery;
        private int finish;

        public Visit(String vertexId, String parentId, int discovery) {
            this.vertexId = vertexId;
            this.parentId = parentId;
            this.discovery = discovery;
        }

        public String getVertexId() { return vertexId; }
        public String getParentId() { return parentId; }
        public int getDiscovery() { return discovery; }
        public int getFinish() { return finish; }
    }

    private final MixedGraph graph;

    // Estado de la última ejecución
    private LinkedList<Visit> order;
    private LinkedList<String> inProgress;   // en la pila de recursión
    private LinkedList<String> finished;     // ya explorado por completo
    private LinkedList<LinkedList<String>> cycles;
    private int clock;

    /**
     * @param graph grafo a recorrer (no puede ser nulo)
     * @throws IllegalArgumentException si el grafo es nulo
     */
    public DepthFirstSearch(MixedGraph graph) {
        if (graph == null) {
            throw new IllegalArgumentException("El grafo no puede ser nulo");
        }
        this.graph = graph;
    }

    /**
     * DFS desde un vértice: solo visita lo alcanzable respetando la orientación.
     * @throws VertexNotFoundException si el vértice de origen no existe
     */
    public LinkedList<Visit> traverse(String startId) {
        if (!graph.hasvertex(startId)) {
            throw new VertexNotFoundException(startId);
        }
        startRun();
        visit(startId, null, null);
        return order;
    }

    /** Bosque DFS: arranca desde cada vértice sin visitar, en orden de inserción. */
    public LinkedList<Visit> traverseAll() {
        startRun();
        for (vertex v : graph.getvertices()) {
            if (!inProgress.contains(v.getId()) && !finished.contains(v.getId())) {
                visit(v.getId(), null, null);
            }
        }
        return order;
    }

    /** @return solo los IDs, en orden de visita */
    public LinkedList<String> order(String startId) {
        LinkedList<String> ids = new LinkedList<>();
        for (Visit v : traverse(startId)) {
            ids.add(v.getVertexId());
        }
        return ids;
    }

    /** @return ciclos encontrados en la última ejecución (cada uno como v, ..., u, v) */
    public LinkedList<LinkedList<String>> getCycles() {
        return cycles;
    }

    /** @return true si el grafo completo contiene al menos un ciclo */
    public boolean hasCycle() {
        traverseAll();
        return !cycles.isEmpty();
    }

    /**
     * Componentes débilmente conexos: se ignora la orientación, así que
     * un arco A→B une a A y B igual que una conexión no dirigida.
     * @return una lista por componente, con los IDs de sus vértices
     */
    public LinkedList<LinkedList<String>> weakComponents() {
        LinkedList<LinkedList<String>> components = new LinkedList<>();
        LinkedList<String> seen = new LinkedList<>();

        for (vertex root : graph.getvertices()) {
            if (seen.contains(root.getId())) continue;

            LinkedList<String> component = new LinkedList<>();
            LinkedList<String> stack = new LinkedList<>();
            seen.add(root.getId());
            stack.push(root.getId());

            while (!stack.isEmpty()) {
                String u = stack.pop();
                component.add(u);
                for (String w : weakNeighbors(u)) {
                    if (!seen.contains(w)) {
                        seen.add(w);
                        stack.push(w);
                    }
                }
            }
            components.add(component);
        }
        return components;
    }

    public int countWeakComponents() {
        return weakComponents().size();
    }

    // ---------------------------------------------------------------- internos

    /** @param viaEdgeId conexión por la que se llegó a u (null en la raíz) */
    private void visit(String u, String parentId, String viaEdgeId) {
        inProgress.add(u);
        Visit current = new Visit(u, parentId, ++clock);
        order.add(current);

        for (Neighbor n : graph.neighbors(u)) {
            if (n.getEdgeId().equals(viaEdgeId)) {
                continue; // no volver por la misma conexión no dirigida
            }
            String v = n.getVertexId();
            if (inProgress.contains(v)) {
                cycles.add(buildCycle(u, v));       // v es ancestro activo: ciclo
            } else if (!finished.contains(v)) {
                visit(v, u, n.getEdgeId());          // vértice nuevo
            }
            // si ya terminó: arista de avance o cruce, no es ciclo
        }

        inProgress.remove(u);
        finished.add(u);
        current.finish = ++clock;
    }

    /** Arma el ciclo v, ..., u, v siguiendo los padres desde u hasta v. */
    private LinkedList<String> buildCycle(String u, String v) {
        LinkedList<String> path = new LinkedList<>();
        String cur = u;
        path.addFirst(cur);
        while (!cur.equals(v)) {
            cur = parentOf(cur);
            path.addFirst(cur);
        }
        path.addLast(v);
        return path;
    }

    private String parentOf(String id) {
        for (Visit v : order) {
            if (v.getVertexId().equals(id)) return v.getParentId();
        }
        return null;
    }

    /** Vecinos ignorando orientación: salientes + vértices que apuntan a u. */
    private LinkedList<String> weakNeighbors(String u) {
        LinkedList<String> result = new LinkedList<>();
        for (Neighbor n : graph.neighbors(u)) {
            result.add(n.getVertexId());
        }
        for (vertex other : graph.getvertices()) {
            for (Neighbor n : graph.neighbors(other.getId())) {
                if (n.getVertexId().equals(u)) {
                    result.add(other.getId());
                }
            }
        }
        return result;
    }

    private void startRun() {
        order = new LinkedList<>();
        inProgress = new LinkedList<>();
        finished = new LinkedList<>();
        cycles = new LinkedList<>();
        clock = 0;
    }
}