package org.megalabs.collection.advanced;

import java.io.Serializable;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

/**
 * Created by ermolaev on 4/7/17.
 */
public class AdvArrayList<T> implements AdvList<T>, RandomAccess, Serializable {

    private static final int DEFAULT_CAPACITY = 10;
    private static final int DEFAULT_SCALE_FACTOR = 2;

    private transient Object[] array;
    private transient int arraySize;

    private int capacity;
    private int scaleFactor;

    public AdvArrayList() {
        this(DEFAULT_CAPACITY, DEFAULT_SCALE_FACTOR);
    }

    public AdvArrayList(final int capacity, final int scaleFactor) {
        init(capacity, scaleFactor);
    }

    private void init(final int capacity, final int scaleFactor) {
        this.array = new Object[capacity];
        this.arraySize = 0;
        this.capacity = capacity;
        this.scaleFactor = scaleFactor;
    }

    @Override
    public void replaceAll(UnaryOperator operator) {

    }

    @Override
    public void sort(Comparator c) {

    }

    @Override
    public Spliterator spliterator() {
        return null;
    }

    @Override
    public boolean removeIf(Predicate filter) {
        return false;
    }

    @Override
    public Stream stream() {
        return null;
    }

    @Override
    public Stream parallelStream() {
        return null;
    }

    @Override
    public void forEach(Consumer action) {
        for(int i = 0; i < arraySize; i++) {
            action.accept(array[i]);
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return Arrays.toString(array);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    @Override
    public AdvList reverse() {
        return null;
    }

    @Override
    public int size() {
        return arraySize;
    }

    @Override
    public boolean isEmpty() {
        return arraySize == 0;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }

    @Override
    public Iterator iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public Object[] toArray(Object[] a) {
        return new Object[0];
    }

    @Override
    public boolean add(Object element) {
        growArray(arraySize + 1);
        array[arraySize++] = element;
        return true;
    }

    private void growArray(int size) {
        if(size == capacity) {
            capacity *= scaleFactor;
            array = Arrays.copyOf(array, capacity);
        }
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection c) {
        return false;
    }

    @Override
    public boolean addAll(Collection c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection c) {
        return false;
    }

    @Override
    public void clear() {
        init(DEFAULT_CAPACITY, DEFAULT_SCALE_FACTOR);
    }

    @Override
    public T get(int index) {
        if(index >= arraySize || index < 0) throw new NoSuchElementException("with index " + index);
        return (T)array[index];
    }

    @Override
    public T set(int index, Object element) {
        return null;
    }

    @Override
    public void add(int index, Object element) {

    }

    @Override
    public T remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        if(o == null) {
            for (int i = 0; i < arraySize; i++)
                if (null == array[i]) return i;
        } else {
            for (int i = 0; i < arraySize; i++)
                if (o.equals(array[i])) return i;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator listIterator() {
        return null;
    }

    @Override
    public ListIterator listIterator(int index) {
        return null;
    }

    @Override
    public List subList(int fromIndex, int toIndex) {
        return null;
    }

    @Override
    public int capacity() {
        return this.capacity;
    }
}
