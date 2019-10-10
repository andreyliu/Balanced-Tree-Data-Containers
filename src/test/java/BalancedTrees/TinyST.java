package BalancedTrees;

import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

class TinyST<T extends BST<String, Integer>> {

    private T st;

    private ByteArrayOutputStream os = new ByteArrayOutputStream();
    private PrintStream ps = new PrintStream(os);

    TinyST(Class<? extends T> c) {
        try {
            st = c.getDeclaredConstructor().newInstance();
        } catch (InstantiationException
                | NoSuchMethodException
                | IllegalAccessException
                | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        buildTinyST(st);
    }

    static void buildTinyST(OrderedMap<String, Integer> st) {
        try (Scanner sc = new Scanner(new FileReader("src/test/resources/tinyST.txt"))) {
            int i = 0;
            while (sc.hasNext()) {
                st.put(sc.next(), i++);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void testInsert() {

        ps.println("size = " + st.size());
        ps.println("min  = " + st.min());
        ps.println("max  = " + st.max());

        String add =
                "size = 10\n" +
                        "min  = A\n" +
                        "max  = X\n";
        assertThat(os.toString(), equalTo(add));
    }

    void testKeys() {
        for (String s : st.keys())
            ps.println(s + " " + st.get(s));

        String keys =
                "A 8\n" +
                        "C 4\n" +
                        "E 12\n" +
                        "H 5\n" +
                        "L 11\n" +
                        "M 9\n" +
                        "P 10\n" +
                        "R 3\n" +
                        "S 0\n" +
                        "X 7\n";
        assertThat(os.toString(), equalTo(keys));
    }

    void testSelect() {
        for (int i = 0; i < st.size(); i++)
            ps.println(i + " " + st.select(i));

        String select =
                "0 A\n" +
                        "1 C\n" +
                        "2 E\n" +
                        "3 H\n" +
                        "4 L\n" +
                        "5 M\n" +
                        "6 P\n" +
                        "7 R\n" +
                        "8 S\n" +
                        "9 X\n";

        assertThat(os.toString(), equalTo(select));
    }

    void testFloorCeil() {
        ps.println("key rank floor ceil");
        ps.println("-------------------");
        for (char i = 'A'; i <= 'Z'; i++) {
            String s = i + "";
            ps.printf("%2s %4d %4s %4s\n", s, st.rank(s), st.floor(s), st.ceiling(s));
        }

        String floorCeil =
                "key rank floor ceil\n" +
                        "-------------------\n" +
                        " A    0    A    A\n" +
                        " B    1    A    C\n" +
                        " C    1    C    C\n" +
                        " D    2    C    E\n" +
                        " E    2    E    E\n" +
                        " F    3    E    H\n" +
                        " G    3    E    H\n" +
                        " H    3    H    H\n" +
                        " I    4    H    L\n" +
                        " J    4    H    L\n" +
                        " K    4    H    L\n" +
                        " L    4    L    L\n" +
                        " M    5    M    M\n" +
                        " N    6    M    P\n" +
                        " O    6    M    P\n" +
                        " P    6    P    P\n" +
                        " Q    7    P    R\n" +
                        " R    7    R    R\n" +
                        " S    8    S    S\n" +
                        " T    9    S    X\n" +
                        " U    9    S    X\n" +
                        " V    9    S    X\n" +
                        " W    9    S    X\n" +
                        " X    9    X    X\n" +
                        " Y   10    X null\n" +
                        " Z   10    X null\n";

        assertThat(os.toString(), equalTo(floorCeil));
    }

    void testRangeSearch() {
        String[] from = { "A", "Z", "X", "0", "B", "C" };
        String[] to   = { "Z", "A", "X", "Z", "G", "L" };
        for (int i = 0; i < from.length; i++) {
            ps.printf("%s-%s (%2d) : ", from[i], to[i], st.size(from[i], to[i]));
            for (String s : st.keys(from[i], to[i]))
                ps.print(s + " ");
            ps.println();
        }

        String rangeSearch =
                "A-Z (10) : A C E H L M P R S X \n" +
                        "Z-A ( 0) : \n" +
                        "X-X ( 1) : X \n" +
                        "0-Z (10) : A C E H L M P R S X \n" +
                        "B-G ( 2) : C E \n" +
                        "C-L ( 4) : C E H L \n";

        assertThat(os.toString(), equalTo(rangeSearch));
    }

    void testDelete() {
        for (int i = 0; i < st.size() / 2; i++) {
            st.deleteMin();
            ps.println(st.size());
        }
        ps.println("After deleting the smallest " + st.size() / 2 + " keys");
        ps.println("--------------------------------");
        for (String s : st.keys())
            ps.println(s + " " + st.get(s));
        ps.println();
        // delete all the remaining keys
        while (!st.isEmpty()) {
            st.delete(st.select(st.size() / 2));
        }
        ps.println("After deleting the remaining keys");
        ps.println("--------------------------------");
        for (String s : st.keys())
            ps.println(s + " " + st.get(s));
        ps.println();

        ps.println("After adding back n keys");
        ps.println("--------------------------------");
        buildTinyST(st);
        for (String s : st.keys())
            ps.println(s + " " + st.get(s));

        String del =
                "9\n8\n7\n" +
                        "After deleting the smallest 3 keys\n" +
                        "--------------------------------\n" +
                        "H 5\n" +
                        "L 11\n" +
                        "M 9\n" +
                        "P 10\n" +
                        "R 3\n" +
                        "S 0\n" +
                        "X 7\n" +
                        "\n" +
                        "After deleting the remaining keys\n" +
                        "--------------------------------\n" +
                        "\n" +
                        "After adding back n keys\n" +
                        "--------------------------------\n" +
                        "A 8\n" +
                        "C 4\n" +
                        "E 12\n" +
                        "H 5\n" +
                        "L 11\n" +
                        "M 9\n" +
                        "P 10\n" +
                        "R 3\n" +
                        "S 0\n" +
                        "X 7\n";

        assertThat(os.toString(), equalTo(del));

    }
}
