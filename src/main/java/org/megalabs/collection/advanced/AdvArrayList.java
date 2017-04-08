package org.megalabs.collection.advanced;

import java.io.Serializable;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.ObjDoubleConsumer;
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
        StringBuilder builder = new StringBuilder(arraySize*3);
        builder.append("[");
        for(int i = -1; ++i < arraySize;) {
            builder.append(array[i].toString());
            if(i+1 < arraySize) builder.append(", ");
        }
        builder.append("]");
        return builder.toString();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
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
    public boolean add(T element) {
        growArray(arraySize + 1);
        array[arraySize++] = element;
        return true;
    }

    private void growArray(int size) {
        if(size == capacity) {
            capacity *= scaleFactor;
            Object[] tmp = array;
            array = new Object[capacity];
            System.arraycopy(tmp, 0, array, 0, tmp.length);
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
        checkIndexBounds(index);
        return (T)array[index];
    }

    @Override
    public T set(int index, T element) {
        checkIndexBounds(index);
        Objects.requireNonNull(element);
        Object tmp = array[index];
        array[index] = element;
        return (T)tmp;
    }

    @Override
    public void add(int index, T element) {
        checkIndexBounds(index);
        if(index == arraySize) { add(element); return;}
        if(arraySize+1 == capacity) capacity *= scaleFactor;

        Object[] tmp = new Object[capacity];
        System.arraycopy(array, 0, tmp, 0, index);

        Objects.requireNonNull(element);
        tmp[index] = element;

        System.arraycopy(array, index, tmp, index+1, arraySize-index);
        this.array = tmp;
        arraySize++;
    }

    @Override
    public T remove(int index) {
        checkIndexBounds(index);
        Object tmp = array[index];

        Object[] newArray = new Object[capacity];
        if(index != 0) {
            System.arraycopy(array, 0, newArray, 0, index);
        }
        if(index != arraySize-1) {
            System.arraycopy(array, index+1, newArray, index, arraySize - index);
        }
        array = newArray;
        arraySize--;
        return (T)tmp;
    }

    @Override
    public int indexOf(Object o) {
        if(o == null) {
            for (int i = arraySize; --i >= 0;)
                if (null == array[i]) return i;
        } else {
            for (int i = arraySize; --i >= 0;)
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

    /*
        Private functions
     */
    private void checkIndexBounds(int index) {
        if(index > arraySize || index < 0) throw new NoSuchElementException("Index is wrong" + index);
    }

    /*
        Help functions
     */

    @Override
    public boolean contains(Predicate<T> predicate) {
        for(int i = arraySize; --i >= 0;) {
            if(predicate.test((T)array[i])) return true;
        }
        return false;
    }

    @Override
    public T last() {
        if(isEmpty()) throw new NoSuchElementException("List is empty");
        return (T)array[arraySize-1];
    }

    @Override
    public T first() {
        if(isEmpty()) throw new NoSuchElementException("List is empty");
        return (T)array[0];
    }

    @Override
    public void reverse() {
        Object[] newArray = new Object[capacity];
        int index = 0;
        for(int i = arraySize; --i >= 0;) {
            newArray[index++] = array[i];
        }
        array = newArray;
    }
}
