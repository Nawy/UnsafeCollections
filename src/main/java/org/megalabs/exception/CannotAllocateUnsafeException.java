package org.megalabs.exception;

/**
 * Created by ermolaev on 4/1/17.
 */
public class CannotAllocateUnsafeException extends RuntimeException {

    public CannotAllocateUnsafeException(String message, Throwable cause) {
        super(message, cause);
    }
}
