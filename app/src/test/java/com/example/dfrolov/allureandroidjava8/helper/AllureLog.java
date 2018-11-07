package com.example.dfrolov.allureandroidjava8.helper;


import com.example.dfrolov.allureandroidjava8.allure_implementation.allure.Allure;

public class AllureLog {
    private static final String TAG = "TEST";

    public static void info(String log,Object value) {
        Allure.addAttachment(log, String.valueOf(value));
    }

}
