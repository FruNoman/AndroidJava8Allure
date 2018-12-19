package com.example.dfrolov.allureandroidjava8.core;

import android.app.Instrumentation;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.UiDevice;

public class BaseAdapterAllure {
    protected Instrumentation instrumentation;
    protected Context appContext;
    protected UiDevice mDevice ;

    public BaseAdapterAllure() {
        this.instrumentation  = InstrumentationRegistry.getInstrumentation();
        this.appContext = InstrumentationRegistry.getTargetContext();
        this.mDevice = UiDevice.getInstance(instrumentation);
    }
}
