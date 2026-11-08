/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.redsegura.excepciones;

/**
 *
 * @author juand
 */
public class InvalidWeightException extends RuntimeException {

    public InvalidWeightException(double peso){
        super("Peso inválido (debe ser mayor que 0 y finito): " + peso);
    }
}
