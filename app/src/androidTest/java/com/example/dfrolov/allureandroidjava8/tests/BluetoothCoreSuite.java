package com.example.dfrolov.allureandroidjava8.tests;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.test.rule.GrantPermissionRule;
import android.util.Log;


import com.example.dfrolov.allureandroidjava8.allure_implementation.RenesasRunner;
import com.example.dfrolov.allureandroidjava8.allure_implementation.allure.Description;
import com.example.dfrolov.allureandroidjava8.allure_implementation.allure.Epic;
import com.example.dfrolov.allureandroidjava8.allure_implementation.allure.Link;
import com.example.dfrolov.allureandroidjava8.allure_implementation.allure.Severity;
import com.example.dfrolov.allureandroidjava8.allure_implementation.allure.SeverityLevel;
import com.example.dfrolov.allureandroidjava8.allure_implementation.allure.Step;
import com.example.dfrolov.allureandroidjava8.allure_implementation.allure.TmsLink;
import com.example.dfrolov.allureandroidjava8.allure_implementation.allure.TmsLinks;
import com.example.dfrolov.allureandroidjava8.allure_implementation.junit4.DisplayName;
import com.example.dfrolov.allureandroidjava8.core.BluetoothAdapterAllure;
import com.example.dfrolov.allureandroidjava8.receivers.BluetoothReceiver;
import com.example.dfrolov.allureandroidjava8.utils.TestUtils;

