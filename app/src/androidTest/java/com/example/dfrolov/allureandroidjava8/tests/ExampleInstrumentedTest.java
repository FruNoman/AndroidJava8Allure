package com.example.dfrolov.allureandroidjava8.tests;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.support.test.rule.GrantPermissionRule;


import com.example.dfrolov.allureandroidjava8.Some;
import com.example.dfrolov.allureandroidjava8.allure_implementation.RenesasRunner;
import com.example.dfrolov.allureandroidjava8.allure_implementation.allure.Severity;
import com.example.dfrolov.allureandroidjava8.allure_implementation.allure.SeverityLevel;
import com.example.dfrolov.allureandroidjava8.allure_implementation.allure.Step;
import com.example.dfrolov.allureandroidjava8.allure_implementation.junit4.DisplayName;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(RenesasRunner.class)
public class ExampleInstrumentedTest {
    private Some some;
    @Rule
    public GrantPermissionRule permissionsRules =
            GrantPermissionRule.grant(
                    Manifest.permission.BLUETOOTH_ADMIN,
                    Manifest.permission.BLUETOOTH,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);


    @Before
    public void beforeSome(){
        some = new Some();
        some.doSome();
    }

    @DisplayName("Aspectj test")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void useAppContext() {
        // Context of the app under test.
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        adapter.enable();

        some.doSome();
        some.doSome();
        some.doSome();
    }

    @DisplayName("Aspectj test2")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void useAppContext1() {
        // Context of the app under test.
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        adapter.enable();

        some.doSome();
        some.doSome();
        some.doSome();
    }

    @After
    public void afterSome(){
        some.doSome();
    }
}
