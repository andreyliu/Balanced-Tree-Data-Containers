package org.andreyliu.containers.disjointset;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

public class UFMedium {

    private String path = "src/test/resources/mediumUF.txt";

    @Test
    public void staticTest() {
        try (Scanner sc = new Scanner(new BufferedReader(new FileReader(path)))) {
            int n = 0;
            if (sc.hasNextInt()) {
                n = sc.nextInt();
            }
            UF<Integer> uf = new UnionFindStatic(n);
            while (sc.hasNextInt()) {
                int p = sc.nextInt();
                int q = sc.nextInt();
                uf.union(p, q);
            }
            assertEquals(3, uf.count());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void dynamicTest() {
        try (Scanner sc = new Scanner(new BufferedReader(new FileReader(path)))) {
            int n = 0;
            if (sc.hasNextInt()) {
                n = sc.nextInt();
            }
            UF<Integer> uf = new UnionFind<>();
            for (int i = 0; i < n; i++) {
                uf.add(i);
            }
            while (sc.hasNextInt()) {
                int p = sc.nextInt();
                int q = sc.nextInt();
                uf.union(p, q);
            }
            assertEquals(3, uf.count());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

}
