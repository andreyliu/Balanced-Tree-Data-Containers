package BalancedTrees;

import java.util.ArrayDeque;
import java.util.Objects;
import java.util.Queue;

public class AVLTree<K extends Comparable<? super K>, V> extends BST<K, V> {

    private Node<K, V> root;

    private static class Node<K extends Comparable<? super K>, V> extends BST.INode<K, V> {
        int height = 0;
        Node<K, V> left;
        Node<K, V> right;
        Node(K key, V val) {
            super(key, val);
        }
        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return val;
        }

        @Override
        INode<K, V> getLeft() {
            return left;
        }

        @Override
        INode<K, V> getRight() {
            return right;
        }
    }
    @Override
    public int size() {
        return size(root);
    }

    private int size(Node<K, V> n) {
        return n == null ? 0 : n.size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public V get(K k) {
        return get(root, Objects.requireNonNull(k));
    }

    @Override
    public boolean contains(K k) {
        return get(k) != null;
    }

    @Override
    public void put(K k, V val) {
        root = put(root, Objects.requireNonNull(k), val);
    }

    private Node<K, V> put(Node<K, V> h, K k, V v) {
        if (h == null) return new Node<>(k, v);
        int comp = k.compareTo(h.key);
        if (comp < 0) {
            h.left = put(h.left, k, v);
        } else if (comp > 0) {
            h.right = put(h.right, k, v);
        } else {
            h.val = v;
            return h;
        }
        return balance(h);
    }

    private Node<K, V> balance(Node<K, V> h) {
        h.height = 1 + Math.max(height(h.left), height(h.right));
        h.size = 1 + size(h.left) + size(h.right);
        int b = balanceFactor(h);
        if (b < -1) {
            // right tree too tall
            if (balanceFactor(h.right) > 0) {
                // right left needs to be moved out of the way
                h.right = rotateRight(h.right);
            }
            h = rotateLeft(h);
        } else if (b > 1) {
            // left tree too tall
            if (balanceFactor(h.left) < 0) {
                // left right needs to be moved out of the way
                h.left = rotateLeft(h.left);
            }
            h = rotateRight(h);
        }
        return h;
    }

    private int balanceFactor(Node<K, V> h) {
        return height(h.left) - height(h.right);
    }

    private int height(Node<K, V> h) {
        return h == null ? -1 : h.height;
    }


    private Node<K, V> rotateLeft(Node<K, V> h) {
        Node<K, V> x = h.right;
        h.right = x.left;
        x.left = h;

        h.height = 1 + Math.max(height(h.left), height(h.right));
        x.height = 1 + Math.max(height(x.left), height(x.right));

        x.size = h.size;
        h.size = 1 + size(h.left) + size(h.right);
        return x;
    }

    private Node<K, V> rotateRight(Node<K, V> h) {
        Node<K, V> x = h.left;
        h.left = x.right;
        x.right = h;

        h.height = 1 + Math.max(height(h.left), height(h.right));
        x.height = 1 + Math.max(height(x.left), height(x.right));

        x.size = h.size;
        h.size = 1 + size(h.left) + size(h.right);
        return x;
    }

    @Override
    public void delete(K k) {
        if (!contains(Objects.requireNonNull(k))) {
            return;
        }
        root = delete(root, k);
    }

    private Node<K, V> delete(Node<K, V> h, K k) {
        int comp = k.compareTo(h.key);
        if (comp < 0) {
            h.left = delete(h.left, k);
        } else if (comp > 0) {
            h.right = delete(h.right, k);
        } else {
            INode<K, V> succ = min(h.right);
            h.key = succ.key;
            h.val = succ.val;
            h.right = deleteMin(h.right);
        }
        return balance(h);
    }

    @Override
    public void deleteMin() {
        if (isEmpty()) return;
        root = deleteMin(root);
    }

    private Node<K, V> deleteMin(Node<K, V> h) {
        if (h.left == null) {
            return null;
        }
        h.left = deleteMin(h.left);
        return balance(h);
    }

    @Override
    public void deleteMax() {
        if (isEmpty()) return;
        root = deleteMax(root);
    }

    private Node<K, V> deleteMax(Node<K, V> h) {
        if (h.right == null) {
            return null;
        }
        h.right = deleteMax(h.right);
        return balance(h);
    }

    @Override
    public int height() {
        return height(root);
    }

    @Override
    public K min() {
        if (isEmpty()) return null;
        return min(root).key;
    }

    @Override
    public K max() {
        if (isEmpty()) return null;
        return max(root).key;
    }

    @Override
    public K floor(K k) {
        Objects.requireNonNull(k);
        INode<K, V>  f = floor(root, k);
        return f == null ? null : f.key;
    }

    @Override
    public K ceiling(K k) {
        Objects.requireNonNull(k);
        INode<K, V> c = ceiling(root, k);
        return c == null ? null : c.key;
    }

    @Override
    public K select(int k) {
        if (k < 0 || k >= size()) {
            return null;
        }
        return select(root, k).key;
    }

    @Override
    public int rank(K k) {
        Objects.requireNonNull(k);
        return rank(root, k);
    }

    @Override
    public Iterable<K> keys() {
        Queue<K> q = new ArrayDeque<>();
        keys(root, min(), max(), q);
        return q;
    }

    @Override
    public Iterable<K> keys(K lo, K hi) {
        Queue<K> q = new ArrayDeque<>();
        keys(root, lo, hi, q);
        return q;
    }

    @Override
    String check() {
        StringBuilder sb = new StringBuilder();
        if (!isBST(root)) {
            sb.append("not BST\n");
        }
        if (!isBalanced()) {
            sb.append("not balanced\n");
        }
        if (!isSizeConsistent(root)) {
            sb.append("size not consistent\n");
        }
        if (!isRankConsistent()) {
            sb.append("rank is not consistent\n");
        }
        return sb.length() == 0 ? "pass" : sb.toString();
    }

    @Override
    boolean isBalanced() {
        return isBalanced(root) != -1;
    }

    @Override
    void delete() {
        delete(root);
        root = null;
    }

    private void delete(Node<K, V> h) {
        if (h == null) return;
        delete(h.left);
        delete(h.right);
        h.left = null;
        h.right = null;
    }

    private int isBalanced(Node<K, V> h) {
        if (h == null) return 0;
        int lh = isBalanced(h.left);
        int rh = isBalanced(h.right);
        if (lh == -1 || rh == -1 || Math.abs(lh - rh) > 1) {
            return -1;
        }
        return 1 + Math.max(lh, rh);
    }
}
