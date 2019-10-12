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
     * @param from left bound, inclusive.
     * @param to right bound, inclusive.
     * @return range sum of the data
     */
    public int rsq(int from, int to) {


    }
    /**
     * Performs a range min query.
     * @param from left bound, inclusive.
     * @param to right bound, inclusive.
     * @return range min of the data
     */
    public int rMinQ(int from, int to) {


    }
    /**
     * Performs a range update on the data. Sets the range of data specified
     * by from and to to the given value.
     * @param from left bound, inclusive.
     * @param to right bound, inclusive.
     * @param value value
     */
    public void update(int from, int to, Integer value) {

    }
}
