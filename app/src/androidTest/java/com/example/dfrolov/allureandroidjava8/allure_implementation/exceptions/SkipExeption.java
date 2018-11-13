package com.example.dfrolov.allureandroidjava8.allure_implementation.exceptions;

public class SkipExeption extends Exception{

    public SkipExeption() {
    }

    public SkipExeption(String message) {
        super(message);
    }

    public SkipExeption(String message, Throwable cause) {
        super(message, cause);
    }

    public SkipExeption(Throwable cause) {
        super(cause);
    }

    public SkipExeption(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
