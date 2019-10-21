package org.andreyliu.containers.disjointset;

import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class UnionFind<K> implements UF<K> {
    private final Map<K, Node> index;
    private int count = 0;
    public UnionFind() {
        index = new HashMap<>();
    }

    @Override
    public void union(K k1, K k2) {
        check(k1); check(k2);
        Node r1 = find(index.get(k1));
        Node r2 = find(index.get(k2));
        if (r1 == r2) return;
        if (r1.rank > r2.rank) {
            r1.parent = r1;
        } else if (r2.rank > r1.rank) {
            r1.parent = r2;
        } else {
            r2.parent = r1;
            r1.rank++;
        }
    }

    private void check(K k) {
        if (!index.containsKey(k)) {
            throw new NoSuchElementException(k.toString());
        }
    }

    private Node find(Node n) {
        if (n.parent == n) return n;
        return n.parent = find(n.parent);
    }

    @Override
    public boolean connected(K k1, K k2) {
        check(k1); check(k2);
        return find(index.get(k1)) == find(index.get(k2));
    }

    @Override
    public boolean add(K k) {
        if (index.containsKey(k)) return false;
        index.put(k, new Node());
        count++;
        return true;
    }

    @Override
    public int count() {
        return count;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        final String format = "%-10s, %s\n";
        String header = String.format(format, "root", "members");
        sb.append(header);
        addContents(sb);
        return sb.toString();
    }

    private void addContents(StringBuilder sb) {
        Map<Node, StringBuilder> components = new HashMap<>();
        for (Map.Entry<K, Node> e : index.entrySet()) {
            Node n = e.getValue();
            if (n.parent == n) {
                components.put(n, new StringBuilder(e.getKey().toString()));
            }
        }
        for (Map.Entry<K, Node> e : index.entrySet()) {
            Node n = e.getValue();
            if (n.parent != n) {
                Node p = find(n);
                StringBuilder s = components.get(p);
                s.append(',');
                s.append(' ');
                s.append(e.getKey().toString());
            }
        }
        for (StringBuilder s : components.values()) {
            sb.append(s.toString());
        }
    }
    private static class Node {
        Node parent = this;
        int rank = 0;
    }
}
