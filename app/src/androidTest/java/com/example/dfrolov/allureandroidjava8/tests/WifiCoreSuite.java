package com.example.dfrolov.allureandroidjava8.tests;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.provider.Settings;
import android.support.test.rule.GrantPermissionRule;

import com.example.dfrolov.allureandroidjava8.allure_implementation.RenesasRunner;
import com.example.dfrolov.allureandroidjava8.allure_implementation.allure.Epic;
import com.example.dfrolov.allureandroidjava8.allure_implementation.allure.Severity;
import com.example.dfrolov.allureandroidjava8.allure_implementation.allure.SeverityLevel;
import com.example.dfrolov.allureandroidjava8.allure_implementation.junit4.DisplayName;
import com.example.dfrolov.allureandroidjava8.core.WifiAdapterAllure;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
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
                    Manifest.permission.INTERNET);


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


    @DisplayName("Wifi adapter ON/OFF test")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void wifi_1_OnOffTest() throws InterruptedException {
        adapter.setWifiEnabled(false);
        adapter.waitState(WifiManager.WIFI_STATE_DISABLED);
        Assert.assertEquals("Unexpected bluetooth adapter state", WifiManager.WIFI_STATE_DISABLED, adapter.getWifiState());
        adapter.setWifiEnabled(true);
        adapter.waitState(WifiManager.WIFI_STATE_ENABLED);
        Assert.assertEquals("Unexpected bluetooth adapter state", WifiManager.WIFI_STATE_ENABLED, adapter.getWifiState());
    }


    @DisplayName("Wifi adapter connect to WPA2 password network")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void wifi_5_connectPassNetworkTest() throws InterruptedException {
        wifiConfig = adapter.getWPA2config(NETWORK_SSID, NETWORK_PASS);
        int networkId = adapter.addNetwork(wifiConfig);
        adapter.enableNetwork(networkId, true);
        boolean success = adapter.waitState(NetworkInfo.DetailedState.CONNECTED);
        Assert.assertTrue("Unexpected network connected state",success);
    }

    @DisplayName("Wifi adapter connect to open network test")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void wifi_6_connectOpenTest() throws InterruptedException {
        wifiConfig = adapter.getOpenConfig(OPEN_NETWORK_SSID);
        int networkId = adapter.addNetwork(wifiConfig);
        adapter.enableNetwork(networkId, true);
        boolean success = adapter.waitState(NetworkInfo.DetailedState.CONNECTED);
        Assert.assertTrue("Unexpected network connected state",success);
    }

    @DisplayName("Wifi adapter ENABLE/DISABLE WPA2 password network")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void wifi_7_enableDisablePassNetworkTest() throws InterruptedException {
        wifiConfig = adapter.getWPA2config(NETWORK_SSID, NETWORK_PASS);
        int networkId = adapter.addNetwork(wifiConfig);
        int switchingCount = 0;
        boolean success = false;
        while (switchingCount < 30) {
            adapter.enableNetwork(networkId, true);
            success = adapter.waitState(NetworkInfo.DetailedState.CONNECTED);
            adapter.disableNetwork(networkId);
            Assert.assertTrue("Unexpected network connected state",success);
            success = adapter.waitState(NetworkInfo.DetailedState.DISCONNECTED);
            Assert.assertTrue("Unexpected network connected state",success);
            switchingCount++;
        }
    }

    @DisplayName("Wifi adapter connect to few password network")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void wifi_8_ConnectFewNetworksTest() throws InterruptedException {
        WifiConfiguration wifiConfigRNS_AES_TKIP = adapter.getWPA2config(NETWORK_SSID, NETWORK_PASS);
        WifiConfiguration wifiConfigRNS_AES = adapter.getWPA2config(NETWORK_SSID_AES, NETWORK_PASS);
        int networkRNS_TKIP = adapter.addNetwork(wifiConfigRNS_AES_TKIP);
        int networkRNS_AES = adapter.addNetwork(wifiConfigRNS_AES);
        Assert.assertTrue("Networks not added to wifi adapter",
                adapter.getConfiguredNetworks().size() >= 2);
        adapter.enableNetwork(networkRNS_TKIP, true);
        boolean success = adapter.waitState(NetworkInfo.DetailedState.CONNECTED);
        Assert.assertTrue("Unexpected network connected state",success);
        adapter.enableNetwork(networkRNS_AES, true);
        success = adapter.waitState(NetworkInfo.DetailedState.CONNECTED);
        Assert.assertTrue("Unexpected network connected state",success);
    }

    @DisplayName("Wifi adapter ON/OFF check enabled network")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void wifi_9_checkOnOffEnabledNetwork() throws InterruptedException {
        wifiConfig = adapter.getWPA2config(NETWORK_SSID, NETWORK_PASS);
        int networkId = adapter.addNetwork(wifiConfig);
        adapter.enableNetwork(networkId, true);
        boolean success = adapter.waitState(NetworkInfo.DetailedState.CONNECTED);
        Assert.assertTrue("Unexpected network connected state",success);
        adapter.setWifiEnabled(false);
        adapter.waitState(WifiManager.WIFI_STATE_DISABLED);
        Assert.assertEquals("Unexpected bluetooth adapter state", WifiManager.WIFI_STATE_DISABLED, adapter.getWifiState());
        adapter.setWifiEnabled(true);
        adapter.waitState(WifiManager.WIFI_STATE_ENABLED);
        Assert.assertEquals("Unexpected bluetooth adapter state", WifiManager.WIFI_STATE_DISABLED, adapter.getWifiState());
        success = adapter.waitState(NetworkInfo.DetailedState.CONNECTED);
    }

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

//    @DisplayName("Wifi adapter downloadFile medium file test")
//    @Severity(SeverityLevel.NORMAL)
//    @Test
//    public void wifi_11_downloadMediumFileTest() throws IOException, InterruptedException {
//        wifiConfig = WiFiConfigHelper.getWPA2config(NETWORK_SSID, NETWORK_PASS);
//        int networkId = adapter.addNetwork(wifiConfig);
//        adapter.enableNetwork(networkId, true);
//        waitState(NetworkInfo.DetailedState.CONNECTED);
//        File file = downloadFile(mediumFileURL);
//        Assert.assertTrue("File " + file.getPath() + " not downloads", file.exists());
//        file.delete();
//    }


    @After
    public void afterWifiTests() throws InterruptedException {
        adapter.setWifiEnabled(false);
        adapter.waitState(WifiManager.WIFI_STATE_DISABLED);
    }
}
