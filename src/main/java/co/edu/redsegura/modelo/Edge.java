/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.redsegura.modelo;
import co.edu.redsegura.excepciones.InvalidWeightException;
/**
 *
 * @author juand
 */
/**
 * Una conexión entre dos lugares de la red.
 * Si es dirigida se recorre solo de origen a destino;
 * si no es dirigida se recorre en ambos sentidos.
 */
public class Edge {

    private final String id;           // ejemplo: "R16"
    private final String origin;       // ID del vértice de origen
    private final String destination;  // ID del vértice de destino
    private final boolean directed;    // true = dirigida, false = no dirigida
    private final double weight;       // minutos estimados de desplazamiento
    private final String label;        // ejemplo: "Ruta SETP al terminal"
    private boolean blocked;           // true = la conexión no se puede usar

    /**
     * @param id          identificador único (no nulo ni vacío)
     * @param origin      ID del vértice de origen (no nulo ni vacío)
     * @param destination ID del vértice de destino (no nulo ni vacío)
     * @param directed    true si es dirigida, false si no lo es
     * @param weight      minutos; debe ser mayor que 0 y finito
     * @param label       descripción de la conexión (puede ser nula)
     * @throws IllegalArgumentException si falta el ID, el origen o el destino
     * @throws InvalidWeightException   si el peso no es válido
     */
    public Edge(String id, String origin, String destination,
                boolean directed, double weight, String label) {
        if (esVacio(id) || esVacio(origin) || esVacio(destination)) {
            throw new IllegalArgumentException(
                "El ID, el origen y el destino de la conexión no pueden ser nulos ni vacíos");
        }
        if (Double.isNaN(weight) || Double.isInfinite(weight) || weight <= 0) {
            throw new InvalidWeightException(weight);
        }
        this.id = id.trim();
        this.origin = origin.trim();
        this.destination = destination.trim();
        this.directed = directed;
        this.weight = weight;
        this.label = (label == null) ? "" : label.trim();
        this.blocked = false;
    }

    private static boolean esVacio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }

    public String getId() {
        return id;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public boolean isDirected() {
        return directed;
    }

    public double getWeight() {
        return weight;
    }

    public String getLabel() {
        return label;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    @Override
    public String toString() {
        return id + ": " + origin + (directed ? " -> " : " -- ") + destination
                + " (" + weight + " min)" + (blocked ? " [BLOQUEADA]" : "");
    }
}