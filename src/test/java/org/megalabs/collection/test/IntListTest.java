package org.megalabs.collection.test;

import org.junit.Test;
import org.megalabs.collection.unsafe.IntList;

import java.io.IOException;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by ermolaev on 4/1/17.
 */
public class IntListTest {

    @Test
    public void test_addingAndGettingElements() throws IOException {
        try(IntList testList = new IntList()) {
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
        try(IntList testList = new IntList(2, 2)) {
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
    public void test_removeMiddle() throws IOException {
        try(IntList testList = new IntList()) {
            testList.add(10);
            testList.add(20);
            testList.add(30);
            testList.add(40);
            testList.add(50);

            testList.remove(2);

            assertThat(testList.get(0)).isEqualTo(10);
            assertThat(testList.get(1)).isEqualTo(20);
            assertThat(testList.get(2)).isEqualTo(40);
            assertThat(testList.get(3)).isEqualTo(50);
        }
    }

    @Test
    public void test_removeFirst() throws IOException {
        try(IntList testList = new IntList()) {
            testList.add(10);
            testList.add(20);
            testList.add(30);
            testList.add(40);
            testList.add(50);

            testList.remove(0);

            assertThat(testList.get(0)).isEqualTo(20);
            assertThat(testList.get(1)).isEqualTo(30);
            assertThat(testList.get(2)).isEqualTo(40);
            assertThat(testList.get(3)).isEqualTo(50);
        }
    }

    @Test
    public void test_removeLast() throws IOException {
        try(IntList testList = new IntList()) {
            testList.add(10);
            testList.add(20);
            testList.add(30);
            testList.add(40);
            testList.add(50);

            testList.remove(testList.getSize()-1);

            assertThat(testList.get(0)).isEqualTo(10);
            assertThat(testList.get(1)).isEqualTo(20);
            assertThat(testList.get(2)).isEqualTo(30);
            assertThat(testList.get(3)).isEqualTo(40);
        }
    }

    @Test
    public void test_insertNormal() throws IOException {
        try (IntList testList = new IntList()) {
            testList.add(20);
            testList.add(30);
            testList.add(40);
            testList.add(50);

            testList.insert(2, 34);

            assertThat(testList.get(0)).isEqualTo(20);
            assertThat(testList.get(1)).isEqualTo(30);
            assertThat(testList.get(2)).isEqualTo(34);
            assertThat(testList.get(3)).isEqualTo(40);
            assertThat(testList.get(4)).isEqualTo(50);
        }
    }

    @Test
    public void test_insertZero() throws IOException {
        try (IntList testList = new IntList()) {
            testList.add(20);
            testList.add(30);
            testList.add(40);
            testList.add(50);

            testList.insert(0, 12345);

            assertThat(testList.get(0)).isEqualTo(12345);
            assertThat(testList.get(1)).isEqualTo(20);
            assertThat(testList.get(2)).isEqualTo(30);
            assertThat(testList.get(3)).isEqualTo(40);
            assertThat(testList.get(4)).isEqualTo(50);
        }
    }

    @Test
    public void test_insertLast() throws IOException {
        try (IntList testList = new IntList()) {
            testList.add(20);
            testList.add(30);
            testList.add(40);

            testList.insert(testList.getSize()-1, 12345);
            System.out.println(testList);
            assertThat(testList.get(0)).isEqualTo(20);
            assertThat(testList.get(1)).isEqualTo(30);
            assertThat(testList.get(2)).isEqualTo(12345);
            assertThat(testList.get(3)).isEqualTo(40);
        }
    }
}
