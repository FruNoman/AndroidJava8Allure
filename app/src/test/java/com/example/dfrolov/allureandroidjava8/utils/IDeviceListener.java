package com.example.dfrolov.allureandroidjava8.utils;

import com.android.ddmlib.AndroidDebugBridge;
import com.android.ddmlib.IDevice;

import java.util.ArrayList;
import java.util.List;

public class IDeviceListener implements AndroidDebugBridge.IDeviceChangeListener {
    private List<IDevice> deviceList;

    public IDeviceListener() {
        this.deviceList = new ArrayList<>();
    }

    @Override
    public void deviceConnected(IDevice device) {
        deviceList.add(device);
    }

    @Override
    public void deviceDisconnected(IDevice device) {
        deviceList.remove(device);
    }

    @Override
    public void deviceChanged(IDevice device, int changeMask) {

    }

    public List<IDevice> waitForDevice() throws InterruptedException {
        while (deviceList.size()<=0){
            Thread.sleep(1000);
        }
        return deviceList;
    }
}
