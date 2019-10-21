package org.andreyliu.containers.others;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SegTreeTest {

    @Test
    public void segTreeTest() {

        int[] arr = {1, 2, 3, 4, 5};
        SegmentTree st = new SegmentTree(arr);

        assertEquals(2, st.rMinQ(1, 4));
        assertEquals(5, st.rsq(1, 2));

        st.update(0, 1, 5);

        assertEquals(3, st.rMinQ(1, 4));
        assertEquals(8, st.rsq(1, 2));

        st.update(1, 4, 1);

        assertEquals(1, st.rMinQ(0, 4));
        assertEquals(4, st.rsq(1, 4));

        st.update(4, 4, 0);

        assertEquals(0, st.rMinQ(2, 4));
        assertEquals(3, st.rsq(1, 4));

        System.out.println(st);
    }
}
