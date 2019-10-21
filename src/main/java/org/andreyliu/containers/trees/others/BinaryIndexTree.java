package org.andreyliu.containers.trees.others;

import java.util.Objects;

/**
 * This {@code Binary Index Tree} supports range sum query and range update with {@code O(log n)}
 * time complexity. Note that {@code update} operations only supports adding by an increment but doesn't support
 * resetting values. For single point reset, user can keep a copy of the original values and calculate the difference
 * before calling the {@link #add(int, int)} method.
 * {@link SegmentTree} may be a better option if range update is requires resetting values.
 */
public class BinaryIndexTree {
    private final int[] d1;
    private final int[] d2;

    public BinaryIndexTree(int n) {
        if (n <= 0) throw new IllegalArgumentException();
        d1 = new int[n + 1];
        d2 = new int[n + 1];
    }

    public BinaryIndexTree(int[] a) {
        int n = Objects.requireNonNull(a).length;
        d1 = new int[n + 1];
        d2 = new int[n + 1];
        for (int i = n; i-- > 1; ) {
            a[i] = a[i] - a[i - 1];
        }
        build(a);
    }

    private void build(int[] d) {
        for (int i = 0; i < d.length; i++) {
            d1[i + 1] = d[i];
            d2[i + 1] = (i + 1) * d[i];
        }
        for (int i = 1; i < d1.length; i++) {
            int next = i + lowbit(i);
            if (next < d1.length) {
                d1[next] += d1[i];
                d2[next] += d2[i];
            }
        }

    }

    private int lowbit(int index) {
        return index & -index;
    }

    private void addInternal(int i, int v) {
        for (int k = i + 1; k < d1.length; k += lowbit(k)) {
            d1[k] += v;
            d2[k] += (i + 1) * v;
        }
    }

    public void add(int i, int v) {
        addInternal(i, v);
        addInternal(i + 1, -v);
    }

    public void add(int from, int to, int v) {
        addInternal(from, v);
        addInternal(to + 1, -v);
    }

    public int rangeSum(int to) {
        int sum = 0;
        for (int k = to + 1; k > 0; k -= lowbit(k)) {
            sum += (to + 2) * d1[k] - d2[k];
        }
        return sum;
    }

    public int rangeSum(int from, int to) {
        return rangeSum(to) - rangeSum(from - 1);
    }

}
