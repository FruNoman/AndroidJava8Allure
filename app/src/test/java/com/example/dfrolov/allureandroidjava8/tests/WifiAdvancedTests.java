package com.example.dfrolov.allureandroidjava8.tests;

import com.android.ddmlib.AdbCommandRejectedException;
import com.android.ddmlib.IDevice;
import com.android.ddmlib.TimeoutException;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Link;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;


@Epic("Wifi core features")
@DisplayName("Wifi suite")
public class WifiAdvancedTests extends BaseTest {

    @Step("Test steps")
    @DisplayName("Wifi adapter reboot test")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Wifi adapter should stay ON after rebooting")
    @Link("example link")
    @Test
    public void wifi_1_RebootTest() throws AdbCommandRejectedException, IOException, TimeoutException, InterruptedException {
        adb.rebootDevice(device);
        Assert.assertEquals("Unexpected device state",adb.getDevice("0000").getState(),IDevice.DeviceState.ONLINE);
    }
}
