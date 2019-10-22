package org.andreyliu.containers.disjointset;

import org.checkerframework.checker.units.qual.K;
import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.Map;

public class UnionFindStatic implements UF<Integer> {
    private final int[] roots;
    private final int[] ranks;
    private int count;
    /**
     * Initialize a {@link UF} with given size.
     * Initial number of connect components will be the same as size.
     * @param size initial size
     */
    public UnionFindStatic(int size) {
        this(size, size);
    }

    /**
     * Initialize a {@link UF} with given size.
     * @param size initial size
     * @param count count must be less than initial {@code size}
     */
    public UnionFindStatic(int size, int count) {
        if (size <= 0) throw new IllegalArgumentException();
        roots = new int[size];
        for (int i = 0; i < size; i++) {
            roots[i] = i;
        }
        ranks = new int[size];
        this.count = count;
    }

    @Override
    public void union(Integer k1, Integer k2) {
        checkIndex(k1); checkIndex(k2);
        int r1 = find(k1);
        int r2 = find(k2);
        if (r1 == r2) return;
        if (ranks[r1] > ranks[r2]) {
            roots[r2] = r1;
        } else if (ranks[r2] > ranks[r1]) {
            roots[r1] = r2;
        } else {
            roots[r2] = r1;
            ranks[r1]++;
        }
        count--;
    }

    private int find(int i) {
        if (roots[i] == i) return i;
        return roots[i] = find(roots[i]);
    }

    private int find2(int i) {
        while (roots[i] != i) {
            roots[i] = roots[roots[i]];
            i = roots[i];
        }
        return i;
    }

    @Override
    public boolean connected(Integer k1, Integer k2) {
        checkIndex(k1); checkIndex(k2);
        return find(k1) == find(k2);
    }

    @Override
    public int count() {
        return count;
    }

    private void checkIndex(int k) {
        if (k < 0 || k >= roots.length) {
            throw new IndexOutOfBoundsException(k);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        final String format = "%-10s";
        String header = String.format(format, "root");
        sb.append(header);
        sb.append(", members\n");
        addContents(sb, format);

        return sb.toString();
    }

    private void addContents(StringBuilder sb, String fmt) {
        Map<Integer, StringBuilder> components = new HashMap<>();
        for (int i = 0; i < roots.length; i++) {
            if (roots[i] == i) {
                components.put(i, new StringBuilder(String.format(fmt, String.valueOf(i))));
            }
        }
        for (int i = 0; i < roots.length; i++) {
            if (roots[i] != i) {
                components.get(find(i)).append(String.format(", %s", i));
            }
        }
        for (StringBuilder s : components.values()) {
            s.append('\n');
            sb.append(s.toString());
        }
    }
}