import org.junit.After;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.util.Arrays;
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
    public BluetoothAdapter bleAdapter;
    public BluetoothAdapterAllure adapter;
    protected String defaultBluetoothName;
    protected String[] languageNames = {
            "блутуз",
            "キングフィッシャー",
            "翠鳥",
            "الرفراف طائر",
            "\uD83D\uDE02\uD83D\uDE0D\uD83C\uDF89\uD83D\uDC4D"};


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
    public void beforeBluetoothTests() throws Exception {
        startActivity(Settings.ACTION_BLUETOOTH_SETTINGS);
        bleAdapter = BluetoothAdapter.getDefaultAdapter();
        adapter = new BluetoothAdapterAllure(bleAdapter);
        adapter.bluetoothForceEnable();
        adapter.isAvailable();
        Assert.assertEquals("Unexpected bluetooth adapter state", BluetoothAdapter.STATE_ON, adapter.getState());
        defaultBluetoothName = adapter.setDefultName();
    }

    @Link(name = "RNSS-4045 EXAMPLE", url = "https://embedded.globallogic.com.ua/testlink/")
    @Description(BluetoothAdapterAllure.DESCRIPTION)
    @DisplayName("Bluetooth adapter ON/OFF test")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void bluetooth_1_OnOffTest() throws InterruptedException {
        adapter.disable();
        adapter.waitForState(BluetoothAdapter.STATE_OFF);
        Assert.assertEquals("Unexpected bluetooth adapter state", BluetoothAdapter.STATE_OFF, adapter.getState());
        adapter.enable();
        adapter.waitForState(BluetoothAdapter.STATE_ON);
        Assert.assertEquals("Unexpected bluetooth adapter state", BluetoothAdapter.STATE_ON, adapter.getState());
    }

    @Link(name = "RNSS-4045 EXAMPLE", url = "https://embedded.globallogic.com.ua/testlink/")
    @Description(BluetoothAdapterAllure.DESCRIPTION)
    @DisplayName("Bluetooth adapter switching many time ON/OFF test")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void bluetooth_2_SwitchingManyTimeOnOffTest() throws InterruptedException {
        int switchingCount = 0;
        while (switchingCount < 30) {
            adapter.disable();
            adapter.waitForState(BluetoothAdapter.STATE_OFF);
            Assert.assertEquals("Unexpected bluetooth adapter state", BluetoothAdapter.STATE_OFF, adapter.getState());
            adapter.enable();
            adapter.waitForState(BluetoothAdapter.STATE_ON);
            Assert.assertEquals("Unexpected bluetooth adapter state", BluetoothAdapter.STATE_ON, adapter.getState());
            switchingCount++;
        }
    }


    @Link(name = "RNSS-4045 EXAMPLE", url = "https://embedded.globallogic.com.ua/testlink/")
    @Description(BluetoothAdapterAllure.DESCRIPTION)
    @DisplayName("Bluetooth adapter short time ON test")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void bluetooth_3_ShortTimeOnTest() throws InterruptedException {
        int interactions = 0;
        while (interactions < 30) {
            int state = adapter.getState();
            Assert.assertEquals("Bluetooth adapter unexpected state", BluetoothAdapter.STATE_ON, state);
            adapter.waitTime(1000);
            interactions++;
        }
    }

    @Link(name = "RNSS-4045 EXAMPLE", url = "https://embedded.globallogic.com.ua/testlink/")
    @Description(BluetoothAdapterAllure.DESCRIPTION)
    @DisplayName("Bluetooth adapter change name test")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void bluetooth_4_ChangeNameTest() throws Exception {
        String expectedName = adapter.setName(defaultBluetoothName + Calendar.getInstance().getTime().getTime());
        adapter.waitLocalNameChanged();
        String actualName = adapter.getName();
        Assert.assertEquals("Bluetooth adapter name not changed",
                expectedName,
                actualName);
    }

    @Link(name = "RNSS-4045 EXAMPLE", url = "https://embedded.globallogic.com.ua/testlink/")
    @Description(BluetoothAdapterAllure.DESCRIPTION)
    @DisplayName("Bluetooth adapter check device empty name")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void bluetooth_5_SetEmptyNameTest() throws Exception {
        String unexpectedName = adapter.setName("");
        String actualName = adapter.getName();
        Assert.assertNotEquals("Bluetooth adapter unexpected name " + unexpectedName, unexpectedName, actualName);
    }

    @Link(name = "RNSS-4045 EXAMPLE", url = "https://embedded.globallogic.com.ua/testlink/")
    @Description(BluetoothAdapterAllure.DESCRIPTION)
    @DisplayName("Bluetooth adapter change name to max length test")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void bluetooth_6_NameMaxLengthTest() throws Exception {
        String expectedName = adapter.setName(TestUtils.randomString(1000));
        adapter.waitLocalNameChanged();
        String actualName = adapter.getName();
        Assert.assertEquals("Unexpected bluetooth adapter name",
                expectedName,
                actualName);
        Assert.assertEquals("Unexpected bluetooth adapter name length",
                1000,
                actualName.length());
    }

    @Link(name = "RNSS-4045 EXAMPLE", url = "https://embedded.globallogic.com.ua/testlink/")
    @Description(BluetoothAdapterAllure.DESCRIPTION)
    @DisplayName("Bluetooth adapter change name to different languages test")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void bluetooth_7_NameLanguagesTest() throws Exception {
        for (String bluetoothTestName : languageNames) {
            String expectedName = adapter.setName(bluetoothTestName);
            adapter.waitLocalNameChanged();
            String actualName = adapter.getName();
            Assert.assertEquals("Unexpected bluetooth adapter name",
                    expectedName,
                    actualName);
        }
    }

    @Link(name = "RNSS-4045 EXAMPLE", url = "https://embedded.globallogic.com.ua/testlink/")
    @Description(BluetoothAdapterAllure.DESCRIPTION)
    @DisplayName("Bluetooth adapter ON get remote device test")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void bluetooth_8_EnableGetRemoteDeviceTest() throws InterruptedException {
        BluetoothDevice device = adapter.getRemoteDevice("00:11:22:AA:BB:CC");
        Assert.assertNotNull("Unexpected bluetooth device ", device);
        Assert.assertEquals("Unexpected bluetooth device address", "00:11:22:AA:BB:CC", device.getAddress());
    }

    @Link(name = "RNSS-4045 EXAMPLE", url = "https://embedded.globallogic.com.ua/testlink/")
    @Description(BluetoothAdapterAllure.DESCRIPTION)
    @DisplayName("Bluetooth adapter OFF get remote device test")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void bluetooth_9_DisableGetRemoteDeviceTest() throws InterruptedException {
        adapter.disable();
        adapter.waitForState(BluetoothAdapter.STATE_OFF);
        Assert.assertEquals("Unexpected bluetooth adapter state", BluetoothAdapter.STATE_OFF, adapter.getState());
        BluetoothDevice device = adapter.getRemoteDevice("00:11:22:AA:BB:CC");
        Assert.assertNotNull("Unexpected bluetooth device ", device);
        Assert.assertEquals("Unexpected bluetooth device address", "00:11:22:AA:BB:CC", device.getAddress());
    }

    @Link(name = "RNSS-4045 EXAMPLE", url = "https://embedded.globallogic.com.ua/testlink/")
    @Description(BluetoothAdapterAllure.DESCRIPTION)
    @DisplayName("Bluetooth adapter scanning test")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void bluetooth_1_0_ScanTest() throws Exception {
        adapter.startDiscovery();
        adapter.waitTime(5000);
        adapter.cancelDiscovery();
        List<BluetoothDevice> scanDevices = adapter.getScanDevices();
        Assert.assertTrue("Unexpected bluetooth devices count",
                scanDevices.size() > 0);
    }

    @Link(name = "RNSS-4045 EXAMPLE", url = "https://embedded.globallogic.com.ua/testlink/")
    @Description(BluetoothAdapterAllure.DESCRIPTION)
    @DisplayName("Bluetooth adapter airplane mode test")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void bluetooth_1_1_AirplaneModeTest() throws Exception {
        setAirPlaneMode(true);
        adapter.waitForState(BluetoothAdapter.STATE_OFF);
        Assert.assertTrue("Unexpected airplane mpde",getAirplaneMode());
        Assert.assertEquals("Unexpected bluetooth adapter state", BluetoothAdapter.STATE_OFF, adapter.getState());
        setAirPlaneMode(false);
        adapter.waitForState(BluetoothAdapter.STATE_ON);
        Assert.assertFalse("Unexpected airplane mpde",getAirplaneMode());
        Assert.assertEquals("Unexpected bluetooth adapter state", BluetoothAdapter.STATE_ON, adapter.getState());
    }

    @Link(name = "RNSS-4045 EXAMPLE", url = "https://embedded.globallogic.com.ua/testlink/")
    @Description(BluetoothAdapterAllure.DESCRIPTION)
    @DisplayName("Bluetooth adapter airplane mode switching many times test")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void bluetooth_1_2_SwitchManyTimeAirplaneModeTest() throws Exception {
        int switchingCount = 0;
        while (switchingCount < 30) {
            setAirPlaneMode(true);
            adapter.waitForState(BluetoothAdapter.STATE_OFF);
            Assert.assertTrue("Unexpected airplane mpde",getAirplaneMode());
            Assert.assertEquals("Unexpected bluetooth adapter state", BluetoothAdapter.STATE_OFF, adapter.getState());
            setAirPlaneMode(false);
            adapter.waitForState(BluetoothAdapter.STATE_ON);
            Assert.assertFalse("Unexpected airplane mpde",getAirplaneMode());
            Assert.assertEquals("Unexpected bluetooth adapter state", BluetoothAdapter.STATE_ON, adapter.getState());
            switchingCount++;
        }
    }

    @Link(name = "RNSS-4045 EXAMPLE", url = "https://embedded.globallogic.com.ua/testlink/")
    @Description(BluetoothAdapterAllure.DESCRIPTION)
    @DisplayName("Bluetooth adapter force enable airplane mode test")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void bluetooth_1_3_ForceOnAirplaneModeTest() throws Exception {
        setAirPlaneMode(true);
        adapter.waitForState(BluetoothAdapter.STATE_OFF);
        Assert.assertTrue("Unexpected airplane mpde",getAirplaneMode());
        Assert.assertEquals("Unexpected bluetooth adapter state", BluetoothAdapter.STATE_OFF, adapter.getState());
        adapter.enable();
        adapter.waitForState(BluetoothAdapter.STATE_ON);
        Assert.assertEquals("Unexpected bluetooth adapter state", BluetoothAdapter.STATE_ON, adapter.getState());
        setAirPlaneMode(false);
        Assert.assertEquals("Unexpected bluetooth adapter state", BluetoothAdapter.STATE_ON, adapter.getState());
        Assert.assertFalse("Unexpected airplane mpde",getAirplaneMode());
        adapter.disable();
        adapter.waitForState(BluetoothAdapter.STATE_OFF);
        Assert.assertEquals("Unexpected bluetooth adapter state", BluetoothAdapter.STATE_OFF, adapter.getState());
    }

    @After
    public void afterBluetoothTests() throws IOException {
        adapter.disable();
    }
}
