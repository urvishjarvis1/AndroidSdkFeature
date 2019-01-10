package com.example.urvish.canvasdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CustomCanvasActivity extends AppCompatActivity {
    private CustomCanvasView customCanvasView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customCanvasView=new CustomCanvasView(this);
        customCanvasView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        setContentView(customCanvasView);
    }
}
