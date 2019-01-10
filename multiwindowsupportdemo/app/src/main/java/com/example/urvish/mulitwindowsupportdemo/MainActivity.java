package com.example.urvish.mulitwindowsupportdemo;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View multiDisabledMessage = findViewById(R.id.warning_multiwindow_disabled);
        if (!isInMultiWindowMode()) {
            multiDisabledMessage.setVisibility(View.VISIBLE);
        } else {
            multiDisabledMessage.setVisibility(View.GONE);
        }
    }

    public void onStartBasicActivity(View view) {
        startActivity(new Intent(this, BasicActivity.class));

    }

    public void onStartUnresizableClick(View view) {
        Intent intent = new Intent(this, UnresizableActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void onStartAdjacentActivity(View view) {
        Intent intent = new Intent(this, AdjacentActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void onStartCustomConfigurationActivity(View view) {
        startActivity(new Intent(this, CustomConfigurationChangeActivity.class));
    }

    public void onStartMinimumSizeActivity(View view) {
        startActivity(new Intent(this, MinimumSizeActivity.class));
    }

    public void onStartLaunchBoundsActivity(View view) {
        Rect bounds = new Rect(500, 300, 100, 0);

        // Set the bounds as an activity option.
        ActivityOptions options = ActivityOptions.makeBasic();
        options.setLaunchBounds(bounds);

        // Start the LaunchBoundsActivity with the specified options
        Intent intent = new Intent(this, LaunchBoundsActivity.class);
        startActivity(intent, options.toBundle());
    }
}
