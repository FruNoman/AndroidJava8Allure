package com.example.dfrolov.allureandroidjava8.core;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.IntentFilter;
import android.util.Log;

import com.example.dfrolov.allureandroidjava8.allure_implementation.allure.Attachment;
import com.example.dfrolov.allureandroidjava8.allure_implementation.allure.Step;
import com.example.dfrolov.allureandroidjava8.receivers.BluetoothReceiver;
import com.example.dfrolov.allureandroidjava8.utils.TestUtils;

import org.junit.Assume;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class BluetoothAdapterAllure extends BaseAdapterAllure{
    private BluetoothAdapter adapter;
    private BluetoothReceiver receiver;
    private String TAG = "Bluetooth Adapter";
    public final static String DESCRIPTION = "Bluetooth adapter states:</br>" +
            "10 - adapter disabled<br>" +
            "12 - adapter enabled<br>";


    public BluetoothAdapterAllure(BluetoothAdapter adapter) {
        super();
        this.adapter = adapter;
        this.receiver = new BluetoothReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        filter.addAction(BluetoothAdapter.ACTION_LOCAL_NAME_CHANGED);
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        appContext.registerReceiver(receiver, filter);
    }


    @Step("Bluetooth adapter force enable")
    public void bluetoothForceEnable() {
        boolean success = true;
        Log.i(TAG,"Bluetooth adapter "+adapter.isEnabled());
        if (!adapter.isEnabled()) {
            success = adapter.enable();
            Log.i(TAG,"Enable bluetooth adapter "+success);
        }
    }

    @Step("Wait for bluetooth adapter state")
    public boolean waitForState(int state) throws InterruptedException {
        boolean success = true;
        long startTime = System.currentTimeMillis();
        while (adapter.getState() != state) {
            Thread.sleep(1000);
            if (System.currentTimeMillis() - startTime > 10000) {
               success = false;
               break;
            }
        }
        return success;
    }

    @Step("Enable bluetooth adapter")
    public boolean enable(){
        boolean success = adapter.enable();
        Log.i(TAG,"Enable bluetooth adapter "+success);
        return success;
    }

    @Step("Disable bluetooth adapter")
    public boolean disable(){
        boolean success = adapter.disable();
        Log.i(TAG,"Disable bluetooth adapter "+success);
        return success;
    }

    @Step("Set bluetooth adapter name")
    public String setName(String name){
        adapter.setName(name);
        Log.i(TAG,"Set bluetooth adapter name "+name);
        return name;
    }

    @Step("Get bluetooth adapter name")
    public String getName(){
        String name = adapter.getName();
        Log.i(TAG,"Get bluetooth adapter name "+name);
        attach(name);
        return name;
    }

    @Step("Set bluetooth adapter default name")
    public String setDefultName() throws IOException {
        String name = mDevice.executeShellCommand(TestUtils.RO_PRODUCT_BOARD);
        adapter.setName(name);
        Log.i(TAG,"Set default bluetooth adapter name "+name);
        return name;
    }

    @Step("Start discovery bluetooth devices")
    public boolean startDiscovery() {
        boolean success = adapter.startDiscovery();
        Log.i(TAG,"Start discovery bluetooth devices "+success);
        return success;
    }


    @Step("Cancel discovery bluetooth devices")
    public boolean cancelDiscovery(){
        boolean success = adapter.cancelDiscovery();
        Log.i(TAG,"Cancel discovery bluetooth devices "+success);
        return success;
    }



    @Step("Get scaned bluetooth devices")
    public List<BluetoothDevice> getScanDevices(){
        List<BluetoothDevice> devices = receiver.getScanDevices();
        Log.i(TAG,"Get scaned bluetooth devices "+Arrays.asList(devices));
        attach(Arrays.asList(devices));
        return devices;
    }

    @Step("Get remote bluetooth device")
    public BluetoothDevice getRemoteDevice(String device){
        BluetoothDevice bleDevice = adapter.getRemoteDevice(device);
        Log.i(TAG,"Get remote bluetooth device "+bleDevice.toString());
        attach(bleDevice);
        return bleDevice;
    }

    @Attachment
    public String attach(Object attach) {
        return attach.toString();
    }

    @Step("Wait time")
    public long waitTime(long time) throws InterruptedException {
        Thread.sleep(time);
        attach(time);
        return time;
    }

    @Step("Wait for bluetooth name change")
    public void waitLocalNameChanged() throws Exception {
        receiver.waitLocalNameChanged();
    }

    @Step("Get bluetooth adapter state")
    public int getState() {
        int state = adapter.getState();
        Log.i(TAG,"Get bluetooth adapter state "+state);
        attach(state);
        return state;
    }

    @Step("Check if bluetooth adapter available")
    public void isAvailable() throws Exception {
        long startTime = System.currentTimeMillis();
        while (adapter.getState() != BluetoothAdapter.STATE_ON) {
            Thread.sleep(1000);
            if (System.currentTimeMillis() - startTime > 3000) {
                Log.i(TAG,"Bluetooth adapter not available");
                Assume.assumeFalse("Bluetooth adapter not available",true);
            }

        }
    }
}
