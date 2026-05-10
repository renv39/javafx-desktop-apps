
package com.rendellvelasco.projectstaffingtool.exceptions;

/**
 * Thrown by ResourceService when assigning an employee would push their
 * weekly total above 40 hours.
 */
public class OverAllocationException extends Exception {

    private final String message;

    public OverAllocationException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() { return message; }
}
