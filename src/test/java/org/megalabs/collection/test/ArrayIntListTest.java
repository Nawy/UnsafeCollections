package org.megalabs.collection.test;

import org.junit.Test;
import org.megalabs.collections.ArrayIntList;

import java.io.IOException;
import java.util.ArrayList;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by ermolaev on 4/1/17.
 */
public class ArrayIntListTest {

    @Test
    public void test_addingAndGettingElements() throws IOException {
        try(ArrayIntList testList = new ArrayIntList()) {
            testList.add(10);
            testList.add(24);
            testList.add(12345);

            assertThat(testList.get(0) == 10).isTrue();
            assertThat(testList.get(1) == 24).isTrue();
            assertThat(testList.get(2) == 12345).isTrue();
        }
    }

    @Test
    public void test_increaseCapacity() throws IOException {
        try(ArrayIntList testList = new ArrayIntList(2, 2)) {
            final int listSize = 100;
            final int mult = 10;

            //fill
            for(int i = 0; i < listSize; i++) {
                testList.add(i*mult);
            }
            //test
            assertThat(testList.getSize()).isEqualTo(listSize);
            for(int i = listSize-1; i >= 0; i--) {
                assertThat(testList.get(i)).isEqualTo(i*mult);
            }
        }
    }

    @Test
    public void test_removeElement() throws IOException {
        try(ArrayIntList testList = new ArrayIntList()) {
            final int listSize = 10;

            for(int i = 0; i < listSize; i++) {
                testList.add(i);
            }

            testList.remove(5); // 5
            testList.remove(7); // became = 8

            assertThat(testList.get(0)).isEqualTo(0);
            assertThat(testList.get(1)).isEqualTo(1);
            assertThat(testList.get(2)).isEqualTo(2);
            assertThat(testList.get(3)).isEqualTo(3);
            assertThat(testList.get(4)).isEqualTo(4);

            assertThat(testList.get(5)).isEqualTo(6);
            assertThat(testList.get(6)).isEqualTo(7);
            assertThat(testList.get(7)).isEqualTo(9);
        }
    }
}
