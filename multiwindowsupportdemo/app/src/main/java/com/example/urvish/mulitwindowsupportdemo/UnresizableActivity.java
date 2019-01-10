package com.example.urvish.mulitwindowsupportdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class UnresizableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unresizable);
    }
}
