package com.example.dfrolov.allureandroidjava8.tests;


import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.test.rule.GrantPermissionRule;

import com.example.dfrolov.allureandroidjava8.allure_implementation.RenesasRunner;
import com.example.dfrolov.allureandroidjava8.allure_implementation.allure.Description;
import com.example.dfrolov.allureandroidjava8.allure_implementation.allure.Epic;
import com.example.dfrolov.allureandroidjava8.allure_implementation.allure.Issue;
import com.example.dfrolov.allureandroidjava8.allure_implementation.allure.Link;
import com.example.dfrolov.allureandroidjava8.allure_implementation.allure.Severity;
import com.example.dfrolov.allureandroidjava8.allure_implementation.allure.SeverityLevel;
import com.example.dfrolov.allureandroidjava8.allure_implementation.junit4.DisplayName;
import com.example.dfrolov.allureandroidjava8.core.BluetoothAdapterAllure;
import com.example.dfrolov.allureandroidjava8.core.WifiAdapterAllure;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;


@Epic("Wifi core features")
@DisplayName("Wifi suite")
@RunWith(RenesasRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WifiCoreSuite extends BaseTest {
    protected WifiManager wifiAdapter;
    protected WifiAdapterAllure adapter;
    protected WifiConfiguration wifiConfig;
    protected final String NETWORK_SSID = "RNS_AES+TKIP";
    protected final String NETWORK_SSID_AES = "RNS_AES";
    protected final String NETWORK_PASS = "12345678";
    protected final String OPEN_NETWORK_SSID = "RNS_Open";

    protected String smallFileURL = "https://www.planwallpaper.com/static/images/3d_falling_leaves.jpg";
    protected String mediumFileURL = "https://speed.hetzner.de/100MB.bin";
    protected String largeFileURL = "https://speed.hetzner.de/1GB.bin";


    @Rule
    public GrantPermissionRule permissionsRules =
            GrantPermissionRule.grant(
                    Manifest.permission.CHANGE_WIFI_STATE,
                    Manifest.permission.ACCESS_WIFI_STATE,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.CHANGE_NETWORK_STATE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.INTERNET,
                    Manifest.permission.WRITE_SECURE_SETTINGS);


    @Before
    public void beforeTest() throws Exception {
        startActivity(Settings.ACTION_WIFI_SETTINGS);
        wifiAdapter = (WifiManager) appContext.getSystemService(Context.WIFI_SERVICE);
        adapter = new WifiAdapterAllure(wifiAdapter);
        adapter.setWifiEnabled(true);
        adapter.isAvailable();
        List<WifiConfiguration> configNetworks = adapter.getConfiguredNetworks();
        configNetworks.stream().filter(Objects::nonNull).forEach((network) -> adapter.removeNetwork(network.networkId));
    }

    @Issue("JIRA EXAMPLE ISSUE")
    @Link(name = "RNSS-4045 EXAMPLE", url = "https://embedded.globallogic.com.ua/testlink/")
    @Description(WifiAdapterAllure.DESCRIPTION)
    @DisplayName("Wifi adapter ON/OFF test")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void wifi_1_OnOffTest() throws InterruptedException {
        adapter.setWifiEnabled(false);
        adapter.waitState(WifiManager.WIFI_STATE_DISABLED);
        Assert.assertEquals("Unexpected wifi adapter state", WifiManager.WIFI_STATE_DISABLED, adapter.getWifiState());
        adapter.setWifiEnabled(true);
        adapter.waitState(WifiManager.WIFI_STATE_ENABLED);
        Assert.assertEquals("Unexpected wifi adapter state", WifiManager.WIFI_STATE_ENABLED, adapter.getWifiState());
    }

    @Issue("JIRA EXAMPLE ISSUE")
    @Link(name = "RNSS-4045 EXAMPLE", url = "https://embedded.globallogic.com.ua/testlink/")
    @Description(WifiAdapterAllure.DESCRIPTION)
    @DisplayName("Wifi adapter switching many times ON/OFF test")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void wifi_2_SwitchingManyTimesOnOffTest() throws InterruptedException {
        int switchingCount = 0;
        while (switchingCount < 30) {
            adapter.setWifiEnabled(false);
            adapter.waitState(WifiManager.WIFI_STATE_DISABLED);
            Assert.assertEquals("Unexpected wifi adapter state", WifiManager.WIFI_STATE_DISABLED, adapter.getWifiState());
            adapter.setWifiEnabled(true);
            adapter.waitState(WifiManager.WIFI_STATE_ENABLED);
            Assert.assertEquals("Unexpected wifi adapter state", WifiManager.WIFI_STATE_ENABLED, adapter.getWifiState());
            switchingCount++;
        }
    }

    @Issue("JIRA EXAMPLE ISSUE")
    @Link(name = "RNSS-4045 EXAMPLE", url = "https://embedded.globallogic.com.ua/testlink/")
    @Description(WifiAdapterAllure.DESCRIPTION)
    @DisplayName("Wifi adapter short time ON test")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void wifi_3_ShortTimeOnTest() throws InterruptedException {
        int switchingCount = 0;
        while (switchingCount < 30) {
            adapter.waitTime(1000);
            Assert.assertEquals("Unexpected wifi adapter state", WifiManager.WIFI_STATE_ENABLED, adapter.getWifiState());
            switchingCount++;
        }
    }

    @Issue("JIRA EXAMPLE ISSUE")
    @Link(name = "RNSS-4045 EXAMPLE", url = "https://embedded.globallogic.com.ua/testlink/")
    @Description(WifiAdapterAllure.DESCRIPTION)
    @DisplayName("Wifi adapter connect to password network")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void wifi_4_connectPassNetworkTest() throws InterruptedException {
        wifiConfig = adapter.getWPA2config(NETWORK_SSID, NETWORK_PASS);
        int networkId = adapter.addNetwork(wifiConfig);
        adapter.enableNetwork(networkId, true);
        adapter.waitState(NetworkInfo.DetailedState.CONNECTED);
        Assert.assertEquals("Unexpected wifi connected state",NetworkInfo.DetailedState.CONNECTED,adapter.getDetailedState());
    }

    @Issue("JIRA EXAMPLE ISSUE")
    @Link(name = "RNSS-4045 EXAMPLE", url = "https://embedded.globallogic.com.ua/testlink/")
    @Description(WifiAdapterAllure.DESCRIPTION)
    @DisplayName("Wifi adapter connect to open network test")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void wifi_5_connectOpenTest() throws InterruptedException {
        wifiConfig = adapter.getOpenConfig(OPEN_NETWORK_SSID);
        int networkId = adapter.addNetwork(wifiConfig);
        adapter.enableNetwork(networkId, true);
        adapter.waitState(NetworkInfo.DetailedState.CONNECTED);
        Assert.assertEquals("Unexpected network connected state",NetworkInfo.DetailedState.CONNECTED,adapter.getDetailedState());
    }


    @Issue("JIRA EXAMPLE ISSUE")
    @Link(name = "RNSS-4045 EXAMPLE", url = "https://embedded.globallogic.com.ua/testlink/")
    @Description(WifiAdapterAllure.DESCRIPTION)
    @DisplayName("Wifi adapter airplane mode test")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void wifi_6_AirplaneModeTest() throws Exception {
        setAirPlaneMode(true);
        adapter.waitState(WifiManager.WIFI_STATE_DISABLED);
        Assert.assertTrue("Unexpected airplane mpde",getAirplaneMode());
        Assert.assertEquals("Unexpected bluetooth adapter state", WifiManager.WIFI_STATE_DISABLED, adapter.getWifiState());
        setAirPlaneMode(false);
        adapter.waitState(WifiManager.WIFI_STATE_ENABLED);
        Assert.assertFalse("Unexpected airplane mpde",getAirplaneMode());
        Assert.assertEquals("Unexpected bluetooth adapter state", WifiManager.WIFI_STATE_ENABLED, adapter.getWifiState());
    }

    @Issue("JIRA EXAMPLE ISSUE")
    @Link(name = "RNSS-4045 EXAMPLE", url = "https://embedded.globallogic.com.ua/testlink/")
    @Description(WifiAdapterAllure.DESCRIPTION)
    @DisplayName("Wifi adapter switch many time airplane mode test")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void wifi_7_SwitchManyTimeAirplaneModeTest() throws Exception {
        int switchingCount = 0;
        while (switchingCount < 30) {
            setAirPlaneMode(true);
            adapter.waitState(WifiManager.WIFI_STATE_DISABLED);
            Assert.assertTrue("Unexpected airplane mpde", getAirplaneMode());
            Assert.assertEquals("Unexpected wifi adapter state", WifiManager.WIFI_STATE_DISABLED, adapter.getWifiState());
            setAirPlaneMode(false);
            adapter.waitState(WifiManager.WIFI_STATE_ENABLED);
            Assert.assertFalse("Unexpected airplane mpde", getAirplaneMode());
            Assert.assertEquals("Unexpected wifi adapter state", WifiManager.WIFI_STATE_ENABLED, adapter.getWifiState());
            switchingCount++;
        }
    }

    @Issue("JIRA EXAMPLE ISSUE")
    @Link(name = "RNSS-4045 EXAMPLE", url = "https://embedded.globallogic.com.ua/testlink/")
    @Description(WifiAdapterAllure.DESCRIPTION)
    @DisplayName("Wifi adapter force enable airplane mode test")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void wifi_8_ForceEnableAirplaneModeTest() throws Exception {
        setAirPlaneMode(true);
        adapter.waitState(WifiManager.WIFI_STATE_DISABLED);
        Assert.assertTrue("Unexpected airplane mpde",getAirplaneMode());
        Assert.assertEquals("Unexpected wifi adapter state", WifiManager.WIFI_STATE_DISABLED, adapter.getWifiState());
        adapter.setWifiEnabled(true);
        adapter.waitState(WifiManager.WIFI_STATE_ENABLED);
        Assert.assertEquals("Unexpected wifi adapter state", WifiManager.WIFI_STATE_ENABLED, adapter.getWifiState());
    }

    @Issue("JIRA EXAMPLE ISSUE")
    @Link(name = "RNSS-4045 EXAMPLE", url = "https://embedded.globallogic.com.ua/testlink/")
    @Description(WifiAdapterAllure.DESCRIPTION)
    @DisplayName("Wifi adapter auto connect to network airplane mode test")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void wifi_9_ConnectNetworkAirplaneModeTest() throws Exception {
        wifiConfig = adapter.getWPA2config(NETWORK_SSID, NETWORK_PASS);
        int networkId = adapter.addNetwork(wifiConfig);
        adapter.enableNetwork(networkId, true);
        adapter.waitState(NetworkInfo.DetailedState.CONNECTED);
        Assert.assertEquals("Unexpected wifi connected state",NetworkInfo.DetailedState.CONNECTED,adapter.getDetailedState());
        setAirPlaneMode(true);
        adapter.waitState(WifiManager.WIFI_STATE_DISABLED);
        Assert.assertTrue("Unexpected airplane mpde",getAirplaneMode());
        Assert.assertEquals("Unexpected wifi adapter state", WifiManager.WIFI_STATE_DISABLED, adapter.getWifiState());
        adapter.waitState(NetworkInfo.DetailedState.FAILED);
        Assert.assertEquals("Unexpected wifi connected state",NetworkInfo.DetailedState.FAILED,adapter.getDetailedState());
        setAirPlaneMode(false);
        adapter.waitState(WifiManager.WIFI_STATE_ENABLED);
        Assert.assertFalse("Unexpected airplane mpde",getAirplaneMode());
        Assert.assertEquals("Unexpected wifi adapter state", WifiManager.WIFI_STATE_ENABLED, adapter.getWifiState());
        adapter.waitState(NetworkInfo.DetailedState.CONNECTED);
        Assert.assertEquals("Unexpected wifi connected state",NetworkInfo.DetailedState.CONNECTED,adapter.getDetailedState());
    }

    @Issue("JIRA EXAMPLE ISSUE")
    @Link(name = "RNSS-4045 EXAMPLE", url = "https://embedded.globallogic.com.ua/testlink/")
    @Description(WifiAdapterAllure.DESCRIPTION)
    @DisplayName("Wifi adapter downloadFile small file test")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void wifi_10_downloadSmallFileTest() throws IOException, InterruptedException {
        wifiConfig = adapter.getWPA2config(NETWORK_SSID, NETWORK_PASS);
        int networkId = adapter.addNetwork(wifiConfig);
        adapter.enableNetwork(networkId, true);
        adapter.waitState(NetworkInfo.DetailedState.CONNECTED);
        File file = adapter.downloadFile(smallFileURL);
        Assert.assertTrue("File " + file.getPath() + " not downloads", file.exists());
        file.delete();
    }

    @Issue("JIRA EXAMPLE ISSUE")
    @Link(name = "RNSS-4045 EXAMPLE", url = "https://embedded.globallogic.com.ua/testlink/")
    @Description(WifiAdapterAllure.DESCRIPTION)
    @DisplayName("Wifi adapter with bluetooth adapter ON/OFF test")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void wifi_11_BluetoothAdapterOnOffTest() throws Exception {
        BluetoothAdapter bleAdapter = BluetoothAdapter.getDefaultAdapter();
        BluetoothAdapterAllure bleAllureadapter = new BluetoothAdapterAllure(bleAdapter);
        bleAllureadapter.bluetoothForceEnable();
        bleAllureadapter.isAvailable();
        Assert.assertEquals("Unexpected bluetooth adapter state", BluetoothAdapter.STATE_ON, bleAllureadapter.getState());
        Assert.assertEquals("Unexpected wifi adapter state", WifiManager.WIFI_STATE_ENABLED, adapter.getWifiState());
        bleAllureadapter.disable();
        bleAllureadapter.waitForState(BluetoothAdapter.STATE_OFF);
        Assert.assertEquals("Unexpected bluetooth adapter state", BluetoothAdapter.STATE_OFF, bleAllureadapter.getState());
        Assert.assertEquals("Unexpected wifi adapter state", WifiManager.WIFI_STATE_ENABLED, adapter.getWifiState());
        adapter.setWifiEnabled(false);
        adapter.waitState(WifiManager.WIFI_STATE_DISABLED);
        Assert.assertEquals("Unexpected wifi adapter state", WifiManager.WIFI_STATE_DISABLED, adapter.getWifiState());
        Assert.assertEquals("Unexpected bluetooth adapter state", BluetoothAdapter.STATE_OFF, bleAllureadapter.getState());
    }

    @Issue("JIRA EXAMPLE ISSUE")
    @Link(name = "RNSS-4045 EXAMPLE", url = "https://embedded.globallogic.com.ua/testlink/")
    @Description(WifiAdapterAllure.DESCRIPTION)
    @DisplayName("Wifi adapter with bluetooth adapter airplane mode test")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void wifi_12_BluetoothAdapterAirplaneModeTest() throws Exception {
        BluetoothAdapter bleAdapter = BluetoothAdapter.getDefaultAdapter();
        BluetoothAdapterAllure bleAllureadapter = new BluetoothAdapterAllure(bleAdapter);
        bleAllureadapter.bluetoothForceEnable();
        bleAllureadapter.isAvailable();
        Assert.assertEquals("Unexpected bluetooth adapter state", BluetoothAdapter.STATE_ON, bleAllureadapter.getState());
        Assert.assertEquals("Unexpected wifi adapter state", WifiManager.WIFI_STATE_ENABLED, adapter.getWifiState());
        setAirPlaneMode(true);
        adapter.waitState(WifiManager.WIFI_STATE_DISABLED);
        bleAllureadapter.waitForState(BluetoothAdapter.STATE_OFF);
        Assert.assertTrue("Unexpected airplane mpde",getAirplaneMode());
        Assert.assertEquals("Unexpected bluetooth adapter state", BluetoothAdapter.STATE_OFF, bleAllureadapter.getState());
        Assert.assertEquals("Unexpected wifi adapter state", WifiManager.WIFI_STATE_DISABLED, adapter.getWifiState());
        setAirPlaneMode(false);
        adapter.waitState(WifiManager.WIFI_STATE_ENABLED);
        bleAllureadapter.waitForState(BluetoothAdapter.STATE_ON);
        Assert.assertFalse("Unexpected airplane mpde",getAirplaneMode());
        Assert.assertEquals("Unexpected wifi adapter state", WifiManager.WIFI_STATE_ENABLED, adapter.getWifiState());
        Assert.assertEquals("Unexpected bluetooth adapter state", BluetoothAdapter.STATE_ON, bleAllureadapter.getState());
    }


    @After
    public void afterWifiTests() throws InterruptedException, IOException {
        adapter.setWifiEnabled(false);
        adapter.waitState(WifiManager.WIFI_STATE_DISABLED);
        setAirPlaneMode(false);
    }
}
