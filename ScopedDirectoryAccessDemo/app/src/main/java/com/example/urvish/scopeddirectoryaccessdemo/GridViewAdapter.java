package com.example.urvish.scopeddirectoryaccessdemo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by urvish on 20/1/18.
 */

public class GridViewAdapter extends BaseAdapter {
    private ImageView mImageView;
    public GridViewAdapter(Activity activity,ArrayList<Bitmap> mImagIds) {
        this.mImagIds=mImagIds;
        this.activity=activity;
    }

    private static ArrayList<Bitmap> mImagIds;
    Activity activity;

    @Override
    public int getCount() {
        return mImagIds.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int pos) {
        return 0;
    }

    @Override
    public View getView(int pos, View view, ViewGroup viewGroup) {
        LayoutInflater inflater=activity.getLayoutInflater();
        View v=inflater.inflate(R.layout.gridview_data,null,true);
        mImageView=(ImageView)v.findViewById(R.id.con_img);
        mImageView.setImageBitmap(mImagIds.get(pos));
        mImageView.setAdjustViewBounds(true);
        mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        //return the imageview for parent view
        return v;

    }
}
