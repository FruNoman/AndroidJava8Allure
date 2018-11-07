package com.example.dfrolov.allureandroidjava8.allure_implementation.allure;

/**
 * @author charlie (Dmitry Baev).
 */
public enum SeverityLevel {

    BLOCKER("blocker"),
    CRITICAL("critical"),
    NORMAL("normal"),
    MINOR("minor"),
    TRIVIAL("trivial");

    private final String value;

    SeverityLevel(final String v) {
        value = v;
    }

    public String value() {
        return value;
    }
}
