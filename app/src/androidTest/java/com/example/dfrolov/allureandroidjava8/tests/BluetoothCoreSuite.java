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

import static junit.framework.TestCase.fail;

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

    public void bluetoothForceEnable() {
        boolean success = true;
        if (!adapter.isEnabled()) {
            success = adapter.enable();
        }
    }

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
    public void bluetoothOnOffTest() throws InterruptedException {
        adapter.disable();
        waitForState(BluetoothAdapter.STATE_OFF);
        adapter.enable();
        waitForState(BluetoothAdapter.STATE_ON);
    }

    @DisplayName("Bluetooth adapter short time ON/OFF test")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void bluetoothShortTimeOnOffTest() throws InterruptedException {
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < SHORT_TIME_WAIT) {
            Assert.assertEquals("Bluetooth adapter state not STATE_ON",
                    BluetoothAdapter.STATE_ON,
                    adapter.getState());
            Thread.sleep(2000);
        }
    }


    @DisplayName("Bluetooth adapter check correct bluetooth addresses test")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void bluetoothCheckCorrectMacAddressesTest() {
        Assert.assertFalse("Bluetooth address can't be null", BluetoothAdapter.checkBluetoothAddress(null));
        // Must be 17 characters long.
        Assert.assertFalse("Bluetooth address must be 17 characters long", BluetoothAdapter.checkBluetoothAddress(""));
        Assert.assertFalse("Bluetooth address must be 17 characters long", BluetoothAdapter.checkBluetoothAddress("0"));
        Assert.assertFalse("Bluetooth address must be 17 characters long", BluetoothAdapter.checkBluetoothAddress("00"));
        Assert.assertFalse("Bluetooth address must be 17 characters long", BluetoothAdapter.checkBluetoothAddress("00:"));
        Assert.assertFalse("Bluetooth address must be 17 characters long", BluetoothAdapter.checkBluetoothAddress("00:0"));
        Assert.assertFalse("Bluetooth address must be 17 characters long", BluetoothAdapter.checkBluetoothAddress("00:00"));
        Assert.assertFalse("Bluetooth address must be 17 characters long", BluetoothAdapter.checkBluetoothAddress("00:00:"));
        Assert.assertFalse("Bluetooth address must be 17 characters long", BluetoothAdapter.checkBluetoothAddress("00:00:0"));
        Assert.assertFalse("Bluetooth address must be 17 characters long", BluetoothAdapter.checkBluetoothAddress("00:00:00"));
        Assert.assertFalse("Bluetooth address must be 17 characters long", BluetoothAdapter.checkBluetoothAddress("00:00:00:"));
        Assert.assertFalse("Bluetooth address must be 17 characters long", BluetoothAdapter.checkBluetoothAddress("00:00:00:0"));
        Assert.assertFalse("Bluetooth address must be 17 characters long", BluetoothAdapter.checkBluetoothAddress("00:00:00:00"));
        Assert.assertFalse("Bluetooth address must be 17 characters long", BluetoothAdapter.checkBluetoothAddress("00:00:00:00:"));
        Assert.assertFalse("Bluetooth address must be 17 characters long", BluetoothAdapter.checkBluetoothAddress("00:00:00:00:0"));
        Assert.assertFalse("Bluetooth address must be 17 characters long", BluetoothAdapter.checkBluetoothAddress("00:00:00:00:00"));
        Assert.assertFalse("Bluetooth address must be 17 characters long", BluetoothAdapter.checkBluetoothAddress("00:00:00:00:00:"));
        Assert.assertFalse("Bluetooth address must be 17 characters long", BluetoothAdapter.checkBluetoothAddress("00:00:00:00:00:0"));
        //
        Assert.assertFalse("Bluetooth adapter must have colons between octets", BluetoothAdapter.checkBluetoothAddress(
                "00x00:00:00:00:00"));
        Assert.assertFalse("Bluetooth adapter must have colons between octets", BluetoothAdapter.checkBluetoothAddress(
                "00:00.00:00:00:00"));
        Assert.assertFalse("Bluetooth adapter must have colons between octets", BluetoothAdapter.checkBluetoothAddress(
                "00:00:00-00:00:00"));
        Assert.assertFalse("Bluetooth adapter must have colons between octets", BluetoothAdapter.checkBluetoothAddress(
                "00:00:00:00900:00"));
        Assert.assertFalse("Bluetooth adapter must have colons between octets", BluetoothAdapter.checkBluetoothAddress(
                "00:00:00:00:00?00"));
        Assert.assertFalse("Bluetooth adapter hex letters must be uppercase.", BluetoothAdapter.checkBluetoothAddress(
                "a0:00:00:00:00:00"));
        Assert.assertFalse("Bluetooth adapter hex letters must be uppercase.", BluetoothAdapter.checkBluetoothAddress(
                "0b:00:00:00:00:00"));
        Assert.assertFalse("Bluetooth adapter hex letters must be uppercase.", BluetoothAdapter.checkBluetoothAddress(
                "00:c0:00:00:00:00"));
        Assert.assertFalse("Bluetooth adapter hex letters must be uppercase.", BluetoothAdapter.checkBluetoothAddress(
                "00:0d:00:00:00:00"));
        Assert.assertFalse("Bluetooth adapter hex letters must be uppercase.", BluetoothAdapter.checkBluetoothAddress(
                "00:00:e0:00:00:00"));
        Assert.assertFalse("Bluetooth adapter hex letters must be uppercase.", BluetoothAdapter.checkBluetoothAddress(
                "00:00:0f:00:00:00"));
        Assert.assertTrue(BluetoothAdapter.checkBluetoothAddress(
                "00:00:00:00:00:00"));
        Assert.assertTrue(BluetoothAdapter.checkBluetoothAddress(
                "12:34:56:78:9A:BC"));
        Assert.assertTrue(BluetoothAdapter.checkBluetoothAddress(
                "DE:F0:FE:DC:B8:76"));
    }

    @DisplayName("Bluetooth adapter check correct device adapter address test")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void bluetoothCheckCorrectDeviceAddressTest() throws InterruptedException {
        Assert.assertTrue("Bluetooth adapter current mac address incorrect", BluetoothAdapter.checkBluetoothAddress(adapter.getAddress()));
    }

    @DisplayName("Bluetooth adapter get device name test")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void bluetoothGetNameTest() {
        Assert.assertNotNull(adapter.getName());
    }

    @DisplayName("Bluetooth adapter check device empty name")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void bluetoothSetEmptyNameTest() throws Exception {
        adapter.setName("");
        Assert.assertNotEquals("Bluetooth adapter name should not be empty","",adapter.getName());
    }

    @DisplayName("Bluetooth adapter get remote device test")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void bluetoothGetRemoteDeviceTest() throws InterruptedException {
        adapter.disable();
        waitForState(BluetoothAdapter.STATE_OFF);
        BluetoothDevice device = adapter.getRemoteDevice("00:11:22:AA:BB:CC");
        Assert.assertNotNull(device);
        Assert.assertEquals("00:11:22:AA:BB:CC", device.getAddress());
    }

    @DisplayName("Bluetooth adapter switching many time ON/OFF test")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void bluetoothSwitchingManyTimeOnOffTest() throws InterruptedException {
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
    public void bluetoothScanTest() throws Exception {
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
    public void bluetoothChangeNameTest() throws Exception {
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
    public void bluetoothNameMaxLengthTest() throws Exception {
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
    public void bluetoothNameLanguagesTest() throws Exception {
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
