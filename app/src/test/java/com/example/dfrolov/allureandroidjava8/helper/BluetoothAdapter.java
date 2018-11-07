package com.example.dfrolov.allureandroidjava8.helper;

import com.android.ddmlib.IDevice;
import com.example.dfrolov.allureandroidjava8.allure_implementation.allure.Step;

import org.junit.Assert;




public class BluetoothAdapter {
    private IDevice iDevice;
    private Shell shell;

    private final String EMPTY_VALUE = "0";
    private final String COMMAND_TEMPLATE = "am broadcast -a %s -e %s %s";
    private final String BLUETOOTH_DEVICE_BROADCAST = "BluetoothBroadcastReceiver";

    public static final int FAILED = 0;
    public static final int STATE_OFF = 10;
    public static final int STATE_ON = 12;


    private BluetoothAdapter(IDevice iDevice) {
        this.iDevice = iDevice;
        this.shell = new Shell();
    }

    public static BluetoothAdapter getDefaultAdapter(IDevice device) {
        return new BluetoothAdapter(device);
    }

    public boolean enable() {
        boolean success = false;
        try {
            iDevice.executeShellCommand(String.format(COMMAND_TEMPLATE,
                    BLUETOOTH_DEVICE_BROADCAST,
                    "enable",
                    EMPTY_VALUE),
                    shell);
            success = shell.getDataOutput(Boolean.TYPE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    public boolean disable() {
        boolean success = false;
        try {
            iDevice.executeShellCommand(String.format(COMMAND_TEMPLATE,
                    BLUETOOTH_DEVICE_BROADCAST,
                    "disable",
                    EMPTY_VALUE),
                    shell);
            success = shell.getDataOutput(Boolean.TYPE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    public int getState() {
        int state = FAILED;
        try {
            iDevice.executeShellCommand(String.format(COMMAND_TEMPLATE,
                    BLUETOOTH_DEVICE_BROADCAST,
                    "getState",
                    EMPTY_VALUE),
                    shell);
            state = shell.getDataOutput(Integer.TYPE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return state;
    }

    public boolean createBond(String address) {
        boolean bounded = false;
        try {
            iDevice.executeShellCommand(String.format(COMMAND_TEMPLATE,
                    BLUETOOTH_DEVICE_BROADCAST,
                    "createBond",
                    address),
                    shell);
            bounded = shell.getDataOutput(Boolean.TYPE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bounded;
    }

    public String getAddress() {
        String address = "";
        try {
            iDevice.executeShellCommand("settings get secure bluetooth_address",
                    shell);
            address = shell.getOutput();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return address.toUpperCase();
    }


    @Step
    public void waitForState(int state) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        int currentState = getState();
        while (currentState != state) {
            if (System.currentTimeMillis() - startTime > 10000) {
                Assert.assertFalse("Wait for  state " + state + " more than " + (System.currentTimeMillis() - startTime) + " milseconds", true);
            }
            Thread.sleep(1000);
            currentState = getState();
        }
        AllureLog.info("Wait for bluetooth adapter state [" + state + "]", "Wait seconds [" + (System.currentTimeMillis() - startTime) + "] milseconds");
    }

}
