package com.example.dfrolov.allureandroidjava8.tests;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;
import android.support.test.rule.GrantPermissionRule;


import com.example.dfrolov.allureandroidjava8.allure_implementation.RenesasRunner;
import com.example.dfrolov.allureandroidjava8.allure_implementation.allure.Epic;
import com.example.dfrolov.allureandroidjava8.allure_implementation.allure.Severity;
import com.example.dfrolov.allureandroidjava8.allure_implementation.allure.SeverityLevel;
import com.example.dfrolov.allureandroidjava8.allure_implementation.allure.Step;
import com.example.dfrolov.allureandroidjava8.allure_implementation.junit4.DisplayName;
import com.example.dfrolov.allureandroidjava8.receivers.BluetoothReceiver;
import com.example.dfrolov.allureandroidjava8.utils.TestUtils;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.Calendar;
import java.util.List;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@Epic("Bluetooth core features")
@DisplayName("Bluetooth suite")
@RunWith(RenesasRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BluetoothCoreSuite extends BaseTest {
    public BluetoothAdapter adapter;
    private BluetoothReceiver receiver;

    @Step
    public void bluetoothForceEnable() {
        boolean success = true;
        if (!adapter.isEnabled()) {
            success = adapter.enable();
        }
    }

    @Step
    public void waitForState(int state) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        while (adapter.getState() != state) {
            Thread.sleep(1000);
            if (System.currentTimeMillis() - startTime > 10000) {
                Assert.assertFalse("Wait for bluetooth adapter state "
                        + state + " more than "
                        + (System.currentTimeMillis() - startTime) / 1000
                        + " seconds",
                        true);
            }
        }
    }



    @Rule
    public GrantPermissionRule permissionsRules =
            GrantPermissionRule.grant(
                    Manifest.permission.BLUETOOTH_ADMIN,
                    Manifest.permission.BLUETOOTH,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.INTERNET,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_LOGS);


    @Before
    public void beforeBluetoothTests() throws InterruptedException {
        Intent intent = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        appContext.startActivity(intent);
        Thread.sleep(TIMEOUT);

        receiver = new BluetoothReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        filter.addAction(BluetoothAdapter.ACTION_LOCAL_NAME_CHANGED);
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        appContext.registerReceiver(receiver, filter);

        adapter = BluetoothAdapter.getDefaultAdapter();
        bluetoothForceEnable();
        waitForState(BluetoothAdapter.STATE_ON);
    }

    @DisplayName("Bluetooth adapter ON/OFF test")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void bluetooth_1_OnOffTest() throws InterruptedException {
        adapter.disable();
        waitForState(BluetoothAdapter.STATE_OFF);
        adapter.enable();
        waitForState(BluetoothAdapter.STATE_ON);
    }

    @DisplayName("Bluetooth adapter short time ON/OFF test")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void bluetooth_2_shortTimeOnOffTest() throws InterruptedException {
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < SHORT_TIME_WAIT) {
            Assert.assertEquals("Bluetooth adapter state not STATE_ON",
                    BluetoothAdapter.STATE_ON,
                    adapter.getState());
            Thread.sleep(2000);
        }
    }

    @DisplayName("Bluetooth adapter switching many time ON/OFF test")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void bluetooth_3_switchingManyTimeOnOffTest() throws InterruptedException {
        int switchingCount = 0;
        while (switchingCount < 30) {
            adapter.disable();
            waitForState(BluetoothAdapter.STATE_OFF);
            adapter.enable();
            waitForState(BluetoothAdapter.STATE_ON);
            switchingCount++;
        }
    }

    @DisplayName("Bluetooth adapter scanning test")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void bluetooth_4_ScanTest() throws Exception {
        adapter.startDiscovery();
        Thread.sleep(5000);
        adapter.cancelDiscovery();
        List<BluetoothDevice> scanDevices = receiver.getScanDevices();
        Assert.assertTrue("No bluetooth devices found",
                scanDevices.size() > 0);
    }
//
    @DisplayName("Bluetooth adapter change name  test")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void bluetooth_5_changeNameTest() throws Exception {
        String bluetoothName = mDevice.executeShellCommand(TestUtils.RO_PRODUCT_BOARD);
        adapter.setName(bluetoothName);
        String bluetoothTestName = bluetoothName + Calendar.getInstance().getTime().getTime();
        adapter.setName(bluetoothTestName);
        receiver.waitLocalNameChanged();
        Assert.assertEquals("Bluetooth adapter name not changed",
                bluetoothTestName,
                adapter.getName());
        adapter.setName(bluetoothName);
        receiver.waitLocalNameChanged();
        Assert.assertEquals("Bluetooth name NOT equals expected",
                bluetoothName,
                adapter.getName());
    }

    @DisplayName("Bluetooth adapter change name to max length test")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void bluetooth_6_NameMaxLengthTest() throws Exception {
        String bluetoothName = mDevice.executeShellCommand(TestUtils.RO_PRODUCT_BOARD);
        adapter.setName(bluetoothName);
        String bluetoothTestName = TestUtils.randomString(1000);
        adapter.setName(bluetoothTestName);
        receiver.waitLocalNameChanged();
        Assert.assertEquals("Bluetooth adapter name not changed",
                bluetoothTestName,
                adapter.getName());
        Assert.assertEquals("Bluetooth adapter max name length less then 1000 symbols",
                1000,
                adapter.getName().length());
        adapter.setName(bluetoothName);
        receiver.waitLocalNameChanged();
        Assert.assertEquals("Bluetooth name NOT equals expected",
                bluetoothName,
                adapter.getName());
    }

    @DisplayName("Bluetooth adapter change name to different languages test")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void bluetooth_7_NameLanguagesTest() throws Exception {
        String bluetoothName = mDevice.executeShellCommand(TestUtils.RO_PRODUCT_BOARD);
        adapter.setName(bluetoothName);
        String[] languageNames = {
                "キングフィッシャー",
                "翠鳥",
                "الرفراف طائر",
                "\uD83D\uDE02\uD83D\uDE0D\uD83C\uDF89\uD83D\uDC4D"};
        for (String bluetoothTestName : languageNames) {
            adapter.setName(bluetoothTestName);
            receiver.waitLocalNameChanged();
            Assert.assertEquals("Bluetooth adapter name not changed",
                    bluetoothTestName,
                    adapter.getName());
        }
        adapter.setName(bluetoothName);
        receiver.waitLocalNameChanged();
        Assert.assertEquals("Bluetooth name NOT equals expected",
                bluetoothName,
                adapter.getName());
    }

    @After
    public void afterBluetoothTests() {
        adapter.disable();
    }
}
