package com.example.urvish.wifidirectdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "WifiDirectDemoLogs";
    private WifiP2pManager wifiP2pManager;
    private WifiP2pManager.Channel mChannel;
    private IntentFilter mIntentFilter;
    WifiP2pDeviceList devices;
    private ArrayAdapter<String> mAdapter;
    private WifiP2pDevice wifiP2pDevice;
    private ArrayList<String> deviceName;
    private Boolean isServer, isClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ListView listView = (ListView) findViewById(R.id.list_view);
        deviceName = new ArrayList<>();
        wifiP2pManager = (WifiP2pManager) getSystemService(WIFI_P2P_SERVICE);
        mChannel = wifiP2pManager.initialize(this, getMainLooper(), new WifiP2pManager.ChannelListener() {
            @Override
            public void onChannelDisconnected() {
                Log.d(TAG, "onChannelDisconnected:chennal disconnected ");
            }
        });
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
        ((Button) findViewById(R.id.btn_server)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (devices == null) {
                    discoverPeers();
                    isServer = true;
                }
            }
        });
        ((Button) findViewById(R.id.btn_client)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (devices == null) {
                    discoverPeers();
                    isClient = true;
                }
            }
        });
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, deviceName);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick: position" + position + " item:" + listView.getItemAtPosition(position));
                for (WifiP2pDevice device : devices.getDeviceList()) {
                    if (device.deviceName.equals(listView.getItemAtPosition(position))) {
                        wifiP2pDevice = device;
                        Log.d(TAG, "onItemClick: " + device.deviceName);
                        connectToDevice(wifiP2pDevice);
                    }
                }
            }
        });
    }

    private void connectToDevice(WifiP2pDevice wifiP2pDevice) {
        WifiP2pConfig config = new WifiP2pConfig();
        config.deviceAddress = wifiP2pDevice.deviceAddress;
        wifiP2pManager.connect(mChannel, config, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(MainActivity.this, "Connection success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int reason) {
                Toast.makeText(MainActivity.this, "Connection Failed due to" + reason, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(WiFiDirectReciever, mIntentFilter);
        discoverPeers();

    }

    private void discoverPeers() {
        wifiP2pManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "onSuccess:discoverPeer success");
                if (isClient)
                    //todo add  asyncTask for client.
                    Log.d(TAG, "onSuccess: isClient");
                else
                    //todo add asynctask for server.
                    Log.d(TAG, "onSuccess: isServer");

            }

            @Override
            public void onFailure(int reason) {
                Log.e(TAG, "onFailure:failed " + reason);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(WiFiDirectReciever);
    }

    BroadcastReceiver WiFiDirectReciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
                int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
                if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                    Toast.makeText(context, "Wifi Direct Available and it is enabled!", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onReceive:wifi direct available");
                } else {
                    ((TextView) findViewById(R.id.textViewStatus)).setText(R.string.wifidirect_enable_msg);
                }
            } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
                requestPeers();
            } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
                // Respond to new connection or disconnections
            } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
                // Respond to this device's wifi state changing
            }
        }
    };

    private void requestPeers() {
        wifiP2pManager.requestPeers(mChannel, new WifiP2pManager.PeerListListener() {
            @Override
            public void onPeersAvailable(WifiP2pDeviceList peers) {
                devices = peers;

                for (WifiP2pDevice device : devices.getDeviceList()) {
                    deviceName.add(device.deviceName);
                }
                mAdapter.notifyDataSetChanged();
                for (WifiP2pDevice wifiP2pDevice : devices.getDeviceList()) {
                    Log.d(TAG, "onPeersAvailable: " + wifiP2pDevice.deviceName);
                }
            }
        });
    }


}
