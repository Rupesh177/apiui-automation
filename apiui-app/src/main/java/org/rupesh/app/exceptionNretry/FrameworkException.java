package org.rupesh.app.exceptionNretry;

public class FrameworkException extends RuntimeException {
    public FrameworkException(String message) {
        super(message);
    }
}