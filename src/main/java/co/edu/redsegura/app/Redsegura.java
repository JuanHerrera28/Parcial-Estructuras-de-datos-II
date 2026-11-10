package co.edu.redsegura.app;


import co.edu.redsegura.grafos.BreadthFirstSearch;
import co.edu.redsegura.grafos.DepthFirstSearch;
import co.edu.redsegura.grafos.MixedGraph;
import co.edu.redsegura.grafos.Neighbor;
import co.edu.redsegura.modelo.Edge;
import co.edu.redsegura.modelo.vertex;
import co.edu.redsegura.nucleo.RedSeguraCore;
import co.edu.redsegura.nucleo.SincelejoDataFactory;
import java.util.LinkedList;
import java.util.Scanner;


/**
 * Aplicación de consola de RedSegura. Cualquier error se muestra
 * y el programa continúa (Q7).
 */
public class Redsegura {

    private final RedSeguraCore core = new RedSeguraCore();
    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        new Redsegura().run();
    }

    // ============================================================ MENU PRINCIPAL

    public void run() {
        boolean running = true;
        while (running) {
            System.out.println("\n==========================================");
            System.out.println("      REDSEGURA - MENU PRINCIPAL");
            System.out.println("==========================================");
            System.out.println("1. Gestion del Grafo");
            System.out.println("2. Gestion de Arboles (ABB y AVL)");
            System.out.println("3. Representaciones del Grafo");
            System.out.println("4. Recorridos (BFS y DFS)");
            System.out.println("5. Ruta Minima (Dijkstra)");
            System.out.println("6. Consultas Obligatorias (Q1-Q7)");
            System.out.println("7. Salir");
            System.out.println("==========================================");
            try {
                switch (ask("Opcion")) {
                    case "1" -> graphMenu();
                    case "2" -> treesMenu();
                    case "3" -> System.out.println(core.describirRepresentaciones());
                    case "4" -> traversalMenu();
                    case "5" -> routeMenu();
                    case "6" -> queriesMenu();
                    case "7" -> running = false;
                    default -> System.out.println("Opcion no valida");
                }
            } catch (RuntimeException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        scanner.close();
    }

    // ============================================================ GESTION DEL GRAFO

    private void graphMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- GESTION DEL GRAFO ---");
            System.out.println("1. Listar vertices");
            System.out.println("2. Listar conexiones");
            System.out.println("3. Insertar vertice");
            System.out.println("4. Eliminar vertice");
            System.out.println("5. Insertar conexion");
            System.out.println("6. Eliminar conexion");
            System.out.println("7. Consultar vecinos de un vertice");
            System.out.println("8. Volver");
            try {
                switch (ask("Opcion")) {
                    case "1" -> printAll(core.listarVertices());
                    case "2" -> printAll(core.listarConexiones());
                    case "3" -> {
                        String id = ask("ID (ej. S13)");
                        String label = ask("Etiqueta");
                        String type = ask("Tipo");
                        core.insertarVertice(id, label, type);
                        System.out.println("Vertice " + id + " insertado en grafo, ABB y AVL");
                    }
                    case "4" -> {
                        String id = ask("ID del vertice");
                        int n = core.eliminarVertice(id);
                        System.out.println("Vertice " + id + " eliminado (" + n
                                + " conexion(es)) del grafo, ABB y AVL");
                    }
                    case "5" -> insertEdge();
                    case "6" -> {
                        String id = ask("ID de la conexion");
                        core.eliminarConexion(id);
                        System.out.println("Conexion " + id + " eliminada");
                    }
                    case "7" -> printAll(core.describirVecinos(ask("ID del vertice")));
                    case "8" -> back = true;
                    default -> System.out.println("Opcion no valida");
                }
            } catch (RuntimeException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void insertEdge() {
        String id = ask("ID (ej. R19)");
        String origin = ask("Origen");
        String destination = ask("Destino");
        boolean directed = ask("Dirigida? (s/n)").equalsIgnoreCase("s");
        double weight;
        try {
            weight = Double.parseDouble(ask("Peso en minutos"));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El peso debe ser un numero");
        }
        String label = ask("Etiqueta");
        core.insertarConexion(id, label, origin, destination, weight, directed);
        System.out.println("Conexion " + id + " insertada");
    }

    // ============================================================ GESTION DE ARBOLES

    private void treesMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- GESTION DE ARBOLES ---");
            System.out.println("1. Ver ABB (recorridos)");
            System.out.println("2. Ver AVL (recorridos)");
            System.out.println("3. Insertar en ABB");
            System.out.println("4. Insertar en AVL");
            System.out.println("5. Eliminar del ABB");
            System.out.println("6. Eliminar del AVL");
            System.out.println("7. Buscar en ABB");
            System.out.println("8. Buscar en AVL");
            System.out.println("9. Comparar alturas ABB vs AVL");
            System.out.println("10. Demostrar rotaciones AVL");
            System.out.println("11. Volver");
            try {
                switch (ask("Opcion")) {
                    case "1" -> System.out.println(core.verABB());
                    case "2" -> System.out.println(core.verAVL());
                    case "3" -> {
                        String key = ask("ID a insertar en el ABB");
                        core.insertarEnABB(key, ask("Etiqueta"));
                        System.out.println("Insertado en el ABB: " + key);
                    }
                    case "4" -> {
                        String key = ask("ID a insertar en el AVL");
                        core.insertarEnAVL(key, ask("Etiqueta"));
                        System.out.println("Insertado en el AVL: " + key);
                    }
                    case "5" -> {
                        String key = ask("ID a eliminar del ABB");
                        core.eliminarDeABB(key);
                        System.out.println("Eliminado del ABB: " + key);
                    }
                    case "6" -> {
                        String key = ask("ID a eliminar del AVL");
                        core.eliminarDeAVL(key);
                        System.out.println("Eliminado del AVL: " + key);
                    }
                    case "7" -> System.out.println(core.buscarEnABB(ask("ID a buscar en el ABB")));
                    case "8" -> System.out.println(core.buscarEnAVL(ask("ID a buscar en el AVL")));
                    case "9" -> System.out.println(core.compararAlturas());
                    case "10" -> System.out.println(core.demostrarRotaciones());
                    case "11" -> back = true;
                    default -> System.out.println("Opcion no valida");
                }
            } catch (RuntimeException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    // ============================================================ RECORRIDOS

    private void traversalMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- RECORRIDOS (BFS y DFS) ---");
            System.out.println("1. BFS desde un vertice");
            System.out.println("2. DFS desde un vertice");
            System.out.println("3. Hay ciclos?");
            System.out.println("4. Componentes debiles");
            System.out.println("5. Volver");
            try {
                switch (ask("Opcion")) {
                    case "1" -> System.out.println("BFS: " + core.ejecutarBFS(ask("Origen")));
                    case "2" -> System.out.println("DFS: " + core.ejecutarDFS(ask("Origen")));
                    case "3" -> System.out.println(core.hayCiclos() ? "Si hay ciclos" : "No hay ciclos");
                    case "4" -> {
                        LinkedList<LinkedList<String>> c = core.componentesDebiles();
                        System.out.println(c.size() + " componente(s): " + c);
                    }
                    case "5" -> back = true;
                    default -> System.out.println("Opcion no valida");
                }
            } catch (RuntimeException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    // ============================================================ RUTA MINIMA

    private void routeMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- RUTA MINIMA (DIJKSTRA) ---");
            System.out.println("1. Calcular ruta entre dos vertices");
            System.out.println("2. Ruta de control S02 -> S08");
            System.out.println("3. Bloquear conexion");
            System.out.println("4. Desbloquear conexion");
            System.out.println("5. Volver");
            try {
                switch (ask("Opcion")) {
                    case "1" -> {
                        String from = ask("Origen");
                        printRoute(from, ask("Destino"));
                    }
                    case "2" -> printRoute("S02", "S08");
                    case "3" -> {
                        String id = ask("ID de la conexion a bloquear");
                        core.bloquearConexion(id);
                        System.out.println("Conexion " + id + " bloqueada");
                    }
                    case "4" -> {
                        String id = ask("ID de la conexion a desbloquear");
                        core.desbloquearConexion(id);
                        System.out.println("Conexion " + id + " desbloqueada");
                    }
                    case "5" -> back = true;
                    default -> System.out.println("Opcion no valida");
                }
            } catch (RuntimeException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void printRoute(String from, String to) {
        System.out.println("Ruta: " + core.calcularRutaCorta(from, to));
        System.out.println("Costo: " + core.calcularDistanciaCorta(from, to) + " min");
    }

    // ============================================================ CONSULTAS Q1-Q7

    private void queriesMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- CONSULTAS OBLIGATORIAS ---");
            System.out.println("Q1. Buscar Hospital Universitario en ABB y AVL");
            System.out.println("Q2. Mostrar las 4 rotaciones AVL");
            System.out.println("Q3. Imprimir lista, matriz adyacencia e incidencia");
            System.out.println("Q4. BFS y DFS desde S02");
            System.out.println("Q5. Dijkstra S02 -> S08 (debe ser 24 min)");
            System.out.println("Q6. Bloquear R16 y recalcular");
            System.out.println("Q7. Consultar vertice inexistente");
            System.out.println("8. Volver");
            try {
                switch (ask("Opcion").toUpperCase()) {
                    case "Q1", "1" -> System.out.println(core.buscarYComparar("S05"));
                    case "Q2", "2" -> System.out.println(core.demostrarRotaciones());
                    case "Q3", "3" -> System.out.println(core.describirRepresentaciones());
                    case "Q4", "4" -> {
                        System.out.println("BFS: " + core.ejecutarBFS("S02"));
                        System.out.println("DFS: " + core.ejecutarDFS("S02"));
                        System.out.println("El orden difiere porque BFS usa una cola y visita por niveles,");
                        System.out.println("mientras DFS se hunde por una rama antes de volver.");
                    }
                    case "Q5", "5" -> printRoute("S02", "S08");
                    case "Q6", "6" -> {
                        System.out.println("Antes de bloquear:");
                        printRoute("S02", "S08");
                        core.bloquearConexion("R16");
                        System.out.println("\nCon R16 bloqueada:");
                        try {
                            printRoute("S02", "S08");
                        } finally {
                            core.desbloquearConexion("R16");
                            System.out.println("(R16 desbloqueada de nuevo)");
                        }
                    }
                    case "Q7", "7" -> {
                        try {
                            System.out.println(core.buscarYComparar("S99"));
                        } catch (RuntimeException e) {
                            System.out.println("Error controlado: " + e.getMessage()
                                    + " (el programa continua)");
                        }
                    }
                    case "8" -> back = true;
                    default -> System.out.println("Opcion no valida");
                }
            } catch (RuntimeException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    // ============================================================ UTILIDADES

    private void printAll(LinkedList<String> lines) {
        for (String s : lines) {
            System.out.println("  " + s);
        }
        System.out.println("Total: " + lines.size());
    }

    private String ask(String prompt) {
        System.out.print(prompt + ": ");
        return scanner.nextLine().trim();
    }
}