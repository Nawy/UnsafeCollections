package org.megalabs.collection.advanced;

import java.util.List;
import java.util.function.Predicate;

/**
 * Created by ermolaev on 4/7/17.
 */
public interface AdvList<T> extends List<T> {

    AdvList reverse();
    int capacity();
    boolean contains(Predicate<T> predicate);
    T last();
    T first();
}
