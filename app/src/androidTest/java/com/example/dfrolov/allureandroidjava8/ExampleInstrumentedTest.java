package com.example.dfrolov.allureandroidjava8;

import android.Manifest;
import android.content.Context;
import android.support.test.rule.GrantPermissionRule;


import com.example.dfrolov.allureandroidjava8.allure_implementation.RenesasRunner;
import com.example.dfrolov.allureandroidjava8.allure_implementation.allure.Allure;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(RenesasRunner.class)
public class ExampleInstrumentedTest {

    @Rule
    public GrantPermissionRule permissionsRules =
            GrantPermissionRule.grant(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);

    @Test
    public void useAppContext() {
        // Context of the app under test.

        Some some = new Some();
        some.doSome();
        some.doSome();
        some.doSome();

    }
}
