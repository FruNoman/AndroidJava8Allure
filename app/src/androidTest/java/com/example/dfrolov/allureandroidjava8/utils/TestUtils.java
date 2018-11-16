package com.example.dfrolov.allureandroidjava8.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.UiDevice;
import android.widget.ImageView;


import com.example.dfrolov.allureandroidjava8.allure_implementation.allure.Attachment;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.security.SecureRandom;
import java.util.Properties;

public class TestUtils {
    public final static String GET_PROP = "getprop ";
    public final static String RO_PRODUCT_BOARD = GET_PROP + "ro.product.board";
    public final static String RO_BUILD_VERSION_SDK = GET_PROP + "ro.build.version.sdk";
    public final static String RO_BUILD_VERSION_INCREMENTAL = GET_PROP + "ro.build.version.incremental";
    public final static String RO_BUILD_FLAVOR = GET_PROP + "ro.build.flavor";
    public final static String RO_BUILD_PLATFORM = GET_PROP + "ro.board.platform";
    public final static String RO_BUILD_USER = GET_PROP + "ro.build.user";
    public final static String RO_VENDOR_BUILD_DATE = GET_PROP + "ro.vendor.build.date";
    public final static String RO_BUILD_ID = GET_PROP + "ro.build.id";
    public final static String RO_BOOT_SERIALNO = GET_PROP + "ro.boot.serialno";

    private static String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    public static SecureRandom rnd = new SecureRandom();



    public static boolean setDevicePropertyToAllure() {
        Properties prop = new Properties();
        OutputStream output = null;
        boolean success = false;
        try {
            output = new FileOutputStream("/sdcard/allure-results/environment.properties");
            // set the properties value
            UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
            prop.setProperty("Product Board", device.executeShellCommand(RO_PRODUCT_BOARD));
            prop.setProperty("SDK Version", device.executeShellCommand(RO_BUILD_VERSION_SDK));
            prop.setProperty("Build Version", device.executeShellCommand(RO_BUILD_VERSION_INCREMENTAL));
            prop.setProperty("Build Flavors", device.executeShellCommand(RO_BUILD_FLAVOR));
            prop.setProperty("Build User", device.executeShellCommand(RO_BUILD_USER));
            prop.setProperty("Build Date", device.executeShellCommand(RO_VENDOR_BUILD_DATE));
            prop.setProperty("Build ID", device.executeShellCommand(RO_BUILD_ID));
            prop.setProperty("Serial", device.executeShellCommand(RO_BOOT_SERIALNO));
            prop.store(output, null);
            success = true;
        } catch (Exception io) {
            io.printStackTrace();

        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return success;
    }



    public static boolean imageDisplayed(@NonNull ImageView view) {
        Drawable drawable = view.getDrawable();
        boolean hasImage = (drawable != null);

        if (hasImage && (drawable instanceof BitmapDrawable)) {
            hasImage = ((BitmapDrawable) drawable).getBitmap() != null;
        }

        return hasImage;
    }

    public static String randomString(int len ){
        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ )
            sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
        return sb.toString();
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

    public static void setTitle(final Activity act, final String topTitle) {
        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String title=topTitle;
                //if(lastSlash>0)	title+=fileName.substring(lastSlash);
                //else title+=fileName;
                act.setTitle(title);
            }
        });
    }

}
