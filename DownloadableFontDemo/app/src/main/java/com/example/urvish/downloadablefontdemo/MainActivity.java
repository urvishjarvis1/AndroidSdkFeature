package com.example.urvish.downloadablefontdemo;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v4.provider.FontRequest;
import android.support.v4.provider.FontsContractCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private TextView mTextView;
    FontsContractCompat.FontRequestCallback callback = new FontsContractCompat.FontRequestCallback() {
        @Override
        public void onTypefaceRetrieved(Typeface typeface) {
            super.onTypefaceRetrieved(typeface);
            mTextView.setTypeface(typeface);
        }

        @Override
        public void onTypefaceRequestFailed(int reason) {
            super.onTypefaceRequestFailed(reason);
            Toast.makeText(MainActivity.this, "unable to process the request" + reason, Toast.LENGTH_SHORT).show();
        }
    };
    private Handler mHandler;
    private List<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = findViewById(R.id.textview);
        arrayList = new ArrayList<>();
        arrayList = Arrays.asList(getResources().getStringArray(R.array.family_names));

    }

    public void changefonts(View view) {
        Random random = new Random();
        int pos = random.nextInt(800);
        Log.d("TAG", "onCreate: " + pos);
        FontRequest request = new FontRequest("com.google.android.gms.fonts", "com.google.android.gms", arrayList.get(pos), R.array.com_google_android_gms_fonts_certs);
        FontsContractCompat.requestFont(this, request, callback, getHandler());
    }

    private Handler getHandler() {

        if (mHandler == null) {
            HandlerThread handlerThread = new HandlerThread("fonts");
            handlerThread.start();
            mHandler = new Handler(handlerThread.getLooper());
        }
        return mHandler;

    }
}
