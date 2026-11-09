/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.redsegura.arboles;

import java.util.LinkedList;

/**
 * Árbol AVL con balanceo automático (rotaciones LL, RR, LR, RL).
 * Mantiene la altura logarítmica y registra los pasos de búsqueda para Q1 y Q2.
 */
public class AvlTree<K extends Comparable<K>, V> {

    public static class Node<K, V> {
        private K key;
        private V value;
        private int height;
        private Node<K, V> left;
        private Node<K, V> right;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.height = 1;
        }

        public K getKey() { return key; }
        public V getValue() { return value; }
        public int getHeight() { return height; }
        public Node<K, V> getLeft() { return left; }
        public Node<K, V> getRight() { return right; }
    }

    public static class SearchResult<V> {
        private final V value;
        private final int steps;

        public SearchResult(V value, int steps) {
            this.value = value;
            this.steps = steps;
        }

        public V getValue() { return value; }
        public int getSteps() { return steps; }
        public boolean isFound() { return value != null; }
    }

    private Node<K, V> root;

    public Node<K, V> getRoot() {
        return root;
    }

    public void insert(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("La clave no puede ser nula");
        }
        root = insertRecursive(root, key, value);
    }

    private Node<K, V> insertRecursive(Node<K, V> node, K key, V value) {
        if (node == null) {
            return new Node<>(key, value);
        }

        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = insertRecursive(node.left, key, value);
        } else if (cmp > 0) {
            node.right = insertRecursive(node.right, key, value);
        } else {
            node.value = value;
            return node;
        }

        updateHeight(node);
        return balance(node);
    }

    public SearchResult<V> search(K key) {
        if (key == null) return new SearchResult<>(null, 0);

        Node<K, V> current = root;
        int steps = 0;

        while (current != null) {
            steps++;
            int cmp = key.compareTo(current.key);
            if (cmp == 0) {
                return new SearchResult<>(current.value, steps);
            } else if (cmp < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return new SearchResult<>(null, steps);
    }

    public void delete(K key) {
        if (key != null) {
            root = deleteRecursive(root, key);
        }
    }

    private Node<K, V> deleteRecursive(Node<K, V> node, K key) {
        if (node == null) return null;

        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = deleteRecursive(node.left, key);
        } else if (cmp > 0) {
            node.right = deleteRecursive(node.right, key);
        } else {
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;

            Node<K, V> successor = getMin(node.right);
            node.key = successor.key;
            node.value = successor.value;
            node.right = deleteRecursive(node.right, successor.key);
        }

        updateHeight(node);
        return balance(node);
    }

    private Node<K, V> balance(Node<K, V> node) {
        int bf = balanceFactor(node);

        // Caso LL: Desbalance a la izquierda (hijo izq con balance >= 0) -> Rotación Derecha
        if (bf > 1 && balanceFactor(node.left) >= 0) {
            return rotateRight(node);
        }
        // Caso LR: Desbalance a la izquierda (hijo izq con balance < 0) -> Izquierda en hijo, Derecha en raíz
        if (bf > 1 && balanceFactor(node.left) < 0) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }
        // Caso RR: Desbalance a la derecha (hijo der con balance <= 0) -> Rotación Izquierda
        if (bf < -1 && balanceFactor(node.right) <= 0) {
            return rotateLeft(node);
        }
        // Caso RL: Desbalance a la derecha (hijo der con balance > 0) -> Derecha en hijo, Izquierda en raíz
        if (bf < -1 && balanceFactor(node.right) > 0) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    private Node<K, V> rotateLeft(Node<K, V> node) {
        Node<K, V> y = node.right;
        Node<K, V> T2 = y.left;

        y.left = node;
        node.right = T2;

        updateHeight(node);
        updateHeight(y);

        return y;
    }

    private Node<K, V> rotateRight(Node<K, V> node) {
        Node<K, V> x = node.left;
        Node<K, V> T2 = x.right;

        x.right = node;
        node.left = T2;

        updateHeight(node);
        updateHeight(x);

        return x;
    }

    private void updateHeight(Node<K, V> node) {
        node.height = 1 + Math.max(height(node.left), height(node.right));
    }

    private int height(Node<K, V> node) {
        return (node == null) ? 0 : node.height;
    }

    public int balanceFactor(Node<K, V> node) {
        if (node == null) return 0;
        return height(node.left) - height(node.right);
    }

    public int height() {
        return height(root);
    }

    private Node<K, V> getMin(Node<K, V> node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public LinkedList<K> inOrder() {
        LinkedList<K> list = new LinkedList<>();
        inOrderRecursive(root, list);
        return list;
    }

    private void inOrderRecursive(Node<K, V> node, LinkedList<K> list) {
        if (node != null) {
            inOrderRecursive(node.left, list);
            list.add(node.key);
            inOrderRecursive(node.right, list);
        }
    }
}