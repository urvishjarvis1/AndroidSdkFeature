package com.example.urvish.autosizingtextview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView mTextView;
    private SeekBar mSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSeekBar=findViewById(R.id.seekbar);
        mTextView=findViewById(R.id.autosizingtextview);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int height=0;
                height= 300 +(seekBar.getProgress()*5);
                ViewGroup.LayoutParams params=(ViewGroup.LayoutParams) mTextView.getLayoutParams();
                params.height=height;
                mTextView.setLayoutParams(params);

                Log.d("TAG", "onProgressChanged: "+height);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        ((Button)findViewById(R.id.button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,GranualityActivity.class);
                startActivity(intent);
            }
        });
    }
}
