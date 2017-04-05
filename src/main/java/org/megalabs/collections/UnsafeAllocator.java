package org.megalabs.collections;

import org.megalabs.exception.CannotAllocateUnsafeException;
import sun.misc.Unsafe;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.Field;

/**
 * Created by ermolaev on 4/1/17.
 */
public abstract class UnsafeAllocator implements Closeable {

    protected Unsafe unsafe;

    private long startPointer;

    protected UnsafeAllocator() {
        this.unsafe = getUnsafe();
    }

    /**
     * This method return instance of Unsafe class. It's protected for you. You have to don't use
     * it outside of this class, because that might lead to memory leaks.
     * @throws CannotAllocateUnsafeException if unsafe doesn't have field 'theUnsafe'
     * @return {@link sun.misc.Unsafe}
     */
    private static Unsafe getUnsafe() {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            return (Unsafe) theUnsafe.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new CannotAllocateUnsafeException("Cannot allocate Unsafe : ", e);
        }
    }

    protected void setStartPointer(final long startPointer) {
        if(this.startPointer != 0L) unsafe.freeMemory(this.startPointer);
        this.startPointer = startPointer;
    }

    protected long getStartPointer() {
        return startPointer;
    }

    @Override
    public void close() throws IOException {
        unsafe.freeMemory(getStartPointer());
    }
}
