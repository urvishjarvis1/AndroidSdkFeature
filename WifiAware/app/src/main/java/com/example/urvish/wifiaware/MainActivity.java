package com.example.urvish.wifiaware;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.aware.AttachCallback;
import android.net.wifi.aware.DiscoverySessionCallback;
import android.net.wifi.aware.PeerHandle;
import android.net.wifi.aware.PublishConfig;
import android.net.wifi.aware.PublishDiscoverySession;
import android.net.wifi.aware.SubscribeConfig;
import android.net.wifi.aware.SubscribeDiscoverySession;
import android.net.wifi.aware.WifiAwareManager;
import android.net.wifi.aware.WifiAwareSession;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG="WifiAwareLogs";
    private WifiAwareManager wifiAwareManager;
    private IntentFilter intentFilter;
    private WifiAwareSession wifiAwareSession;
    AttachCallback attachCallback = new AttachCallback() {
        @Override
        public void onAttached(WifiAwareSession session) {
            super.onAttached(session);
            wifiAwareSession = session;
        }
    };
    private SubscribeDiscoverySession session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(getPackageManager().hasSystemFeature(PackageManager.FEATURE_WIFI_AWARE)){
            Log.d(TAG, "onCreate: this device supports WifiAware");
            wifiAwareManager=(WifiAwareManager)getSystemService(WIFI_AWARE_SERVICE);
            intentFilter=new IntentFilter(WifiAwareManager.ACTION_WIFI_AWARE_STATE_CHANGED);
            wifiAwareManager.attach(attachCallback, null);
        }else{
            Toast.makeText(this, "Your Device Doesn't support WifiAware", Toast.LENGTH_LONG).show();
            finish();
        }
        //publishing service
        findViewById(R.id.btn_publish_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (wifiAwareSession != null) {
                    PublishConfig config = new PublishConfig.Builder().setServiceName("WIFI_AWARE_SERVICE").build();
                    wifiAwareSession.publish(config, new DiscoverySessionCallback() {
                        @Override
                        public void onPublishStarted(@NonNull PublishDiscoverySession session) {
                            super.onPublishStarted(session);
                            Log.d(TAG, "onPublishStarted: successfully service published");
                            Toast.makeText(MainActivity.this, "Service published! Waiting for Subscriber", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onMessageReceived(PeerHandle peerHandle, byte[] message) {
                            super.onMessageReceived(peerHandle, message);
                            Toast.makeText(MainActivity.this, "Message Received" + message, Toast.LENGTH_SHORT).show();
                        }
                    }, null);
                } else {
                    Toast.makeText(MainActivity.this, "not connected to wifi aware", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //subcribing service
        findViewById(R.id.btn_subscribe_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (wifiAwareSession != null) {
                    SubscribeConfig config = new SubscribeConfig.Builder().setServiceName("WIFI_AWARE_SERVICE").build();
                    wifiAwareSession.subscribe(config, new DiscoverySessionCallback() {
                        @Override
                        public void onSubscribeStarted(@NonNull SubscribeDiscoverySession session) {
                            super.onSubscribeStarted(session);
                            MainActivity.this.session = session;
                            Toast.makeText(MainActivity.this, "Subscriber started", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onServiceDiscovered(PeerHandle peerHandle, byte[] serviceSpecificInfo, List<byte[]> matchFilter) {
                            super.onServiceDiscovered(peerHandle, serviceSpecificInfo, matchFilter);
                            Toast.makeText(MainActivity.this, "Service discoverd" + peerHandle.toString() + "\t sending message now", Toast.LENGTH_SHORT).show();
                            String msg = "hello";
                            session.sendMessage(peerHandle, 1, msg.getBytes());
                        }
                    }, null);
                }
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(wifiAwareChangeReciever,intentFilter);
    }

    private BroadcastReceiver wifiAwareChangeReciever=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(wifiAwareManager.isAvailable()){
                Log.d(TAG, "onReceive: wifiaware! it is available");

            }else {
                Log.i(TAG, "onReceive: wifiaware not available");
            }
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(wifiAwareChangeReciever);
    }

}
