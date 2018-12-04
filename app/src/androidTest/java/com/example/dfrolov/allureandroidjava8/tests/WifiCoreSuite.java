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
import com.example.dfrolov.allureandroidjava8.utils.WiFiConfigHelper;

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
    protected WifiManager adapter;
    protected WifiConfiguration wifiConfig;
    protected final String NETWORK_SSID = "RNS_AES+TKIP";
    protected final String NETWORK_SSID_AES = "RNS_AES";
    protected final String NETWORK_PASS = "12345678";
    protected final String OPEN_NETWORK_SSID = "RNS_Open";
    protected ConnectivityManager connectivityManager;
    protected String smallFileURL = "https://www.planwallpaper.com/static/images/3d_falling_leaves.jpg";
    protected String mediumFileURL = "https://speed.hetzner.de/100MB.bin";
    protected String largeFileURL = "https://speed.hetzner.de/1GB.bin";

    public boolean isInternetAvailable() {
        try {
            final InetAddress address = InetAddress.getByName("www.google.com");
            return !address.equals("");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return false;
    }

    public File downloadFile(String urlStr) throws IOException {
        URL url = new URL(urlStr);
        String name = urlStr.split("/")[urlStr.split("/").length - 1];
        InputStream is = url.openStream();
        DataInputStream dis = new DataInputStream(is);
        byte[] buffer = new byte[1024];
        int length = 0;
        File directory = new File(Environment.getExternalStorageDirectory() + File.separator + "test");
        directory.mkdirs();
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "test" + File.separator + name);
        FileOutputStream fos = new FileOutputStream(file);
        while ((length = dis.read(buffer)) > 0) {
            fos.write(buffer, 0, length);
        }
        return file;
    }

    public void waitState(NetworkInfo.DetailedState state) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        NetworkInfo.DetailedState currentState = NetworkInfo.DetailedState.FAILED;
        while (currentState != state) {
            Thread.sleep(1000);
            if (System.currentTimeMillis() - startTime > 15000) {
                Assert.assertFalse("Wait for connection state "
                                + state
                                + " more than "
                                + 10
                                + " seconds",
                        true);
            }
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();
            if (info != null) {
                currentState = info.getDetailedState();
            }
        }
    }


    public void waitState(int state) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        int currentState = adapter.getWifiState();
        while (currentState != state) {
            Thread.sleep(1000);
            if (System.currentTimeMillis() - startTime > 15000) {
                Assert.assertFalse("Wait for  state "
                                + state
                                + " more than "
                                + (System.currentTimeMillis() - startTime) / 1000
                                + " seconds",
                        true);
            }
            currentState = adapter.getWifiState();
        }
    }

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
    public void beforeTest() throws InterruptedException {
        connectivityManager = (ConnectivityManager) appContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        adapter = (WifiManager) appContext.getSystemService(Context.WIFI_SERVICE);

        Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        appContext.startActivity(intent);
        Thread.sleep(TIMEOUT);

        adapter.setWifiEnabled(true);
        waitState(WifiManager.WIFI_STATE_ENABLED);
        List<WifiConfiguration> configNetworks = adapter.getConfiguredNetworks();
        configNetworks.stream().filter(Objects::nonNull).forEach((network) -> adapter.removeNetwork(network.networkId));
    }


    @DisplayName("Wifi adapter ON/OFF test")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void wifi_1_OnOffTest() throws InterruptedException {
        adapter.setWifiEnabled(true);
        waitState(WifiManager.WIFI_STATE_ENABLED);
        adapter.setWifiEnabled(false);
        waitState(WifiManager.WIFI_STATE_DISABLED);
    }

    @DisplayName("Wifi adapter short time ON/OFF test")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void wifi_2_shortTimeOnOffTest() throws InterruptedException {
        adapter.setWifiEnabled(true);
        waitState(WifiManager.WIFI_STATE_ENABLED);
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < SHORT_TIME_WAIT) {
            Assert.assertEquals("Wifi adapter state not STATE_ON",
                    WifiManager.WIFI_STATE_ENABLED,
                    adapter.getWifiState());
            Thread.sleep(1000);
        }
        adapter.setWifiEnabled(false);
        waitState(WifiManager.WIFI_STATE_DISABLED);
    }


    @DisplayName("Wifi adapter switching many times ON/OFF test")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void wifi_3_switchManyTimesOnOffTest() throws InterruptedException {
        int switchingCount = 0;
        while (switchingCount < 30) {
            adapter.setWifiEnabled(true);
            waitState(WifiManager.WIFI_STATE_ENABLED);
            adapter.setWifiEnabled(false);
            waitState(WifiManager.WIFI_STATE_DISABLED);
            switchingCount++;
        }
    }

    @DisplayName("Wifi adapter scanning test")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void wifi_4_scanTest() throws InterruptedException {
        boolean success = adapter.startScan();
        Thread.sleep(7000);
        List<ScanResult> scanDevices = adapter.getScanResults();
        Assert.assertTrue("Wifi adapter can't find devices",
                scanDevices.size() > 0);
    }

    @DisplayName("Wifi adapter connect to WPA2 password network")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void wifi_5_connectPassNetworkTest() throws InterruptedException {
        wifiConfig = WiFiConfigHelper.getWPA2config(NETWORK_SSID, NETWORK_PASS);
        int networkId = adapter.addNetwork(wifiConfig);
        adapter.enableNetwork(networkId, true);
        waitState(NetworkInfo.DetailedState.CONNECTED);
    }

    @DisplayName("Wifi adapter connect to open network test")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void wifi_6_connectOpenTest() throws InterruptedException {
        wifiConfig = WiFiConfigHelper.getOpenConfig(OPEN_NETWORK_SSID);
        int networkId = adapter.addNetwork(wifiConfig);
        adapter.enableNetwork(networkId, true);
        waitState(NetworkInfo.DetailedState.CONNECTED);
    }

    @DisplayName("Wifi adapter ENABLE/DISABLE WPA2 password network")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void wifi_7_enableDisablePassNetworkTest() throws InterruptedException {
        wifiConfig = WiFiConfigHelper.getWPA2config(NETWORK_SSID, NETWORK_PASS);
        int networkId = adapter.addNetwork(wifiConfig);
        int switchingCount = 0;
        while (switchingCount < 30) {
            adapter.enableNetwork(networkId, true);
            waitState(NetworkInfo.DetailedState.CONNECTED);
            adapter.disableNetwork(networkId);
            waitState(NetworkInfo.DetailedState.DISCONNECTED);
            switchingCount++;
        }
    }

    @DisplayName("Wifi adapter connect to few password network")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void wifi_8_ConnectFewNetworksTest() throws InterruptedException {
        WifiConfiguration wifiConfigRNS_AES_TKIP = WiFiConfigHelper.getWPA2config(NETWORK_SSID, NETWORK_PASS);
        WifiConfiguration wifiConfigRNS_AES = WiFiConfigHelper.getWPA2config(NETWORK_SSID_AES, NETWORK_PASS);
        int networkRNS_TKIP = adapter.addNetwork(wifiConfigRNS_AES_TKIP);
        int networkRNS_AES = adapter.addNetwork(wifiConfigRNS_AES);
        Assert.assertTrue("Networks not added to wifi adapter",
                adapter.getConfiguredNetworks().size() >= 2);
        adapter.enableNetwork(networkRNS_TKIP, true);
        waitState(NetworkInfo.DetailedState.CONNECTED);
        Assert.assertTrue("Wifi adapter has not expected network",
                adapter.getConnectionInfo().getSSID().contains(NETWORK_SSID));
        adapter.enableNetwork(networkRNS_AES, true);
        waitState(NetworkInfo.DetailedState.CONNECTED);
        Assert.assertTrue("Wifi adapter has not expected network",
                adapter.getConnectionInfo().getSSID().contains(NETWORK_SSID_AES));
    }

    @DisplayName("Wifi adapter ON/OFF check enabled network")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void wifi_9_checkOnOffEnabledNetwork() throws InterruptedException {
        wifiConfig = WiFiConfigHelper.getWPA2config(NETWORK_SSID, NETWORK_PASS);
        int networkId = adapter.addNetwork(wifiConfig);
        adapter.enableNetwork(networkId, true);
        waitState(NetworkInfo.DetailedState.CONNECTED);
        adapter.setWifiEnabled(false);
        waitState(WifiManager.WIFI_STATE_DISABLED);
        adapter.setWifiEnabled(true);
        waitState(WifiManager.WIFI_STATE_ENABLED);
        waitState(NetworkInfo.DetailedState.CONNECTED);
    }

    @DisplayName("Wifi adapter downloadFile small file test")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void wifi_10_downloadSmallFileTest() throws IOException, InterruptedException {
        wifiConfig = WiFiConfigHelper.getWPA2config(NETWORK_SSID, NETWORK_PASS);
        int networkId = adapter.addNetwork(wifiConfig);
        adapter.enableNetwork(networkId, true);
        waitState(NetworkInfo.DetailedState.CONNECTED);
        File file = downloadFile(smallFileURL);
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
        waitState(WifiManager.WIFI_STATE_DISABLED);
    }
}
