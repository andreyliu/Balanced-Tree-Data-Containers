package org.andreyliu.containers.disjointset;

import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import static junit.framework.TestCase.assertEquals;

public class TinyUFDynamicTest {

    UF<Integer> uf = new UnionFind<>();

    @Before
    public void initialize() {
        try (Scanner sc = new Scanner(new FileReader("src/test/resources/TinyUF.txt"))) {
            if (sc.hasNextLine()) {
                int max = Integer.parseInt(sc.nextLine());
                for (int i = 0; i < max; i++) {
                    uf.add(i);
                }
            }
            while (sc.hasNextLine()) {
                String[] tokens = sc.nextLine().split("\\s");
                uf.union(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testCount() {
        assertEquals(2, uf.count());
        uf.union(1, 9);
        assertEquals(1, uf.count());
    }

    @Test
    public void testToString() {
        TinyUFStaticTest.testString(uf.toString());
    }
}
