package com.example.urvish.mulitwindowsupportdemo;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class CustomConfigurationChangeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_configuration_change);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        ((TextView)findViewById(R.id.heightandwidth)).setText("Height: "+newConfig.screenHeightDp+" Width: " +newConfig.screenWidthDp);
        if(newConfig.orientation==Configuration.ORIENTATION_LANDSCAPE){
            Toast.makeText(this, "Landscape Mode Activated", Toast.LENGTH_SHORT).show();
            ((TextView)findViewById(R.id.orientation)).setText("Orientation: Landscape ");
        }else if(newConfig.orientation==Configuration.ORIENTATION_PORTRAIT){
            Toast.makeText(this, "Potrait Mode Activated", Toast.LENGTH_SHORT).show();
            ((TextView)findViewById(R.id.orientation)).setText("Orientation: Potrait ");
        }
    }
}
