package com.example.urvish.appindexingdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onNewIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String action = intent.getAction();
        String data = intent.getDataString();
        if (Intent.ACTION_VIEW.equalsIgnoreCase(action) && data != null) {
            String blogCategory = data.split("/")[4];
            Toast.makeText(this, "" + blogCategory, Toast.LENGTH_SHORT).show();
            Log.d("TAG", "onNewIntent: " + data + "catagory:" + blogCategory);
        }
    }
}
