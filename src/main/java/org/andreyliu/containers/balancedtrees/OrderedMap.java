package org.andreyliu.containers.balancedtrees;

/**
 * Ordered map representing a map container of keys to values, where the keys
 * have a total order.
 * The get, put, delete, contains operations should have O(log(n)) worse case
 * complexity, where n is the number of entries in the map container.
 * @param <K> key
 * @param <V> value
 */
public interface OrderedMap<K extends Comparable<? super K>, V> {
    /**
     * @return size of the map
     */
    int size();

    /**
     * @return whether map is empty.
     */
    boolean isEmpty();
    /**
     * get the mapped value of the key.
     * @param k key
     * @return value mapped by key, or null if key does not exist.
     */
    V get(K k);

    /**
     * contains.
     * @param k key
     * @return whether map contains the key.
     */
    boolean contains(K k);

    /**
     * Puts the key-value mapping in the map. Replace the existing
     * mapping if key already in the map.
     * @param k key, non-nullable
     * @param val value, non-nullable
     */
    void put(K k, V val);

    /**
     * Delete the key-value mapping for the specified key if key is in the map.
     * @param k key, non-nullable
     */
    void delete(K k);

    /**
     * Delete minimum key entry.
     */
    void deleteMin();
    /**
     * Delete maximum key entry.
     */
    void deleteMax();

    /**
     * @return minimum key
     */
    K min();

    /**
     * @return maximum key
     */
    K max();

    /**
     * @param k key, non-nullable
     * @return the floor key if exists, or null
     */
    K floor(K k);
    /**
     * @param k key, non-nullable
     * @return the ceiling key if exists, or null
     */
    K ceiling(K k);

    /**
     * Select the key with the given rank.
     * @param k rank (from 0 to size() - 1)
     * @return null if rank is not valid, otherwise return the key with the rank
     */
    K select(int k);

    /**
     * Returns the rank of the key given.
     * @param k key
     * @return rank (from 0 to size() - 1)
     */
    int rank(K k);

    /**
     * Returns an iterable collection of all the keys in the map in order.
     * @return all the keys in order
     */
    Iterable<K> keys();

    /**
     * Returns an iterable collection of all the keys in the map in order
     * from lo to hi (inclusive).
     * @param lo lower bound
     * @param hi upper bound (inclusive)
     * @return range of keys in order
     */
    Iterable<K> keys(K lo, K hi);

    /**
     * Retursn the number of keys in the map within the range.
     * @param from lower bound
     * @param to upper bound (inclusive)
     * @return number of keys in the given range.
     */
    int size(K from, K to);

    /**
     * Represents an entry in the Map that maps key to value
     * @param <K> key
     * @param <V> value
     */
    interface Entry<K extends Comparable<? super K>, V> {
        K getKey();
        V getValue();
    }
}
