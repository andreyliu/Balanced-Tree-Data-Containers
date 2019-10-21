package org.andreyliu.containers.disjointset;

public interface UF<K> {
    void union(K k1, K k2);

    boolean connected(K k1, K k2);

    default boolean add(K k) {
        throw new UnsupportedOperationException("Method add not supported!");
    }

    int count();
}
