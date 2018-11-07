package com.example.dfrolov.allureandroidjava8;



import com.android.ddmlib.AdbCommandRejectedException;
import com.android.ddmlib.InstallException;
import com.android.ddmlib.ShellCommandUnresponsiveException;


import com.example.dfrolov.allureandroidjava8.allure_implementation.RenesasRunner;
import com.example.dfrolov.allureandroidjava8.allure_implementation.allure.Allure;
import com.example.dfrolov.allureandroidjava8.allure_implementation.allure.Description;
import com.example.dfrolov.allureandroidjava8.allure_implementation.allure.Epic;
import com.example.dfrolov.allureandroidjava8.allure_implementation.allure.Severity;
import com.example.dfrolov.allureandroidjava8.allure_implementation.allure.SeverityLevel;
import com.example.dfrolov.allureandroidjava8.allure_implementation.junit4.DisplayName;
import com.example.dfrolov.allureandroidjava8.helper.BluetoothAdapter;
import com.example.dfrolov.allureandroidjava8.helper.Descriptions;
import com.example.dfrolov.allureandroidjava8.helper.Shell;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.util.concurrent.TimeoutException;



@Epic("Bluetooth advanced features")
@DisplayName("Bluetooth suite")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(RenesasRunner.class)
public class BluetoothAdvancedTests extends BaseTest {

    @Before
    public void beforeTest() throws InterruptedException, TimeoutException, AdbCommandRejectedException, ShellCommandUnresponsiveException, IOException, InstallException, com.android.ddmlib.TimeoutException {
        PLATFORM_SERIAL = System.getenv("ANDROID_SERIAL");
        device = getDeviceBySerial(PLATFORM_SERIAL);
        Allure.addAttachment("sda","sdasd");
        bleAdapter = BluetoothAdapter.getDefaultAdapter(device);
        device.installPackage(APK_PATH,true,"-g");
        startTestApk();
        device.executeShellCommand(START_BLUETOOTH_SETTINGS, new Shell());
        device.executeShellCommand("logcat -c",new Shell());
    }

    @DisplayName("Bluetooth adapter check state after reboot")
    @Severity(SeverityLevel.NORMAL)
    @Description(Descriptions.WIFI)
    @Test
    public void bluetoothAdvanced_1_saveStateAfterRebootTest() throws AdbCommandRejectedException, IOException, InterruptedException, ShellCommandUnresponsiveException, InstallException, com.android.ddmlib.TimeoutException {
        bleAdapter.enable();
        bleAdapter.waitForState(BluetoothAdapter.STATE_ON);
        device.executeShellCommand("reboot", new Shell());
        device = waitForDeviceReboot(REBOOT_MAX_TIMEOUT);
        Allure.addAttachment("sda","sdasd");
        Thread.sleep(7000);
        startTestApk();
        device.executeShellCommand(START_BLUETOOTH_SETTINGS, new Shell());
        bleAdapter.waitForState(BluetoothAdapter.STATE_ON);
        bleAdapter.disable();
        Allure.addAttachment("sda","sdasd");
        bleAdapter.waitForState(BluetoothAdapter.STATE_OFF);
    }


}
