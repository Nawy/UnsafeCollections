package org.megalabs.collection.advanced;

import org.junit.Test;

import java.util.NoSuchElementException;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by ermolaev on 4/7/17.
 */
public class AdvArrayListTest {

    @Test
    public void test_add() {
        final String oneValue = "One";
        final String twoValue = "Two";
        final String threeValue = "Three";
        AdvList<String> list = new AdvArrayList<>();
        list.add(oneValue);
        list.add(twoValue);
        list.add(threeValue);

        assertThat(list.get(0)).isEqualTo(oneValue);
        assertThat(list.get(1)).isEqualTo(twoValue);
        assertThat(list.get(2)).isEqualTo(threeValue);
    }

    @Test(expected = NoSuchElementException.class)
    public void test_get_throwsExceptionMoreSizeIndex() {
        final String oneValue = "One";
        final String twoValue = "Two";
        final String threeValue = "Three";
        AdvList<String> list = new AdvArrayList<>();
        list.add(oneValue);
        list.add(twoValue);
        list.add(threeValue);

        list.get(4);
    }

    @Test(expected = NoSuchElementException.class)
    public void test_get_throwsExceptionLowZeroIndex() {
        final String oneValue = "One";
        final String twoValue = "Two";
        final String threeValue = "Three";
        AdvList<String> list = new AdvArrayList<>();
        list.add(oneValue);
        list.add(twoValue);
        list.add(threeValue);

        list.get(-1);
    }

    @Test
    public void test_growCapacityScaleFactor2() {
        final int capacity = 10;
        final int scaleFactor = 2;
        AdvList<Integer> list = new AdvArrayList<>(capacity, scaleFactor);
        for(int i = 0; i < 100; i++) {
            list.add(i);
        }
        assertThat(list.capacity()).isEqualTo(160);
    }

    @Test
    public void test_containsWithOne() {
        AdvList<Integer> list = new AdvArrayList<>();
        list.add(1);

        assertThat(list.contains(1)).isTrue();
        assertThat(list.contains(3)).isFalse();
    }

    @Test
    public void test_containsWithThree() {
        AdvList<Integer> list = new AdvArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        assertThat(list.contains(1)).isTrue();
        assertThat(list.contains(2)).isTrue();
        assertThat(list.contains(3)).isTrue();
        assertThat(list.contains(4)).isFalse();
    }

    @Test
    public void test_containsWithZero() {
        AdvList<Integer> list = new AdvArrayList<>();

        assertThat(list.contains(4)).isFalse();
    }
}
