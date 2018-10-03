package com.worldpay.task.exception;

public class OfferExpiredException extends Exception {
    public OfferExpiredException() {
    }

    public OfferExpiredException(String message) {
        super(message);
    }

    public OfferExpiredException(String message, Throwable cause) {
        super(message, cause);
    }

    public OfferExpiredException(Throwable cause) {
        super(cause);
    }

    public OfferExpiredException(String message, Throwable cause, boolean enableSuppression,
        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
