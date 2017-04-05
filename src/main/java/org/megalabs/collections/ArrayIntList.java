package org.megalabs.collections;

import sun.misc.Unsafe;

import java.io.Closeable;
import java.io.IOException;
import java.util.NoSuchElementException;

/**
 * Created by ermolaev on 4/1/17.
 */
//TODO Should implement List
public class ArrayIntList extends UnsafeAllocator implements Closeable {

    /**
     * Size of [int] in bytes for JVM
     */
    private static final long INT_SIZE_IN_BYTES = 4L;

    /**
     * Default allocated memory
     */
    private static final long DEFAULT_CAPACITY = 10L;

    /**
     * Default multiplicator. When capacity became lower than size,
     * capacity is increases by capacity*multiplicator
     */
    private static final long DEFAULT_MULTIPLICATOR = 2L;

    private long startPointer;
    private long size;
    private long capacity;
    private long multiplicator;

    private Unsafe unsafe;

    public ArrayIntList() {
        this(DEFAULT_CAPACITY, DEFAULT_MULTIPLICATOR);
    }

    public ArrayIntList(final long capacity, final long multiplicator) {
        this.size = 0;
        this.capacity = capacity;
        this.multiplicator = multiplicator;
        this.unsafe = getUnsafe();
        this.startPointer = unsafe.allocateMemory(capacity * INT_SIZE_IN_BYTES);
    }

    /**
     * Added new elements to list
     *
     * @param elem
     */
    public void add(int elem) {
        if (size + 1 > capacity) {
            this.capacity = capacity * multiplicator;
            final long newStartPointer = unsafe.allocateMemory(capacity * INT_SIZE_IN_BYTES);
            unsafe.copyMemory(startPointer, newStartPointer, size * INT_SIZE_IN_BYTES);
            unsafe.freeMemory(startPointer);
            this.startPointer = newStartPointer;
        }

        unsafe.putInt(calcIndex(size), elem);
        size++;
    }

    /**
     * Get element from array by index
     *
     * @param index
     */
    public long get(long index) {
        if (index > size || index < 0) throw new NoSuchElementException("Array don't have element with index " + index);
        return unsafe.getInt(calcIndex(index));
    }

    /**
     * Remove element from array by index
     *
     * @param index
     */
    public void remove(long index) {
        if (index > size || index < 0) throw new NoSuchElementException("Array don't have element with index " + index);
        final long newStartPointer = unsafe.allocateMemory(capacity * INT_SIZE_IN_BYTES);
        unsafe.copyMemory(startPointer, newStartPointer, index * INT_SIZE_IN_BYTES);
        unsafe.copyMemory(calcIndex(index + 1), newStartPointer + calcOffset(index), (size - index - 1) * INT_SIZE_IN_BYTES);
        this.startPointer = newStartPointer;
        size--;
    }

    public long getSize() {
        return size;
    }

    public long getCapacity() {
        return capacity;
    }

    private long calcIndex(long offset) {
        return startPointer + offset * INT_SIZE_IN_BYTES;
    }

    private long calcOffset(long index) {
        return index * INT_SIZE_IN_BYTES;
    }

    public void close() {
        unsafe.freeMemory(startPointer);
    }
}
