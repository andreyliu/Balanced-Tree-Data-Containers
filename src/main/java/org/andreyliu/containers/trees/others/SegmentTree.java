package org.andreyliu.containers.trees.others;

import com.google.common.math.IntMath;

import java.math.RoundingMode;
import java.util.Objects;

/**
 * Segment tree with lazy propagation.
 * Lazy propagation ensures update does not fall to O(n).
 */
public class SegmentTree {

    private final int[] arr;

    private final Node[] heap;

    private static class Node {
        int sum;
        int min;
        Integer pendingVal;
        final int from;
        final int to;

        Node(int from, int to) {
            if (from > to) {
                throw new IllegalArgumentException("invalid interval");
            }
            this.from = from;
            this.to = to;
        }

        int size() {
            return to - from + 1;
        }

        boolean contains(int from, int to) {
            return this.from <= from && this.to >= to;
        }

        boolean isContainedIn(int from, int to) {
            return from <= this.from && to >= this.to;
        }
        boolean intersects(int from, int to) {
            return Math.max(this.from, from) <= Math.min(this.to, to);
        }
    }

    public SegmentTree(Integer[] arr) {
        Objects.requireNonNull(arr);
        int n = arr.length;
        if (n == 0) throw new IllegalArgumentException("cannot build segment tree from empty array");
        this.arr = new int[n];
        int i = 0;
        for (Integer num : arr) {
            this.arr[i++] = num;
        }
        int size = (int) Math.pow(2, IntMath.log2(n, RoundingMode.CEILING));
        this.heap = new Node[size];
        buildHeap(1, 0, n - 1);
    }

    // v starts from 1
    private void buildHeap(int v, int from, int to) {
        Node n = new Node(from, to);
        heap[v] = n;
        if (from == to) {
            n.sum = arr[from];
            n.min = arr[from];
            return;
        }
        int mid = from + (to - from) / 2;
        buildHeap(v << 1, from, mid);
        buildHeap(v << 1 | 1, mid + 1, to);
        n.sum = heap[v << 1].sum + heap[v << 1 | 1].sum;
        n.min = Math.min(heap[v << 1].min, heap[v << 1 | 1].min);
    }

    /**
     * @return size of the collection of points represented by this segment tree.
     */
    public int size() {
        return arr.length;
    }
    /**
     * Performs a range sum query.
     * From and to are zero-indexed.
     * @param from left bound, inclusive.
     * @param to right bound, inclusive.
     * @return range sum of the data
     */
    public int rsq(int from, int to) {
        return rsq(1, from, to);
    }
    // v starts with 1.
    private int rsq(int v, int from, int to){
        Node h = heap[v];
        if (!h.intersects(from, to)) {
            return 0;
        }
        // we first check for pending values
        if (h.pendingVal != null && h.contains(from, to)) {
            return (from - to + 1) * h.pendingVal;
        }
        // then check if h matches the interval query
        if (h.isContainedIn(from, to)) {
            return h.sum;
        }
        propagate(v);
        int leftSum = rsq(v << 1, from, to);
        int rightSum = rsq(v << 1 | 1, from, to);
        return leftSum + rightSum;
    }
    // should only be called with non leaf nodes.
    private void propagate(int v) {
        Node h = heap[v];

        if (h.pendingVal == null) return;

        change(heap[v << 2], h.pendingVal);
        change(heap[v << 2 | 1], h.pendingVal);
        arr[h.from] = h.pendingVal;
        h.pendingVal = null;
    }

    private void change(Node h, int val) {
        h.pendingVal = val;
        h.sum = h.size() * val;
        h.min = val;
    }
    /**
     * Performs a range min query.
     * From and to are zero-indexed.
     * @param from left bound, inclusive.
     * @param to right bound, inclusive.
     * @return range min of the data. When input interval is not in the tree, return Integer.MAX_VALUE
     *
     */
    public int rMinQ(int from, int to) {
        return rMinQ(1, from, to);
    }

    private int rMinQ(int v, int from, int to) {
        Node h = heap[v];
        if (!h.intersects(from, to)) {
            return Integer.MAX_VALUE;
        }
        if (h.contains(from, to) || h.isContainedIn(from, to)) {
            return h.min;
        }
        propagate(v);
        int leftMin = rMinQ(v << 1, from, to);
        int rightMin = rMinQ(v << 1 | 1, from, to);
        return Math.min(leftMin, rightMin);
    }
    /**
     * Performs a range update on the data. Sets the range of data specified
     * by from and to to the given value.
     * From and to are zero-indexed.
     * @param from left bound, inclusive.
     * @param to right bound, inclusive.
     * @param value value
     */
    public void update(int from, int to, int value) {
        update(1, from, to, value);
    }

    private void update(int v, int from, int to, int value) {
        Node h = heap[v];
        if (!h.intersects(from, to)) {
            return;
        }
        if (h.isContainedIn(from, to)) {
            // if h is contained update range, lazily update h.
            change(h, value);
            return;
        }
        // propagate previous updates that might not completely
        // overlap with this update
        propagate(v);
        update(v << 1, from, to, value);
        update(v << 1 | 1, from, to, value);
        h.sum = heap[v << 1].sum + heap[v << 1 | 1].sum;
        h.min = Math.min(heap[v << 1].min, heap[v << 1 | 1].min);
    }
}
