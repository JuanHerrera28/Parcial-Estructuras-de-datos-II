/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.redsegura.nucleo;



import co.edu.redsegura.arboles.AvlTree;
import co.edu.redsegura.arboles.BinarySearchTree;
import co.edu.redsegura.excepciones.VertexNotFoundException;
import co.edu.redsegura.grafos.BreadthFirstSearch;
import co.edu.redsegura.grafos.DepthFirstSearch;
import co.edu.redsegura.grafos.DijkstraSolver;
import co.edu.redsegura.grafos.GraphRepresentations;
import co.edu.redsegura.grafos.MixedGraph;
import co.edu.redsegura.grafos.Neighbor;
import co.edu.redsegura.modelo.Edge;
import co.edu.redsegura.modelo.vertex;
import java.util.LinkedList;


/**
 * Núcleo principal que integra las estructuras de datos y algoritmos.
 * Solo expone métodos públicos y no permite acceso a campos privados.
 */
public class RedSeguraCore {

    private final MixedGraph graph;
    private final BinarySearchTree<String, String> abb;
    private final AvlTree<String, String> avl;

    public RedSeguraCore() {
        this.graph = SincelejoDataFactory.createGraph();
        this.abb = new BinarySearchTree<>();
        this.avl = new AvlTree<>();
        poblarArboles();
    }

    private void poblarArboles() {
        for (vertex v : graph.getvertices()) {
            abb.insert(v.getId(), v.getLabel());
            avl.insert(v.getId(), v.getLabel());
        }
    }

    // --- Q1: Búsqueda y comparación ABB vs AVL ---
    public String buscarYComparar(String id) {
        if (!graph.hasvertex(id)) {
            throw new VertexNotFoundException(id);
        }
        BinarySearchTree.SearchResult<String> rAbb = abb.search(id);
        AvlTree.SearchResult<String> rAvl = avl.search(id);

        return String.format(
            "Resultado para '%s' (%s):\n" +
            " - ABB: %d comparaciones/pasos (Altura arbol: %d)\n" +
            " - AVL: %d comparaciones/pasos (Altura arbol: %d)",
            id, rAvl.getValue(),
            rAbb.getSteps(), abb.height(),
            rAvl.getSteps(), avl.height()
        );
    }

    // --- Q3: Representaciones del Grafo ---
    public GraphRepresentations getRepresentations() {
        return new GraphRepresentations(graph);
    }

    /** Lista de adyacencia, matriz de adyacencia e incidencia en un solo texto. */
    public String describirRepresentaciones() {
        GraphRepresentations r = new GraphRepresentations(graph);
        return "=== LISTA DE ADYACENCIA ===\n" + r.adjacencyListToString()
             + "\n\n=== MATRIZ DE ADYACENCIA PONDERADA ===\n" + r.adjacencyMatrixToString()
             + "\n\n=== MATRIZ DE INCIDENCIA ===\n" + r.incidenceMatrixToString();
    }

    // --- Q4: Recorridos BFS y DFS ---
    public LinkedList<String> ejecutarBFS(String startId) {
        BreadthFirstSearch bfs = new BreadthFirstSearch(graph);
        return bfs.order(startId);
    }

    public LinkedList<String> ejecutarDFS(String startId) {
        DepthFirstSearch dfs = new DepthFirstSearch(graph);
        return dfs.order(startId);
    }

    public boolean hayCiclos() {
        return new DepthFirstSearch(graph).hasCycle();
    }

    public LinkedList<LinkedList<String>> componentesDebiles() {
        return new DepthFirstSearch(graph).weakComponents();
    }

    // --- Q5 y Q6: Dijkstra y Bloqueo de Rutas ---
    public LinkedList<String> calcularRutaCorta(String startId, String endId) {
        DijkstraSolver dijkstra = new DijkstraSolver(graph);
        return dijkstra.getShortestPath(startId, endId);
    }

    public double calcularDistanciaCorta(String startId, String endId) {
        DijkstraSolver dijkstra = new DijkstraSolver(graph);
        return dijkstra.getShortestDistance(startId, endId);
    }

    public void bloquearConexion(String edgeId) {
        graph.setEdgeBlocked(edgeId, true);
    }

