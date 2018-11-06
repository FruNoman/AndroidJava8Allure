package com.example.dfrolov.allureandroidjava8.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class WiFiBroadcastReceiver extends BroadcastReceiver {
    private WifiManager wifiManager;
    private static int wifiState;
    private NetworkInfo.DetailedState wifiConnectState;


    @Override
    public void onReceive(Context context, Intent intent) {
        wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        if (intent.getAction().equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {
            wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, -1);
        }
        if (intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
            NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            wifiConnectState = info.getDetailedState();
        }

        if (intent.getStringExtra("setWifiEnabled") != null) {
            this.setResultData(String.valueOf(
                    wifiManager.setWifiEnabled(
                            Boolean.parseBoolean(
                                    intent.getStringExtra("setWifiEnabled")))));
        }

        if (intent.getStringExtra("getState") != null) {
            this.setResultData(String.valueOf(wifiState));
        }

        if (intent.getStringExtra("addNetwork") != null) {
            wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiConfiguration configuration;
            String[] wifiCreds = intent.getStringExtra("addNetwork").split(",");
            for (String s : wifiCreds) {
                Log.i("WIFI !!!!!!!!", s);
            }
            if (wifiCreds.length == 2) {
                configuration = WiFiConfigHelper.getWPA2config(wifiCreds[0], wifiCreds[1]);
            } else {
                configuration = WiFiConfigHelper.getOpenConfig(wifiCreds[0]);
            }
            int networkId = wifiManager.addNetwork(configuration);
            this.setResultData(String.valueOf(networkId));
        }

        if (intent.getStringExtra("enableNetwork") != null) {
            int networkId = Integer.parseInt(intent.getStringExtra("enableNetwork"));
            boolean state = wifiManager.enableNetwork(networkId, true);
            this.setResultData(String.valueOf(state));
        }

        if (intent.getStringExtra("removeNetwork") != null) {
            int networkId = Integer.parseInt(intent.getStringExtra("removeNetwork"));
            boolean success = wifiManager.removeNetwork(networkId);
            this.setResultData(String.valueOf(success));
        }


        if (intent.getStringExtra("getConfiguredNetworks") != null) {
            List<WifiConfiguration> listConfigs = wifiManager.getConfiguredNetworks();
            Gson gson = new GsonBuilder().create();
            Type type = new TypeToken<List<WifiConfiguration>>() {
            }.getType();
            String deviceJson = gson.toJson(listConfigs, type);
            this.setResultData(deviceJson);
        }


        if (intent.getStringExtra("getDetailedState") != null) {
            NetworkInfo.DetailedState state = null;
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();
            if (info != null) {
                state = info.getDetailedState();
            } else {
                state = NetworkInfo.DetailedState.FAILED;
            }
            this.setResultData(state.name());
        }

    }
}
