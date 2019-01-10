package com.example.dfrolov.allureandroidjava8.utils;

import com.android.ddmlib.AdbCommandRejectedException;
import com.android.ddmlib.AndroidDebugBridge;
import com.android.ddmlib.IDevice;
import com.android.ddmlib.InstallException;
import com.android.ddmlib.TimeoutException;

import java.io.IOException;
import java.util.Calendar;

import io.qameta.allure.Step;

public class AdbWrapper {
    private AndroidDebugBridge adb;
    private IDeviceListener listener;


    public AdbWrapper() {
        AndroidDebugBridge.initIfNeeded(true);
        adb = AndroidDebugBridge.createBridge("adb",true);
        listener = new IDeviceListener();
        AndroidDebugBridge.addDeviceChangeListener(listener);
    }

    @Step("Get adb device by udid")
    public IDevice getDevice(String udid) throws InterruptedException {
        IDevice device = null;
        for(IDevice iDevice:listener.waitForDevice()){
            if(iDevice.getSerialNumber().equals(udid)) {
                device=iDevice;
            }
        }
        return device;
    }

    @Step("Reboot adb device")
    public long rebootDevice(IDevice device) throws AdbCommandRejectedException, IOException, TimeoutException, InterruptedException {
        device.reboot(null);
        Thread.sleep(1500);
        long start = Calendar.getInstance().getTimeInMillis();
        listener.waitForDevice();
        long stop = Calendar.getInstance().getTimeInMillis();
        return stop - start;
    }

    @Step("Install package to device")
    public void installPackage(IDevice device,String path) throws InstallException {
        device.installPackage(path,true);
    }

    @Step("Disconnect adb")
    public void disconnect(){
        AndroidDebugBridge.disconnectBridge();
    }
}
