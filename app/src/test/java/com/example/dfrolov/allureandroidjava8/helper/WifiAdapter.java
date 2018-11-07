package com.example.dfrolov.allureandroidjava8.helper;

import com.android.ddmlib.IDevice;
import com.google.common.reflect.TypeToken;

import org.junit.Assert;

public class WifiAdapter {
    private IDevice iDevice;
    private Shell shell;

    private final String EMPTY_VALUE = "0";
    private final String COMMAND_TEMPLATE = "am broadcast -a %s -e %s %s";
    private final String WIFI_DEVICE_BROADCAST = "WiFiBroadcastReceiver";

    public static final int WIFI_STATE_DISABLING = 0;
    public static final int WIFI_STATE_DISABLED = 1;
    public static final int WIFI_STATE_ENABLING = 2;
    public static final int WIFI_STATE_ENABLED = 3;
    public static final int WIFI_STATE_UNKNOWN = 4;

    public static final String IDLE = "IDLE";
    public static final String SCANNING = "SCANNING";
    public static final String CONNECTING = "CONNECTING";
    public static final String AUTHENTICATING = "AUTHENTICATING";
    public static final String OBTAINING_IPADDR = "OBTAINING_IPADDR";
    public static final String CONNECTED = "CONNECTED";
    public static final String SUSPENDED = "SUSPENDED";
    public static final String DISCONNECTING = "DISCONNECTING";
    public static final String DISCONNECTED = "DISCONNECTED";
    public static final String FAILED = "FAILED";
    public static final String BLOCKED = "BLOCKED";


    private WifiAdapter(IDevice iDevice) {
        this.iDevice = iDevice;
        this.shell = new Shell();
    }

    public static WifiAdapter getDefaultAdapter(IDevice device) {
        return new WifiAdapter(device);
    }

    public boolean setWifiEnabled(boolean state) {
        boolean success = false;
        try {
            iDevice.executeShellCommand(String.format(COMMAND_TEMPLATE,
                    WIFI_DEVICE_BROADCAST,
                    "setWifiEnabled",
                    state),
                    shell);
            success = shell.getDataOutput(Boolean.TYPE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        AllureLog.info("Switch wifi adapter to state " + state, String.valueOf(success));

        return success;
    }

    public int getState() {
        int state = -1;
        try {
            iDevice.executeShellCommand(String.format(COMMAND_TEMPLATE,
                    WIFI_DEVICE_BROADCAST,
                    "getState",
                    EMPTY_VALUE),
                    shell);
            state = shell.getDataOutput(Integer.TYPE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        AllureLog.info("Get wifi adapter state", String.valueOf(state));

        return state;
    }

    public int addNetwork(String name, String password) {
        int netId = -1;
        try {
            iDevice.executeShellCommand(String.format(COMMAND_TEMPLATE,
                    WIFI_DEVICE_BROADCAST,
                    "addNetwork",
                    name + "," + password),
                    shell);
            netId = shell.getDataOutput(Integer.TYPE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        AllureLog.info("Wifi adapter add network [" + name + " " + password + "]", "network id: " + netId);

        return netId;
    }

    public boolean enableNetwork(int networkId) {
        boolean success = false;
        try {
            iDevice.executeShellCommand(String.format(COMMAND_TEMPLATE,
                    WIFI_DEVICE_BROADCAST,
                    "enableNetwork",
                    networkId),
                    shell);
            success = shell.getDataOutput(Boolean.TYPE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        AllureLog.info("Wifi adapter enable network [" + networkId + "]", success);

        return success;
    }

    public boolean removeNetwork(int networkId) {
        boolean success = false;
        try {
            iDevice.executeShellCommand(String.format(COMMAND_TEMPLATE,
                    WIFI_DEVICE_BROADCAST,
                    "removeNetwork",
                    networkId),
                    shell);
            success = shell.getDataOutput(Boolean.TYPE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        AllureLog.info("Wifi adapter removeNetwork network [" + networkId + "]", success);

        return success;
    }

    public String getDetailedState() {
        String state= FAILED;
        try {
            iDevice.executeShellCommand(String.format(COMMAND_TEMPLATE,
                    WIFI_DEVICE_BROADCAST,
                    "getDetailedState",
                    EMPTY_VALUE),
                    shell);
            state = shell.getDataOutput(new TypeToken<String>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        AllureLog.info("Wifi adapter get detailed state ", state);
        return state;
    }

    public void waitState(int state) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        int currentState = getState();
        while (currentState != state) {
            if (System.currentTimeMillis() - startTime > 10000) {
                Assert.assertFalse("Wait for  state " + state + " more than " + (System.currentTimeMillis() - startTime) + " milseconds", true);
            }
            Thread.sleep(1000);
            currentState = getState();
        }
        AllureLog.info("Wait for wifi adapter state [" + state + "]", "Wait seconds [" + (System.currentTimeMillis() - startTime) + "] milseconds");
    }

    public void waitState(String state) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        String currentState = getDetailedState();
        while (!currentState.equals(state)) {
            if (System.currentTimeMillis() - startTime > 10000) {
                Assert.assertFalse("Wait for detailed state " + state + " more than " + (System.currentTimeMillis() - startTime) + " milseconds", true);
            }
            Thread.sleep(1000);
            currentState = getDetailedState();
        }
        AllureLog.info("Wait for wifi adapter detailed state [" + state + "]", "Wait seconds [" + (System.currentTimeMillis() - startTime) + "] milseconds");
    }

}

