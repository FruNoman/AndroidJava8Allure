package com.example.dfrolov.allureandroidjava8.tests;

import android.app.Instrumentation;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.uiautomator.UiDevice;

import com.example.dfrolov.allureandroidjava8.MainActivity;
import com.example.dfrolov.allureandroidjava8.allure_implementation.allure.Allure;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;

import java.io.IOException;

public class BaseTest {
    protected final int REPEAT_TIMES = 30;
    protected Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
    public static Context appContext = InstrumentationRegistry.getTargetContext();
    protected PackageManager packageManager = appContext.getPackageManager();
    protected UiDevice mDevice = UiDevice.getInstance(instrumentation);
    protected int TIMEOUT = 3000;
    protected final static int LOW_QUALITY = 20;
    protected final static int MEDIUM_QUALITY = 50;
    protected final static int FULL_QUALITY = 100;
    protected final static int SHORT_TIME_WAIT = 30000;
    protected final static int LONG_TIME_WAIT = 30000;


    @Rule
    public ActivityTestRule<MainActivity> activityActivityTestRle =
            new ActivityTestRule<MainActivity>(MainActivity.class);

    @Before
    public void beforeEachTests() throws IOException {
        mDevice.executeShellCommand("logcat -c");
    }

    @After
    public void afterEachTests() throws IOException {
        mDevice.pressBack();
        String logcat = mDevice.executeShellCommand("logcat -d");
        Allure.addAttachment("logcat",logcat);
    }
}
