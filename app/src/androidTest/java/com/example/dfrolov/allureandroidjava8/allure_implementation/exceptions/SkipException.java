package com.example.dfrolov.allureandroidjava8.allure_implementation.exceptions;

public class SkipException extends Exception{

    public SkipException() {
    }

    public SkipException(String message) {
        super(message);
    }

    public SkipException(String message, Throwable cause) {
        super(message, cause);
    }

    public SkipException(Throwable cause) {
        super(cause);
    }

    public SkipException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
