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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    public String time;
    public String getCurrentTimeStamp() {
        return new SimpleDateFormat("MM-dd HH:mm:ss.SSS").format(new Date());
    }

    public static String getLogs(String time){
        StringBuilder builder = new StringBuilder();

        try {
            String[] command = new String[] { "logcat", "-d","-t", time };

            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = bufferedReader.readLine()) != null) {

                builder.append(line+"\n");
                //Code here

            }
        } catch (Exception ex) {

        }
        return builder.toString();
    }

    @Rule
    public ActivityTestRule<MainActivity> activityActivityTestRle =
            new ActivityTestRule<MainActivity>(MainActivity.class);

    @Before
    public void beforeEachTests() throws IOException, InterruptedException {
        String[] command = new String[] { "logcat", "-c" };
        Runtime.getRuntime().exec(command).waitFor();
        time = getCurrentTimeStamp();
    }

    @After
    public void afterEachTests() throws IOException {
        mDevice.pressBack();
        String logcat = getLogs(time);
        Allure.addAttachment("logcat",logcat);
    }
}
