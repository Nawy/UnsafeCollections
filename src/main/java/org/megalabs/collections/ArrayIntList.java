package org.megalabs.collections;

import sun.misc.Unsafe;

import java.io.Closeable;
import java.io.IOException;
import java.util.NoSuchElementException;

/**
 * Created by ermolaev on 4/1/17.
 */
public class ArrayIntList extends UnsafeAllocator implements Closeable {

    /**
     * Size of [int] in bytes for JVM
     */
    private static final int INT_SIZE_IN_BYTES = 4;

    /**
     * Default allocated memory
     */
    private static final long DEFAULT_CAPACITY = 10;

    /**
     * Default multiplicator. When capacity became lower than size,
     * capacity is increases by capacity*multiplicator
     */
    private static final short DEFAULT_MULTIPLICATOR = 2;

    private long startPointer;
    private long size;
    private long capacity;
    private short multiplicator;

    private Unsafe unsafe;

    public ArrayIntList() {
        this(DEFAULT_CAPACITY, DEFAULT_MULTIPLICATOR);
    }

    public ArrayIntList(final long capacity, final short multiplicator) {
        this.size = 0;
        this.capacity = capacity;
        this.multiplicator = multiplicator;
        this.unsafe = getUnsafe();
        this.startPointer = unsafe.allocateMemory(capacity * INT_SIZE_IN_BYTES);
    }

    public void add(int value) {
        if(size > capacity) {
            this.capacity = capacity * multiplicator;
            final long newStartPointer = unsafe.allocateMemory(capacity * INT_SIZE_IN_BYTES);
            unsafe.copyMemory(startPointer, newStartPointer, size*INT_SIZE_IN_BYTES);
            this.startPointer = newStartPointer;
        }
        unsafe.putInt(calcIndex(size), value);
        size++;
    }

    public long get(long index) {
        if(index > size || index < 0) throw new NoSuchElementException("Array don't have element with index " + index);
        return unsafe.getInt(calcIndex(index));
    }

    public void remove(long index) {
        if(index > size || index < 0) throw new NoSuchElementException("Array don't have element with index " + index);
        final long newStartPointer = unsafe.allocateMemory(capacity * INT_SIZE_IN_BYTES);
        unsafe.copyMemory(startPointer, newStartPointer, index*INT_SIZE_IN_BYTES);
        unsafe.copyMemory(calcIndex(index+1), newStartPointer+calcOffset(index), (size-index-1)*INT_SIZE_IN_BYTES);
        this.startPointer = newStartPointer;
        size--;
    }

    public long getSize() {
        return size;
    }

    private long calcIndex(long offset) {
        return startPointer + offset*INT_SIZE_IN_BYTES;
    }
    private long calcOffset(long index) { return index*INT_SIZE_IN_BYTES; }

    public void close() throws IOException {
        unsafe.freeMemory(startPointer);
    }
}
