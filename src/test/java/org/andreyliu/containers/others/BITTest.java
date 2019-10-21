package org.andreyliu.containers.others;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class BITTest {

    @Test
    public void test1() {
        int[] a = {1, 2, 3, 4, 5};
        BinaryIndexTree bit = new BinaryIndexTree(a);

        assertThat(bit.rangeSum(4), is(equalTo(15)));
        assertThat(bit.rangeSum(0, 4), is(equalTo(15)));
        assertThat(bit.rangeSum(2, 4), is(equalTo(12)));

        bit.add(0,10);

        assertThat(bit.rangeSum(4), is(equalTo(25)));
        assertThat(bit.rangeSum(0, 4), is(equalTo(25)));
        assertThat(bit.rangeSum(2, 4), is(equalTo(12)));

        bit.add(1, 4, -1);
        assertThat(bit.rangeSum(4), is(equalTo(21)));
        assertThat(bit.rangeSum(0, 4), is(equalTo(21)));
        assertThat(bit.rangeSum(2, 4), is(equalTo(9)));
    }

    @Test
    public void test2() {
        BinaryIndexTree bit = new BinaryIndexTree(10);
        assertThat(bit.rangeSum(4), is(equalTo(0)));

        bit.add(5, 1);
        assertThat(bit.rangeSum(4), is(equalTo(0)));
        assertThat(bit.rangeSum(5), is(equalTo(1)));
        assertThat(bit.rangeSum(5, 5), is(equalTo(1)));
        assertThat(bit.rangeSum(6, 9), is(equalTo(0)));

        bit.add(5, -11);
        assertThat(bit.rangeSum(4), is(equalTo(0)));
        assertThat(bit.rangeSum(5), is(equalTo(-10)));
        assertThat(bit.rangeSum(5, 5), is(equalTo(-10)));
        assertThat(bit.rangeSum(6, 9), is(equalTo(0)));

    }
}
