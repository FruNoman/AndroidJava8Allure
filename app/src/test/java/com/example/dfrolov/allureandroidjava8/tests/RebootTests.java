package com.example.dfrolov.allureandroidjava8.tests;

import com.android.ddmlib.AdbCommandRejectedException;
import com.android.ddmlib.IDevice;
import com.android.ddmlib.TimeoutException;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;

@DisplayName("Reboot tests")
public class RebootTests extends BaseTest {

    @Step("Test steps")
    @DisplayName("Reboot test")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Check reboot time less then 20 seconds")
    @Link("example link")
    @Test
    public void reboot_1_Test() throws AdbCommandRejectedException, IOException, TimeoutException, InterruptedException {
        long rebootTime = adb.rebootDevice(device);
        Assert.assertEquals("Unexpected device state",adb.getDevice("0000").getState(),IDevice.DeviceState.ONLINE);
        Assert.assertTrue("Unexpected reboot time "+rebootTime, rebootTime < 19000);
    }

    @Step("Test steps")
    @DisplayName("Many reboot test")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Check many reboot time less then 20 seconds")
    @Link("example link")
    @Test
    public void reboot_2_ManyRebootTest() throws AdbCommandRejectedException, IOException, TimeoutException, InterruptedException {
        int count = 0;
        while (count<10) {
            long rebootTime = adb.rebootDevice(device);
            Assert.assertEquals("Unexpected device state",adb.getDevice("0000").getState(),IDevice.DeviceState.ONLINE);
            Assert.assertTrue("Unexpected reboot time " + rebootTime, rebootTime < 20000);
            count++;
        }
    }
}
