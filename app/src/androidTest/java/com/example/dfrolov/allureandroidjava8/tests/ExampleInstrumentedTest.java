package com.example.dfrolov.allureandroidjava8.tests;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.net.wifi.WifiManager;
import android.support.test.rule.GrantPermissionRule;


import com.example.dfrolov.allureandroidjava8.allure_implementation.RenesasRunner;
import com.example.dfrolov.allureandroidjava8.allure_implementation.allure.Epic;
import com.example.dfrolov.allureandroidjava8.allure_implementation.allure.Feature;
import com.example.dfrolov.allureandroidjava8.allure_implementation.allure.Link;
import com.example.dfrolov.allureandroidjava8.allure_implementation.allure.Severity;
import com.example.dfrolov.allureandroidjava8.allure_implementation.allure.SeverityLevel;
import com.example.dfrolov.allureandroidjava8.allure_implementation.allure.Story;
import com.example.dfrolov.allureandroidjava8.allure_implementation.junit4.DisplayName;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@Epic("Allure examples")
@Feature("Junit 4 support")
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
    @Link("https://example.org")
    @Story("Advanced support for bdd annotations")
    @Test
    public void useAppContext() {
        // Context of the app under test.
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        adapter.enable();

        some.doSome();
        Some.takeScrenshot();
        Assert.assertTrue(true);
        some.enable("FUCK !!!");
        some.doSome();
    }

    @Story("Advanced support for bdd annotations")
    @DisplayName("Aspectj test2")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void useAppContext1() {
        // Context of the app under test.
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        adapter.enable();
        some.doSome();
        adapter.setName("PApa");
        Assert.assertEquals(adapter.getName(),"papapa");


        some.enable("FUCK !!!");
        some.doSome();
    }

    @After
    public void afterSome(){
        some.doSome();
    }
}
