package com.example.dfrolov.allureandroidjava8;

import android.bluetooth.BluetoothAdapter;
import android.graphics.Bitmap;
import android.support.test.InstrumentationRegistry;

import com.example.dfrolov.allureandroidjava8.allure_implementation.allure.Allure;
import com.example.dfrolov.allureandroidjava8.allure_implementation.allure.Attachment;
import com.example.dfrolov.allureandroidjava8.allure_implementation.allure.Step;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;


public class Some {


    public void doSome(){
        Allure.addAttachment("SOME","FUCK");
        System.out.println("FUCK !");
    }

    @Step
    public void enable(String doSome){
        System.out.println(doSome);
    }

    @Attachment
    public static byte[] takeScrenshot() {
        Bitmap bitmap = InstrumentationRegistry.getInstrumentation().getUiAutomation().takeScreenshot();
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() * 0.7), (int) (bitmap.getHeight() * 0.7), true);
        byte[] bitmapdata = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 20, bos);
            bitmapdata = bos.toByteArray();
            ByteArrayInputStream bs = new ByteArrayInputStream(bitmapdata);
        } catch (Exception e) {

        }
        return bitmapdata;
    }
}
