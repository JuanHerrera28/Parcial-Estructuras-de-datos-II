/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package co.edu.redsegura.app;

import co.edu.redsegura.modelo.Edge;
import co.edu.redsegura.modelo.vertex;

/**
 *
 * @author juand
 */
public class Redsegura {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        
        
        vertex v = new co.edu.redsegura.modelo.vertex("S05", "Hospital Universitario", "Salud");
            System.out.println(v);
            Edge e = new Edge("R16", "S07", "S08", true, 12, "Ruta SETP al terminal");
System.out.println(e);
    }
    
   
}
