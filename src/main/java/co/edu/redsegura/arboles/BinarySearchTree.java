/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.redsegura.arboles;

import java.util.LinkedList;

public class BinarySearchTree<K extends Comparable<K>, V> {

    public static class Node<K, V> {
        private K key;
        private V value;
        private Node<K, V> left;
        private Node<K, V> right;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() { return key; }
        public V getValue() { return value; }
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

    public void insert(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("La clave no puede ser nula");
        }
        root = insertRecursive(root, key, value);
    }

    private Node<K, V> insertRecursive(Node<K, V> current, K key, V value) {
        if (current == null) {
            return new Node<>(key, value);
        }
        int cmp = key.compareTo(current.key);
        if (cmp < 0) {
            current.left = insertRecursive(current.left, key, value);
        } else if (cmp > 0) {
            current.right = insertRecursive(current.right, key, value);
        } else {
            current.value = value;
        }
        return current;
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

    private Node<K, V> deleteRecursive(Node<K, V> current, K key) {
        if (current == null) return null;

        int cmp = key.compareTo(current.key);
        if (cmp < 0) {
            current.left = deleteRecursive(current.left, key);
        } else if (cmp > 0) {
            current.right = deleteRecursive(current.right, key);
        } else {
            if (current.left == null) return current.right;
            if (current.right == null) return current.left;

            Node<K, V> successor = getMin(current.right);
            current.key = successor.key;
            current.value = successor.value;
            current.right = deleteRecursive(current.right, successor.key);
        }
        return current;
    }

    private Node<K, V> getMin(Node<K, V> node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public int height() {
        return heightRecursive(root);
    }

    private int heightRecursive(Node<K, V> node) {
        if (node == null) return 0;
        return 1 + Math.max(heightRecursive(node.left), heightRecursive(node.right));
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

    public LinkedList<K> preOrder() {
        LinkedList<K> list = new LinkedList<>();
        preOrderRecursive(root, list);
        return list;
    }

    private void preOrderRecursive(Node<K, V> node, LinkedList<K> list) {
        if (node != null) {
            list.add(node.key);
            preOrderRecursive(node.left, list);
            preOrderRecursive(node.right, list);
        }
    }

    public LinkedList<K> postOrder() {
        LinkedList<K> list = new LinkedList<>();
        postOrderRecursive(root, list);
        return list;
    }

    private void postOrderRecursive(Node<K, V> node, LinkedList<K> list) {
        if (node != null) {
            postOrderRecursive(node.left, list);
            postOrderRecursive(node.right, list);
            list.add(node.key);
        }
    }
}