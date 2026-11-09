/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package co.edu.redsegura.app;

import co.edu.redsegura.arboles.AvlTree;
import co.edu.redsegura.arboles.BinarySearchTree;
import co.edu.redsegura.excepciones.VertexNotFoundException;
import co.edu.redsegura.grafos.BreadthFirstSearch;
import co.edu.redsegura.grafos.DepthFirstSearch;
import co.edu.redsegura.grafos.DijkstraSolver;
import co.edu.redsegura.grafos.GraphRepresentations;
import co.edu.redsegura.grafos.MixedGraph;
import co.edu.redsegura.modelo.Edge;
import co.edu.redsegura.modelo.vertex;
import co.edu.redsegura.nucleo.SincelejoDataFactory;
import java.util.LinkedList;

/**
 *
 * @author juand
 */
public class Redsegura {

    public static void main(String[] args) {
       System.out.println("=== Q1: BUSQUEDA 'Hospital Universitario' (ABB vs AVL) ===");
        
        BinarySearchTree<String, String> abb = new BinarySearchTree<>();
        AvlTree<String, String> avl = new AvlTree<>();

        // Insertar secuencialmente (simula degeneración en lista para ABB)
        String[] ids = {"S01", "S02", "S03", "S04", "S05", "S06", "S07", "S08", "S09", "S10", "S11", "S12"};
        String[] nombres = {
            "UAJS Sede C", "UAJS Sede E", "Unisucre", "CECAR", 
            "Hospital Universitario", "Clinica Americas", "Estación SETP", 
            "Terminal", "Parque Santander", "Mercado", "PEP Argelia", "Glorieta"
        };

        for (int i = 0; i < ids.length; i++) {
            abb.insert(ids[i], nombres[i]);
            avl.insert(ids[i], nombres[i]);
        }

        BinarySearchTree.SearchResult<String> rAbb = abb.search("S05");
        AvlTree.SearchResult<String> rAvl = avl.search("S05");

        System.out.println("ABB -> Encontrado: " + rAbb.getValue() + " | Pasos: " + rAbb.getSteps() + " | Altura total: " + abb.height());
        System.out.println("AVL -> Encontrado: " + rAvl.getValue() + " | Pasos: " + rAvl.getSteps() + " | Altura total: " + avl.height());

        System.out.println("\n=== Q2: DEMOSTRACION DE LAS 4 ROTACIONES AVL ===");
        
        // Rotación LL (30, 20, 10) -> Raíz debe quedar en 20
        AvlTree<Integer, String> avlLL = new AvlTree<>();
        avlLL.insert(30, "A"); avlLL.insert(20, "B"); avlLL.insert(10, "C");
        System.out.println("Rotacion LL (30,20,10) -> Raiz: " + avlLL.getRoot().getKey() + " (Esperado: 20)");

        // Rotación RR (10, 20, 30) -> Raíz debe quedar en 20
        AvlTree<Integer, String> avlRR = new AvlTree<>();
        avlRR.insert(10, "A"); avlRR.insert(20, "B"); avlRR.insert(30, "C");
        System.out.println("Rotacion RR (10,20,30) -> Raiz: " + avlRR.getRoot().getKey() + " (Esperado: 20)");

        // Rotación LR (30, 10, 20) -> Raíz debe quedar en 20
        AvlTree<Integer, String> avlLR = new AvlTree<>();
        avlLR.insert(30, "A"); avlLR.insert(10, "B"); avlLR.insert(20, "C");
        System.out.println("Rotacion LR (30,10,20) -> Raiz: " + avlLR.getRoot().getKey() + " (Esperado: 20)");

        // Rotación RL (10, 30, 20) -> Raíz debe quedar en 20
        AvlTree<Integer, String> avlRL = new AvlTree<>();
        avlRL.insert(10, "A"); avlRL.insert(30, "B"); avlRL.insert(20, "C");
        System.out.println("Rotacion RL (10,30,20) -> Raiz: " + avlRL.getRoot().getKey() + " (Esperado: 20)");
    }
}