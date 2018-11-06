package com.example.dfrolov.allureandroidjava8.receivers;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.lang.reflect.Method;


public class BluetoothBroadcastReceiver extends BroadcastReceiver {
    private BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();


    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getStringExtra("enable") != null) {
            boolean success = true;
            if(!bluetoothAdapter.isEnabled()){
                bluetoothAdapter.enable();
            }
            this.setResultData(String.valueOf(success));
        }

        if (intent.getStringExtra("disable") != null) {
            this.setResultData(String.valueOf(bluetoothAdapter.disable()));
        }

        if (intent.getStringExtra("getState") != null) {
            this.setResultData(String.valueOf(bluetoothAdapter.getState()));
        }


        if (intent.getStringExtra("createBond") != null) {
            try {
                BluetoothDevice bluetoothDevice = bluetoothAdapter
                        .getRemoteDevice(String.valueOf(
                                intent.getStringExtra("createBond")));
                boolean success = bluetoothDevice.createBond();
                this.setResultData(String.valueOf(success));
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        if (intent.getStringExtra("removeBond") != null) {
            BluetoothDevice bluetoothDevice = bluetoothAdapter
                    .getRemoteDevice(String.valueOf(
                            intent.getStringExtra("removeBond")));
            try {
                Method m = bluetoothDevice.getClass()
                        .getMethod("removeBond", (Class[]) null);
                m.invoke(bluetoothDevice, (Object[]) null);
                this.setResultData(String.valueOf(true));
            } catch (Exception e) {
                this.setResultData(String.valueOf(false));
            }
        }

        if (intent.getStringExtra("getBondState") != null) {
            BluetoothDevice bluetoothDevice = bluetoothAdapter.getRemoteDevice(intent.getStringExtra("getBondState"));
            this.setResultData(String.valueOf(bluetoothDevice.getBondState()));
        }

    }
}
