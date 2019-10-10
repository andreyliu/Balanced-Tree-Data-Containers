package BalancedTrees;

public interface OrderedMap<K extends Comparable<? super K>, V> {
    int size();
    boolean isEmpty();
    V get(K k);
    boolean contains(K k);
    void put(K k, V val);
    void delete(K k);
    void deleteMin();
    void deleteMax();
    int height();
    K min();
    K max();
    K floor(K k);
    K ceiling(K k);
    K select(int k);
    int rank(K k);
    Iterable<K> keys();
    Iterable<K> keys(K lo, K hi);
    int size(K from, K to);

    interface Entry<K extends Comparable<? super K>, V> {
        K getKey();
        V getValue();
    }
}
