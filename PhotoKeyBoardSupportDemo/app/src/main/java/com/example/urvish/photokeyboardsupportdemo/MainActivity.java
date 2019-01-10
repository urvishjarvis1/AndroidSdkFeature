package com.example.urvish.photokeyboardsupportdemo;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v13.view.inputmethod.InputContentInfoCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PictureEditText pictureEditText = findViewById(R.id.imageedittext);
        pictureEditText.setKeyBoardInputCallbackListener(new PictureEditText.KeyBoardInputCallbackListener() {
            @Override
            public void onCommitContent(InputContentInfoCompat inputContentInfo, int flags, Bundle opts) {

                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(MainActivity.this.getContentResolver(), inputContentInfo.getContentUri());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (bitmap != null) ((ImageView) findViewById(R.id.image)).setImageBitmap(bitmap);
                else
                    ((ImageView) findViewById(R.id.image)).setImageResource(R.drawable.ic_launcher_background);

            }
        });

    }
}
