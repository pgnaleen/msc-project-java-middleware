package com.ubs.sis.admin.form.exception;

public class FormsException extends RuntimeException {
    public FormsException() {
    }

    public FormsException(String message) {
        super(message);
    }

    public FormsException(String message, Throwable cause) {
        super(message, cause);
    }

    public FormsException(Throwable cause) {
        super(cause);
    }

    public FormsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
