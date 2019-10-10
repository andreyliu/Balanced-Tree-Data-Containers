package BalancedTrees;

import java.util.Objects;
import java.util.Queue;

public abstract class BST<K extends Comparable<? super K>, V> implements OrderedMap<K, V> {

    static abstract class INode<K extends Comparable<? super K>, V> implements OrderedMap.Entry<K, V> {
        K key;
        V val;
        int size = 1;
        INode(K key, V val) {
            this.key = key;
            this.val = val;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return val;
        }

        abstract INode<K, V> getLeft();
        abstract INode<K, V> getRight();
    }
    int size(INode<K, V> h) {
        return h == null ? 0 : h.size;
    }
    V get(INode<K, V> h, K k) {
        if (h == null) return null;
        int comp = k.compareTo(h.key);
        if (comp < 0) {
            return get(h.getLeft(), k);
        }
        if (comp > 0) {
            return get(h.getRight(), k);
        }
        return h.val;
    }

    @Override
    public boolean contains(K k) {
        return get(k) != null;
    }

    INode<K, V> min(INode<K, V> h) {
        if (h.getLeft() == null) return h;
        return min(h.getLeft());
    }

    INode<K, V> max(INode<K, V> h) {
        if (h.getRight() == null) return h;
        return max(h.getRight());
    }

    INode<K, V> floor(INode<K, V> h, K k) {
        if (h == null) return null;
        int comp = h.key.compareTo(k);
        if (comp < 0) {
            INode<K, V> t = floor(h.getRight(), k);
            return t == null ? h : t;
        }
        if (comp > 0) {
            return floor(h.getLeft(), k);
        }
        return h;
    }

    INode<K, V> ceiling(INode<K, V> h, K k) {
        if (h == null) return null;
        int comp = h.key.compareTo(k);
        if (comp > 0) {
            INode<K, V> t = ceiling(h.getLeft(), k);
            return t == null ? h : t;
        }
        if (comp < 0) {
            return ceiling(h.getRight(), k);
        }
        return h;
    }

    INode<K, V> select(INode<K, V> h, int k) {
        int rank = size(h.getLeft());
        if (rank > k) {
            return select(h.getLeft(), k);
        }
        if (rank < k) {
            return select(h.getRight(), k - rank - 1);
        }
        return h;
    }

    int rank(INode<K, V> h, K k) {
        // how many keys are strictly less than k?
        if (h == null) return 0;
        int comp = h.key.compareTo(k);
        if (comp < 0) {
            return 1 + size(h.getLeft()) + rank(h.getRight(), k);
        }
        return rank(h.getLeft(), k);
    }

    void keys(INode<K, V> h, K lo, K hi, Queue<K> q) {
        if (h == null) return;
        int loComp = lo.compareTo(h.key);
        int hiComp = hi.compareTo(h.key);
        if (loComp < 0) {
            keys(h.getLeft(), lo, hi, q);
        }
        if (loComp <= 0 && hiComp >= 0) {
            q.offer(h.key);
        }
        if (hiComp > 0) {
            keys(h.getRight(), lo, hi, q);
        }
    }

    @Override
    public int size(K from, K to) {
        Objects.requireNonNull(from);
        Objects.requireNonNull(to);
        if (from.compareTo(to) > 0) {
            return 0;
        }
        if (contains(to)) return rank(to) - rank(from) + 1;

        return rank(to) - rank(from);
    }

    abstract String check();
}
