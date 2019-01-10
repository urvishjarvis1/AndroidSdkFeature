package com.example.urvish.companiondevicepairingdemo;

import android.bluetooth.BluetoothDevice;
import android.companion.AssociationRequest;
import android.companion.BluetoothDeviceFilter;
import android.companion.CompanionDeviceManager;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private CompanionDeviceManager  manager;
    private AssociationRequest mAssociationRequest;
    private BluetoothDeviceFilter mDeviceFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager=(CompanionDeviceManager) getSystemService(COMPANION_DEVICE_SERVICE);
        mDeviceFilter=new BluetoothDeviceFilter.Builder().setNamePattern(Pattern.compile("S6")).build();
        mAssociationRequest=new AssociationRequest.Builder().addDeviceFilter(mDeviceFilter).setSingleDevice(true).build();
        manager.associate(mAssociationRequest, new CompanionDeviceManager.Callback() {
            @Override
            public void onDeviceFound(IntentSender chooserLauncher) {
                try {
                    startIntentSenderForResult(chooserLauncher,407,null,0,0,0);
                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(CharSequence error) {

            }
        },null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==407){
            if(resultCode==RESULT_OK){
                BluetoothDevice device=data.getParcelableExtra(CompanionDeviceManager.EXTRA_DEVICE);
                device.createBond();
            }
        }
    }
}
