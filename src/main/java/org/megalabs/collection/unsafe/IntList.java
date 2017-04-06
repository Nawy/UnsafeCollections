package org.megalabs.collection.unsafe;

import java.util.NoSuchElementException;

/**
 * Created by ermolaev on 4/1/17.
 */
public class IntList extends UnsafeAllocator {

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

    private long size;
    private long capacity;
    private long multiplicator;

    public IntList() {
        this(DEFAULT_CAPACITY, DEFAULT_MULTIPLICATOR);
    }

    public IntList(final long capacity, final long multiplicator) {
        this.size = 0;
        this.capacity = capacity;
        this.multiplicator = multiplicator;
        setStartPointer(unsafe.allocateMemory(capacity * INT_SIZE_IN_BYTES));
    }

    /**
     * Added new elements to list
     * @param element
     */
    public void add(int element) {
        if(size+1 > capacity) {
            this.capacity = capacity * multiplicator;
            this.startPointer = unsafe.reallocateMemory(this.startPointer, capacity * INT_SIZE_IN_BYTES);
        }
        unsafe.putInt(calcIndex(size, getStartPointer()), element);
        size++;
    }

    /**
     * Get element from array by index
     * @param index
     */
    public long get(long index) {
        if(index >= size || index < 0) throw new NoSuchElementException("Array don't have element with index " + index);
        return unsafe.getInt(calcIndex(index, getStartPointer()));
    }

    /**
     * Remove element from array by index
     * @param index
     */
    public void remove(long index) {
        if(index >= size || index < 0) throw new NoSuchElementException("Array don't have element with index " + index);
        final long newStartPointer = unsafe.allocateMemory(capacity * INT_SIZE_IN_BYTES);
        unsafe.copyMemory(
                getStartPointer(),
                newStartPointer,
                index*INT_SIZE_IN_BYTES
        );
        unsafe.copyMemory(
                calcIndex(index+1, getStartPointer()),
                newStartPointer+calcOffset(index),
                calcOffset(size-index > 0 ? (size-1)-index : 1)
        );
        setStartPointer(newStartPointer);
        size--;
    }

    /**
     * Insert element in array by index
     * @param index
     * @param element
     */
    public void insert(final long index, final int element) {
        if(index > size || index < 0) throw new NoSuchElementException("Array don't have element with index " + index);
        if(index == size) {
            this.add(element);
            return;
        }
        if(size == capacity) {
            this.capacity = capacity * multiplicator;
        }
        final long newStartPointer = unsafe.allocateMemory(capacity * INT_SIZE_IN_BYTES);
        if(index != 0) {
            unsafe.copyMemory(
                    getStartPointer(),
                    newStartPointer,
                    calcOffset(index+1)
            );
        }
        unsafe.putInt(
                calcIndex(index, newStartPointer),
                element
        );
        unsafe.copyMemory(
                calcIndex(index, getStartPointer()),
                calcIndex(index + 1, newStartPointer),
                calcOffset(size - index)
        );
        setStartPointer(newStartPointer);
        size++;
    }

    public void normalize() {
        if(capacity == size) return;

        this.capacity = size;
        final long newStartPointer = unsafe.allocateMemory(capacity * INT_SIZE_IN_BYTES);
        unsafe.copyMemory(
                getStartPointer(),
                newStartPointer,
                calcOffset(size)
        );
        setStartPointer(newStartPointer);
    }

    public long getSize() {
        return size;
    }

    public long getCapacity() {
        return capacity;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder((int)size*2);
        builder.append("[");
        for (int i = 0; i < size; i++) {
            builder.append(this.get(i));
            if(i+1 < size) builder.append(",");
        }
        builder.append("]");
        return builder.toString();
    }

    private long calcIndex(long index, long pointer) {
        return pointer + calcOffset(index);
    }
    private long calcOffset(long index) { return index * INT_SIZE_IN_BYTES; }
}
