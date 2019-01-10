package com.example.urvish.scopeddirectoryaccessdemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.provider.MediaStore;
import android.support.v4.provider.DocumentFile;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int REQCODE = 1;
    private static final String TAG =MainActivity.class.getSimpleName() ;
    private StorageManager mStorageManager;
    private StorageVolume mStoreVolume;
    private Uri mUri;
    private List<Uri> imgs;
    private GridView gridView;
    private GridViewAdapter mGridAdapter;
    private ArrayList<Bitmap> bitmaps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mStorageManager = (StorageManager) getSystemService(STORAGE_SERVICE);
        if (mStorageManager != null) mStoreVolume = mStorageManager.getPrimaryStorageVolume();
        Intent intent = mStoreVolume.createAccessIntent(Environment.DIRECTORY_PICTURES);
        startActivityForResult(intent, REQCODE);
        imgs=new ArrayList<>();
        bitmaps=new ArrayList<>();
        ((Button)findViewById(R.id.button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = mStoreVolume.createAccessIntent(Environment.DIRECTORY_PICTURES);
                startActivityForResult(intent, REQCODE);
            }
        });


    }

    private void getBitMaps() {
        for(Uri uri:imgs){
            try {
                bitmaps.add(MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQCODE) {
            if (resultCode == RESULT_OK) {
                ((Button)findViewById(R.id.button)).setEnabled(false);
                ((TextView) findViewById(R.id.status)).setVisibility(View.GONE);
                DocumentFile folder=DocumentFile.fromTreeUri(this,data.getData());
                Log.d(TAG, "onActivityResult: "+folder.isDirectory());
                Log.d(TAG, "onActivityResult: "+folder.getName());
                if(folder.getName().contains("Pictures")){
                    DocumentFile subFolder=folder.findFile("Screenshots");
                    if (subFolder.exists()){
                        for(DocumentFile file:subFolder.listFiles()){
                            if(!file.isDirectory())
                                imgs.add(file.getUri());
                        }
                        getBitMaps();
                        gridView=(GridView) findViewById(R.id.gridview);
                        mGridAdapter=new GridViewAdapter(this,  bitmaps);
                        gridView.setAdapter(mGridAdapter);
                    }
                }


            } else {
                ((TextView) findViewById(R.id.status)).setVisibility(View.VISIBLE);
                ((TextView) findViewById(R.id.status)).setText("In oder to use this functionality you need to give the permission");
            }
        }
    }

}
