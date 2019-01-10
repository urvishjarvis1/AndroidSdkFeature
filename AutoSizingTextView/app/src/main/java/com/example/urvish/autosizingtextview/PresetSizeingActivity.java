package com.example.urvish.autosizingtextview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

public class PresetSizeingActivity extends AppCompatActivity {
    private TextView mTextView;
    private SeekBar mSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preset_sizeing);
        mSeekBar = findViewById(R.id.seekbar);
        mTextView = findViewById(R.id.autosizingtextview);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int height = 0;
                height =  (seekBar.getProgress() * 5);
                ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) mTextView.getLayoutParams();
                params.height = height;
                mTextView.setLayoutParams(params);

                Log.d("TAG", "onProgressChanged: " + height);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
