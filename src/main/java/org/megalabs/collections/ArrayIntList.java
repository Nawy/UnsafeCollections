package org.megalabs.collections;

import sun.misc.Unsafe;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by ermolaev on 4/1/17.
 */
public class ArrayIntList extends UnsafeAllocator implements Closeable {
    private static final long INT_SIZE_IN_BYTES = 4;
    private static final long DEFAULT_CAPACITY = 10;

    private long startPointer;
    private long size;

    private Unsafe unsafe;

    public ArrayIntList() {
        this(DEFAULT_CAPACITY);
    }

    public ArrayIntList(final long capacity) {
        this.size = 0;
        this.unsafe = getUnsafe();
        this.startPointer = unsafe.allocateMemory(capacity * INT_SIZE_IN_BYTES);
    }

    public void add(int value) {
        unsafe.putInt(index(size), value);
        size++;
    }

    public long get(long index) {
        return unsafe.getInt(index(index));
    }

    private long index(long offset) {
        return startPointer + offset*INT_SIZE_IN_BYTES;
    }

    public void close() throws IOException {
        unsafe.freeMemory(startPointer);
    }
}
