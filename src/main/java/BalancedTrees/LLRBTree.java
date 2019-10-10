package BalancedTrees;

import java.util.ArrayDeque;
import java.util.Objects;
import java.util.Queue;

public class LLRBTree<K extends Comparable<? super K>, V> extends BST<K, V> {

    private static final boolean RED = true;
    private static final boolean BLACK = false;
    private Node<K, V> root;

    static class Node<K extends Comparable<? super K>, V> extends INode<K, V> {
        boolean color = RED;
        Node<K, V> left;
        Node<K, V> right;
        Node(K key, V val) {
            super(key, val);
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

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public V get(K k) {
        Objects.requireNonNull(k);

        return get(root, k);
    }

/*    private V get(Node<K, V> h, K k) {
        if (h == null) return null;
        int comp = k.compareTo(h.key);
        if (comp < 0) {
            return get(h.left, k);
        }
        if (comp > 0) {
            return get(h.right, k);
        }
        return h.val;
    }*/
//
//    @Override
//    public boolean contains(K k) {
//        return get(k) != null;
//    }

    private boolean red(Node<K, V> n) {
        return n != null && n.color == RED;
    }

    private Node<K, V> rotateLeft(Node<K, V> x) {
        assert(red(x.right));
        Node<K, V> h = x.right;
        x.right = h.left;
        h.left = x;

        h.color = x.color;
        x.color = RED;

        h.size = x.size;
        x.size = 1 + size(x.left) + size(x.right);
        return h;
    }

    private Node<K, V> rotateRight(Node<K, V> x) {
        assert(red(x.left));
        Node<K, V> h = x.left;
        x.left = h.right;
        h.right = x;

        h.color = x.color;
        x.color = RED;

        h.size = x.size;
        x.size = 1 + size(x.left) + size(x.right);
        return h;
    }

    private void flip(Node<K, V> h) {
        assert(h.left != null && h.right != null);
        assert (h.color != h.left.color && h.color != h.right.color);
        h.color = !h.color;
        h.left.color = !h.left.color;
        h.right.color = !h.right.color;
    }

    private Node<K, V> balance(Node<K, V> h) {
        if (red(h.right) && !red(h.left)) {
            h = rotateLeft(h);
        }
        if (red(h.left) && red(h.left.left)) {
            h = rotateRight(h);
        }
        if (red(h.left) && red(h.right)) {
            flip(h);
        }
        h.size = 1 + size(h.left) + size(h.right);
        return h;
    }

    @Override
    public void put(K k, V v) {
        Objects.requireNonNull(k);
        root = put(root, k, v);
        root.color = BLACK;

    }

    private Node<K, V> put(Node<K, V> h, K key, V val) {
        if (h == null) return new Node<>(key, val);

        int comp = key.compareTo(h.key);
        if (comp < 0) {
            h.left = put(h.left, key, val);
        } else if (comp > 0) {
            h.right = put(h.right, key, val);
        } else {
            h.val = val;
            return h;
        }
        return balance(h);
    }

    @Override
    public void delete(K k) {
        Objects.requireNonNull(k);
        if (!contains(k)) return;
        root.color = RED;
        root = delete(root, k);
        if (!isEmpty()) root.color = BLACK;
    }

    private Node<K, V> delete(Node<K, V> h, K k) {
        if (k.compareTo(h.key) < 0) {
            if (!red(h.left) && !red(h.left.left)) {
                h = groupLeftFour(h);
            }
            h.left = delete(h.left, k);
        } else {
            if (red(h.left)) h = rotateRight(h);
            if (h.right == null) {
                return null;
            }
            if (!red(h.right) && !red(h.right.left)) {
                h = groupRightFour(h);
            }
            if (k.compareTo(h.key) > 0) {
                h.right = delete(h.right, k);
            } else {
                INode<K, V> succ = min(h.right);
                h.key = succ.key;
                h.val = succ.val;
                h.right = deleteMin(h.right);
            }
        }
        return balance(h);
    }

    @Override
    public void deleteMin() {
        if (isEmpty()) return;
        root.color = RED;
        root = deleteMin(root);
        if (!isEmpty()) root.color = BLACK;
    }

    private Node<K, V> deleteMin(Node<K, V> h) {
        if (h.left == null) return null;
        if (!red(h.left) && !red(h.left.left)) {
            h = groupLeftFour(h);
        }
        h.left = deleteMin(h.left);
        return balance(h);
    }

    private Node<K, V> groupLeftFour(Node<K, V> h) {
        assert(h.left != null);
        assert(!red(h.left) && !red(h.left.left));

        flip(h);
        if (red(h.right.left)) {
            h.right = rotateRight(h.right);
            h = rotateLeft(h);
            flip(h);
        }
        return h;
    }

    @Override
    public void deleteMax() {
        if (isEmpty()) return;
        root.color = RED;
        root = deleteMax(root);
        if (!isEmpty()) root.color = BLACK;
    }

    private Node<K, V> deleteMax(Node<K, V> h) {
        if (red(h.left)) h = rotateRight(h);
        if (h.right == null) return null;
        if (!red(h.right) && !red(h.right.left)) {
            h = groupRightFour(h);
        }
        h.right = deleteMax(h.right);
        return balance(h);
    }

    private Node<K, V> groupRightFour(Node<K, V> h) {
        assert(h.right != null);
        assert(!red(h.right) && !red(h.right.left));

        flip(h);
        if (red(h.left.left)) {
            h = rotateRight(h);
            flip(h);
        }
        return h;
    }

    @Override
    public int height() {
        return height(root);
    }

    private int height(Node<K, V> h) {
        if (h == null) return -1;
        return 1 + Math.max(height(h.left), height(h.right));
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

    String check() {
        StringBuilder sb = new StringBuilder();
        if (!isBST())            sb.append("Not in symmetric order\n");
        if (!isSizeConsistent()) sb.append("Subtree counts not consistent\n");
        if (!isRankConsistent()) sb.append("Ranks not consistent\n");
        if (!is23())             sb.append("Not a 2-3 tree\n");
        if (!isBalanced())       sb.append("Not balanced\n");
        return sb.length() == 0 ? "pass" : sb.toString();
    }

    private boolean isBalanced() {
        return isBalanced(root) != -1;
    }

    private int isBalanced(Node<K, V> h) {
        if (h == null) return 1;
        int l = isBalanced(h.left);
        int r = isBalanced(h.right);
        if (l == -1 || r == -1) return -1;
        if (l != r) return -1;
        int curr = red(h) ? 0 : 1;
        return curr + l;
    }

    private boolean is23() {
        return is23(root);
    }

    private boolean is23(Node<K, V> h) {
        if (h == null) return true;
        if (red(h.right)) return false;
        if (red(h) && red(h.left)) return false;
        return is23(h.left) && is23(h.right);
    }

    private boolean isRankConsistent() {

        for (int i = 0; i < size(); i++) {
            if (i != rank(select(i))) {
                return false;
            }
        }

        for (K key : this.keys()) {
            if (key.compareTo(select(rank(key))) != 0) {
                return false;
            }
        }
        return true;
    }

    private boolean isBST() {
        return isBST(root, null, null);
    }
    private boolean isBST(Node<K, V> h, K min, K max) {
        if (h == null) return true;
        if (min != null && min.compareTo(h.key) >= 0) {
            return false;
        }
        if (max != null && max.compareTo(h.key) <= 0) {
            return false;
        }
        return isBST(h.left, min, h.key) && isBST(h.right, h.key, max);
    }

    private boolean isSizeConsistent() {
        return isSizeConsistent(root);
    }

    private boolean isSizeConsistent(Node<K, V> h) {
        if (h == null) return true;
        int expected = 1 + size(h.left) + size(h.right);
        if (h.size != expected) {
            return false;
        }
        return isSizeConsistent(h.left) && isSizeConsistent(h.right);
    }
}
