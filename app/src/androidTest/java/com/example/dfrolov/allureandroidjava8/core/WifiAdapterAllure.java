package com.example.dfrolov.allureandroidjava8.core;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;

import com.example.dfrolov.allureandroidjava8.allure_implementation.allure.Step;

import org.junit.Assert;
import org.junit.Assume;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class WifiAdapterAllure extends BaseAdapterAllure {
    private WifiManager adapter;
    private String TAG = "Wifi adapter";
    private ConnectivityManager connectivityManager;
    public final static String DESCRIPTION = "Wifi adapter states:</br>" +
            "1 - adapter disabled<br>" +
            "3 - adapter enabled<br>";

    public WifiAdapterAllure(WifiManager adapter) {
        super();
        this.adapter = adapter;
        this.connectivityManager = (ConnectivityManager) appContext.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    @Step("Set wifi adapter state")
    public boolean setWifiEnabled(boolean state) {
        boolean success = adapter.setWifiEnabled(state);
        Log.i(TAG, "Set wifi adapter state " + state);
        return success;
    }

    @Step("Get wifi configured networks")
    public List<WifiConfiguration> getConfiguredNetworks() {
        List<WifiConfiguration> networks = adapter.getConfiguredNetworks();
        Log.i(TAG, "Get wifi configured networks");
        return networks;
    }

    @Step("Wifi adapter remove network id")
    public boolean removeNetwork(int netId) {
        boolean success = adapter.removeNetwork(netId);
        Log.i(TAG, "Wifi adapter remove network id "+netId+" "+success);
        return success;
    }

    @Step("Get wifi adapter state")
    public int getWifiState() {
        int state = adapter.getWifiState();
        Log.i(TAG, "Get wifi adapter state " + state);
        return state;
    }

    @Step("Wifi adapter start scanning networks")
    public boolean startScan(){
        boolean success = adapter.startScan();
        Log.i(TAG, "Wifi adapter start scanning networks " + success);
        return success;
    }

    @Step("Wifi adapter get scan results")
    public List<ScanResult> getScanResults() {
        List<ScanResult> results = adapter.getScanResults();
        Log.i(TAG, "Wifi adapter get scan results ");
        return results;
    }

    @Step("Wifi adapter add network configuration")
    public int addNetwork(WifiConfiguration config) {
        int netid = adapter.addNetwork(config);
        Log.i(TAG, "Wifi adapter add network configuration "+config+" with network id "+netid);
        return netid;
    }

    @Step("Wifi adapter enable network")
    public boolean enableNetwork(int netId, boolean attemptConnect) {
        boolean success = adapter.enableNetwork(netId,attemptConnect);
        Log.i(TAG, "Wifi adapter enable network id "+netId);
        return success;
    }

    @Step("Wifi adapter disable network")
    public boolean disableNetwork(int netId){
        boolean success = adapter.disableNetwork(netId);
        Log.i(TAG, "Wifi adapter disable network id "+netId);
        return success;
    }

    @Step("Wifi adapter get wifi info")
    public WifiInfo getConnectionInfo(){
        WifiInfo info = adapter.getConnectionInfo();
        Log.i(TAG, "Wifi adapter get wifi info "+info);
        return info;
    }

    @Step("Download file")
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
        Log.i(TAG, "Download file "+file.getName());
        return file;
    }

    @Step("Get wifi adapter connected state")
    public NetworkInfo.DetailedState getDetailedState(){
        NetworkInfo.DetailedState currentState = NetworkInfo.DetailedState.FAILED;
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info != null) {
            currentState = info.getDetailedState();
        }
        return currentState;
    }

    @Step("Wait for wifi adapter state")
    public boolean waitState(NetworkInfo.DetailedState state) throws InterruptedException {
        boolean success = true;
        long startTime = System.currentTimeMillis();
        NetworkInfo.DetailedState currentState = NetworkInfo.DetailedState.FAILED;
        while (currentState != state) {
            SystemClock.sleep(1000);
            if (System.currentTimeMillis() - startTime > 8000) {
                success = false;
                break;
            }
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();
            if (info != null) {
                currentState = info.getDetailedState();
            }
        }
        Log.i(TAG, "Wait for wifi adapter state "+state+" time "+(System.currentTimeMillis() - startTime));
        return success;
    }


    @Step("Wait for wifi adapter state")
    public boolean waitState(int state) throws InterruptedException {
        boolean success = true;
        int count = 0;
        while (adapter.getWifiState() != state) {
            if (count > 12) {
                success = false;
                break;
            }
            Thread.sleep(1000);
            count++;
        }
        Log.i(TAG, "Wait for wifi adapter state "+state+" time "+count+" sec");
        return success;
    }

    @Step("Check if wifi adapter available")
    public void isAvailable() throws Exception {
        int count = 0;
        while (adapter.getWifiState() != WifiManager.WIFI_STATE_ENABLED) {
            if (count > 10) {
                Log.i(TAG,"Wifi adapter not available");
                Assume.assumeFalse("Wifi adapter not available",true);
            }
            SystemClock.sleep(1000);
            count++;
        }
    }

    @Step("Get wifi password network configuration")
    public WifiConfiguration getWPA2config(String networkName,String networkPass){
        WifiConfiguration wifiRNSconfig = new WifiConfiguration();

        wifiRNSconfig.SSID = "\""+networkName+"\"";
        wifiRNSconfig.preSharedKey = "\""+networkPass+"\"";
        wifiRNSconfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
        wifiRNSconfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
        wifiRNSconfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
        wifiRNSconfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
        wifiRNSconfig.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
        wifiRNSconfig.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
        Log.i(TAG,"Get wifi password network configuration "+wifiRNSconfig);
        return wifiRNSconfig;
    }

    @Step("Get wifi open network configuration")
    public WifiConfiguration getOpenConfig(String networkName){
        WifiConfiguration wifiRNSconfig = new WifiConfiguration();

        wifiRNSconfig.SSID = "\""+networkName+"\"";
        wifiRNSconfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
        wifiRNSconfig.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
        wifiRNSconfig.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
        wifiRNSconfig.allowedAuthAlgorithms.clear();
        wifiRNSconfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
        wifiRNSconfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
        wifiRNSconfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
        wifiRNSconfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
        wifiRNSconfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
        wifiRNSconfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
        Log.i(TAG,"Get wifi password network configuration "+wifiRNSconfig);
        return wifiRNSconfig;
    }

    @Step("Wait time")
    public long waitTime(long time) throws InterruptedException {
        Thread.sleep(time);
        return time;
    }

}
