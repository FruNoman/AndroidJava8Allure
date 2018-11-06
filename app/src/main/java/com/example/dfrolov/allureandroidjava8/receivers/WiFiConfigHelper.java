package com.example.dfrolov.allureandroidjava8.receivers;

import android.net.wifi.WifiConfiguration;

public class WiFiConfigHelper {

    public static WifiConfiguration getWPA2config(String networkName,String networkPass){
        WifiConfiguration wifiRNSconfig = new WifiConfiguration();

        wifiRNSconfig.SSID = "\""+networkName+"\"";
        wifiRNSconfig.preSharedKey = "\""+networkPass+"\"";
        wifiRNSconfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
        wifiRNSconfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
        wifiRNSconfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
        wifiRNSconfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
        wifiRNSconfig.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
        wifiRNSconfig.allowedProtocols.set(WifiConfiguration.Protocol.WPA);

        return wifiRNSconfig;
    }

    public static WifiConfiguration getOpenConfig(String networkName){
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

        return wifiRNSconfig;
    }
}