    public void desbloquearConexion(String edgeId) {
        graph.setEdgeBlocked(edgeId, false);
    }

    // --- Gestión dinámica del grafo (sincronizada con los árboles) ---

    /**
     * Inserta un vértice en el grafo, el ABB y el AVL.
     * @throws co.edu.redsegura.excepciones.DuplicateIdException si el ID ya existe
     */
    public void insertarVertice(String id, String label, String type) {
        graph.addVertex(new vertex(id, label, type)); // valida antes de tocar los árboles
        abb.insert(id, label);
        avl.insert(id, label);
    }

    /**
     * Elimina un vértice y sus conexiones del grafo, y su clave de los dos árboles.
     * @return cuántas conexiones se eliminaron
     * @throws VertexNotFoundException si el vértice no existe
     */
    public int eliminarVertice(String id) {
        int removed = graph.removeVertex(id);  // lanza la excepción antes de tocar los árboles
        abb.delete(id);
        avl.delete(id);
        return removed;
    }

    public void insertarConexion(String id, String label, String origin,
                                 String destination, double weight, boolean directed) {
        graph.addEdge(new Edge(id, origin, destination, directed, weight, label));
    }

    public void eliminarConexion(String id) {
        graph.removeEdge(id);
    }

    public LinkedList<String> listarVertices() {
        LinkedList<String> out = new LinkedList<>();
        for (vertex v : graph.getvertices()) {
            out.add(v.toString());
        }
        return out;
    }

    public LinkedList<String> listarConexiones() {
        LinkedList<String> out = new LinkedList<>();
        for (Edge e : graph.getEdges()) {
            out.add(e.toString());
        }
        return out;
    }

    public LinkedList<String> describirVecinos(String id) {
        LinkedList<String> out = new LinkedList<>();
        for (Neighbor n : graph.neighbors(id)) {
            out.add(n.getVertexId() + " (" + n.getWeight() + " min) por " + n.getEdgeId());
        }
        return out;
    }

    // --- Gestión de árboles (cada árbol por separado) ---

    public String verABB() {
        return "ABB\n  inOrder:   " + abb.inOrder()
             + "\n  preOrder:  " + abb.preOrder()
             + "\n  postOrder: " + abb.postOrder()
             + "\n  altura:    " + abb.height();
    }

    public String verAVL() {
        return "AVL\n  inOrder:   " + avl.inOrder()
             + "\n  preOrder:  " + avl.preOrder()
             + "\n  postOrder: " + avl.postOrder()
             + "\n  altura:    " + avl.height();
    }

    public void insertarEnABB(String key, String label) {
        abb.insert(key, label);
    }

    public void insertarEnAVL(String key, String label) {
        avl.insert(key, label);
    }

    public void eliminarDeABB(String key) {
        abb.delete(key);
    }

    public void eliminarDeAVL(String key) {
        avl.delete(key);
    }

    public String buscarEnABB(String key) {
        BinarySearchTree.SearchResult<String> r = abb.search(key);
        return "ABB: " + (r.getValue() == null ? "no encontrado" : r.getValue())
             + " (" + r.getSteps() + " pasos)";
    }

    public String buscarEnAVL(String key) {
        AvlTree.SearchResult<String> r = avl.search(key);
        return "AVL: " + (r.getValue() == null ? "no encontrado" : r.getValue())
             + " (" + r.getSteps() + " pasos)";
    }

    public String compararAlturas() {
        return "Altura ABB: " + abb.height() + "\nAltura AVL: " + avl.height();
    }

    /** Q2: las cuatro rotaciones con secuencias de tres claves (árboles aparte). */
    public String demostrarRotaciones() {
        return rotacion("LL", "30", "20", "10")
             + rotacion("RR", "10", "20", "30")
             + rotacion("LR", "30", "10", "20")
             + rotacion("RL", "10", "30", "20");
    }

    private String rotacion(String nombre, String a, String b, String c) {
        AvlTree<String, String> t = new AvlTree<>();
        t.insert(a, "x");
        t.insert(b, "x");
        t.insert(c, "x");
        return "Rotacion " + nombre + ": insertar " + a + ", " + b + ", " + c
             + "  ->  preOrder " + t.preOrder() + "  (raiz " + b + ", altura " + t.height() + ")\n";
    }
}