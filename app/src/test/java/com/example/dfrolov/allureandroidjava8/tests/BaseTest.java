package com.example.dfrolov.allureandroidjava8.tests;

import com.android.ddmlib.IDevice;
import com.example.dfrolov.allureandroidjava8.utils.AdbWrapper;


import org.junit.After;
import org.junit.Before;

import io.qameta.allure.Step;




public class BaseTest {
    protected AdbWrapper adb;
    protected IDevice device;


    @Step("Before tests")
    @Before
    public void beforeTests() throws InterruptedException {
        adb = new AdbWrapper();
        device = adb.getDevice("0000");
    }


    @Step("After tests")
    @After
    public void afterTests(){
          adb.disconnect();
    }
}
