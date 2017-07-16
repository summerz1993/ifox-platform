package com.ifox.platform.common.exception;

public class PersistenceException extends RuntimeException {

	private static final long serialVersionUID = 1L;

    public PersistenceException() {
    }

    public PersistenceException(String message) {
        super(message);
    }

    public PersistenceException(String message, Throwable cause) {
        super(message, cause);
    }

    public PersistenceException(Throwable cause) {
        super(cause);
    }

}
