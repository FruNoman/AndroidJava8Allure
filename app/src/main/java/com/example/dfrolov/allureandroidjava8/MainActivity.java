package com.example.dfrolov.allureandroidjava8;

import android.Manifest;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.wifi.WifiManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.dfrolov.allureandroidjava8.receivers.BluetoothBroadcastReceiver;
import com.example.dfrolov.allureandroidjava8.receivers.WiFiBroadcastReceiver;


public class MainActivity extends AppCompatActivity {
    public ImageView imageView;
    public SurfaceView surfaceView;
    public BluetoothBroadcastReceiver receiver;
    public WiFiBroadcastReceiver wifireceiver;

    public void setImageView(final Bitmap bitmap){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                surfaceView.setVisibility(View.GONE);
                imageView.setVisibility(View.VISIBLE);
                imageView.setImageBitmap(bitmap);
            }
        });
    }

    public void getSurfaceView() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                imageView.setVisibility(View.GONE);
                surfaceView.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //set up full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imageView);
        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);

        receiver = new BluetoothBroadcastReceiver();
        wifireceiver = new WiFiBroadcastReceiver();

        IntentFilter filter = new IntentFilter("BluetoothBroadcastReceiver");
        IntentFilter wifiFilter = new IntentFilter("WiFiBroadcastReceiver");

        wifiFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        wifiFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);

        registerReceiver(receiver,filter);
        registerReceiver(wifireceiver,wifiFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        unregisterReceiver(wifireceiver);
    }

}
