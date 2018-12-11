package com.example.dfrolov.allureandroidjava8.receivers;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class BluetoothReceiver extends BroadcastReceiver {
    private static int bluetoothStatus;
    private boolean nameStatus;
    public static List<BluetoothDevice> scanDevices;
    private int pin = 0;

    public BluetoothReceiver() {
        scanDevices = new ArrayList<>();
    }

    public int getPin() {
        return pin;
    }

    public List<BluetoothDevice> getScanDevices() {
        return scanDevices;
    }


    public void waitLocalNameChanged() throws Exception {
        int count = 0;
        while (!nameStatus) {
            try {
                if (count > 15) {
                    throw new Exception("The current bluetooth name has no changed");
                } else {
                    Thread.sleep(1000);
                    count++;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        nameStatus = false;
        final String action = intent.getAction();
        if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
            final int state = intent.getIntExtra(
                    BluetoothAdapter.EXTRA_STATE,
                    BluetoothAdapter.ERROR);
            bluetoothStatus = state;
        }
        if (action.equals(BluetoothAdapter.ACTION_LOCAL_NAME_CHANGED)) {
            nameStatus = true;
        }
        if (action.equals(BluetoothDevice.ACTION_FOUND)) {
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            Log.i("BluetoothReceiver", "Found bluetooth device \"" + device.getName() + "\" with MAC \"" + device.getAddress() + "\"");
            scanDevices.add(device);
        }
        if (action.equals(BluetoothDevice.ACTION_PAIRING_REQUEST)) {
            pin = intent.getIntExtra(BluetoothDevice.EXTRA_PAIRING_KEY, 0);
            Log.i("BluetoothReceiver", "Pairing request with PIN [" + pin + "]");
        }
    }


}
