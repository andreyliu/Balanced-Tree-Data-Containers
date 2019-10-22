package org.andreyliu.containers.disjointset;

import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

import static junit.framework.TestCase.assertEquals;

public class TinyUFStaticTest {
    UF<Integer> uf;

    @Before
    public void init() {
        initializeFromFile("src/test/resources/TinyUF.txt");
    }

    private void initializeFromFile(String path) {
        try (Scanner sc = new Scanner(new FileReader(path))) {
            if (sc.hasNextLine()) {
                String token = sc.nextLine();
                uf = new UnionFindStatic(Integer.parseInt(token));

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
    public void testAdd() {
        assertEquals(2, uf.count());
        uf.union(1, 9);
        assertEquals(1, uf.count());
    }

    @Test
    public void testToString() {
        testString(uf.toString());

    }

    static void testString(String s) {
        Scanner sc = new Scanner(s);
        Set<Set<Integer>> rSet = new HashSet<>();
        sc.nextLine();
        while (sc.hasNextLine()) {
            Set<Integer> curr = new HashSet<>();
            String[] tokens = sc.nextLine().replace(',', ' ').split("\\s+");
            for (String t : tokens) {
                curr.add(Integer.parseInt(t));
            }
            rSet.add(curr);
        }
        assertEquals(2, rSet.size());
        Set<Integer> s1 = new HashSet<>(Arrays.asList(4, 3, 8, 9));
        Set<Integer> s2 = new HashSet<>(Arrays.asList(6, 0, 1, 2, 5, 7));
        Set<Set<Integer>> rSet2 = new HashSet<>();
        rSet2.add(s1);
        rSet2.add(s2);
        assertEquals(rSet2, rSet);
    }

}
