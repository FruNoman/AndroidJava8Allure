package com.example.dfrolov.allureandroidjava8;

import com.android.ddmlib.AdbCommandRejectedException;
import com.android.ddmlib.AndroidDebugBridge;
import com.android.ddmlib.IDevice;
import com.android.ddmlib.ShellCommandUnresponsiveException;
import com.android.ddmlib.TimeoutException;
import com.example.dfrolov.allureandroidjava8.allure_implementation.allure.Step;
import com.example.dfrolov.allureandroidjava8.helper.BluetoothAdapter;
import com.example.dfrolov.allureandroidjava8.helper.Shell;
import com.example.dfrolov.allureandroidjava8.helper.WifiAdapter;


import org.junit.Assert;
import org.junit.BeforeClass;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;




public class BaseTest {

    protected final int REBOOT_MAX_TIMEOUT = 30000;
    protected static AndroidDebugBridge adb;
    protected IDevice device;
    protected IDevice supplyDevice;
    protected static String PLATFORM_SERIAL = System.getenv("ANDROID_SERIAL");
    protected final String PROJECT_PACKAGE = (System.getProperty("user.dir"));
    protected final String APK_PATH = PROJECT_PACKAGE + "/build/outputs/apk/debug/app-debug.apk";
    protected final static String START_WIFI_SETTINGS = "am start -a android.settings.WIFI_SETTINGS";
    protected final static String START_BLUETOOTH_SETTINGS = "am start -a android.settings.BLUETOOTH_SETTINGS";
    protected static String START_TEST_APK = "am start -n com.example.dfrolov.allureandroidjava8/.MainActivity";
    protected final String NETWORK_SSID = "RNS_AES+TKIP";
    protected final String NETWORK_SSID_AES = "RNS_AES";
    protected final String NETWORK_PASS = "12345678";
    protected final String OPEN_NETWORK_SSID = "RNS_Open";
    protected WifiAdapter adapter;
    protected BluetoothAdapter bleAdapter;
    protected BluetoothAdapter supplyBleAdapter;
    protected int networkId;

    public IDevice getDeviceBySerial(String serial) {
        IDevice foundDevice = null;
        List<IDevice> devices = Arrays.asList(adb.getDevices());
        if (devices != null) {
            for (IDevice device : devices) {
                if (device.getSerialNumber().contains(serial)) {
                    foundDevice = device;
                    break;
                }
            }
        }
        return foundDevice;
    }

    public IDevice getSupplyDevice() {
        IDevice supplyDevice = null;
        List<IDevice> devices = Arrays.asList(adb.getDevices());
        for (IDevice device : devices) {
            if (device.getProperty("ro.product.board").equals("kingfisher") || device.getProperty("ro.product.board").equals("salvator-x")) {
                continue;
            } else {
                supplyDevice = device;
            }
        }
        return supplyDevice;
    }

    public IDevice waitForDeviceReboot(long maxTime) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        IDevice deviceScan = null;
        while (deviceScan == null) {
            if ((System.currentTimeMillis() - startTime) > maxTime) {
                Assert.assertFalse("Wait for device reboot more than " + (System.currentTimeMillis() - startTime) + " milseconds", true);
            }
            deviceScan = getDeviceBySerial(PLATFORM_SERIAL);
            Thread.sleep(1000);
        }
        long rebootTime = System.currentTimeMillis() - startTime;
        return deviceScan;
    }


    @Step
    public void startTestApk() throws TimeoutException, AdbCommandRejectedException, ShellCommandUnresponsiveException, IOException, InterruptedException {
        device.executeShellCommand(START_TEST_APK, new Shell());
        Thread.sleep(4000);
    }

    @BeforeClass
    public static void beforeClass() throws InterruptedException {
        AndroidDebugBridge.initIfNeeded(true);
        adb = AndroidDebugBridge.createBridge("adb", true);
        while (!adb.isConnected()) {
            Thread.sleep(1000);
        }

    }

}
