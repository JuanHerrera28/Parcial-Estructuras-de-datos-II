/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.redsegura.excepciones;

/**
 *
 * @author juand
 */
public class DuplicateIdException extends RuntimeException {
    
    public DuplicateIdException(String id){
        super("El ID ya existe: " + id);
    }
    
}   
