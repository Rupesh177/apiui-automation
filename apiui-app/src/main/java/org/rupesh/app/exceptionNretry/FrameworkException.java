package org.rupesh.app.exceptionNretry;

public class FrameworkException extends RuntimeException {

    // -------------------------------
    // BASIC MESSAGE
    // -------------------------------
    public FrameworkException(String message) {
        super(message);
    }

    // -------------------------------
    // MESSAGE + CAUSE (IMPORTANT)
    // -------------------------------
    public FrameworkException(String message, Throwable cause) {
        super(message, cause);
    }

    // -------------------------------
    // ONLY CAUSE (OPTIONAL)
    // -------------------------------
    public FrameworkException(Throwable cause) {
        super(cause);
    }
}