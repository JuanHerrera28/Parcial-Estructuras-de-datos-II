/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.redsegura.modelo;

/**
 *
 * @author juand
 */
public class vertex {
    
    private final String id;     // ejemplo: "S05"
    private final String label;  // ejemplo: "Hospital Universitario"
    private final String type;
    
    /**
     * @param id    identificador único (no puede ser nulo ni vacío)
     * @param label nombre del lugar (no puede ser nulo ni vacío)
     * @param type  categoría del lugar (no puede ser nula ni vacía)
     * @throws IllegalArgumentException si algún dato es nulo o vacío
     */
    public vertex(String id, String label, String type){
        if(esVacio(id) || esVacio(label) || esVacio(type)){
             throw new IllegalArgumentException(
                "El ID, la etiqueta y el tipo del vértice no pueden ser nulos ni vacíos");
        }
        this.id = id.trim();
        this.label = label.trim();
        this.type =  type.trim();
               
        }
    
    private static boolean esVacio(String texto){
        return texto == null || texto.trim().isEmpty();
    }

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public String getType() {
        return type;
    }
    
    @Override
    public String toString(){
        return id + " (" + label + ")";
    }
    
    }

