package org.megalabs.collections;

import org.megalabs.exception.CannotAllocateUnsafeException;
    import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Created by ermolaev on 4/1/17.
 */
public abstract class UnsafeAllocator {

    /**
     * This method return instance of Unsafe class. It's protected for you. You have to don't use
     * it outside of this class, because that might lead to memory leaks.
     * @throws CannotAllocateUnsafeException if unsafe doesn't have field 'theUnsafe'
     * @return {@link sun.misc.Unsafe}
     */
    protected static Unsafe getUnsafe() {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            return (Unsafe) theUnsafe.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new CannotAllocateUnsafeException("Cannot allocate Unsafe : ", e);
        }
    }
}
