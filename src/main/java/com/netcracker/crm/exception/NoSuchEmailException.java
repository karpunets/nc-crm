package com.netcracker.crm.exception;

/**
 * Created by Pasha on 26.04.2017.
 */
public class NoSuchEmailException extends Exception {

    public NoSuchEmailException() {
    }

    public NoSuchEmailException(String message) {
        super(message);
    }

    public NoSuchEmailException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchEmailException(Throwable cause) {
        super(cause);
    }

    public NoSuchEmailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
