package com.example.urvish.photokeyboardsupportdemo;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v13.view.inputmethod.EditorInfoCompat;
import android.support.v13.view.inputmethod.InputConnectionCompat;
import android.support.v13.view.inputmethod.InputContentInfoCompat;
import android.support.v4.os.BuildCompat;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.util.Log;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;


public class PictureEditText extends AppCompatEditText {
    private KeyBoardInputCallbackListener keyBoardInputCallbackListener;

    public PictureEditText(Context context) {
        super(context);
    }

    public PictureEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PictureEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public InputConnection onCreateInputConnection(final EditorInfo outAttrs) {
        final InputConnection inputConnection=super.onCreateInputConnection(outAttrs);
        EditorInfoCompat.setContentMimeTypes(outAttrs,new String[]{"image/png",
                "image/gif",
                "image/jpeg",
                "image/webp"});
        final InputConnectionCompat.OnCommitContentListener callback=new InputConnectionCompat.OnCommitContentListener() {
            @Override
            public boolean onCommitContent(InputContentInfoCompat inputContentInfo, int flags, Bundle opts) {
                if(Build.VERSION.SDK_INT>=25 && (flags & InputConnectionCompat.INPUT_CONTENT_GRANT_READ_URI_PERMISSION)!=0){
                    try{
                        inputContentInfo.requestPermission();
                    }catch (Exception e){
                        return false;
                    }
                }
                if (keyBoardInputCallbackListener != null) {

                    keyBoardInputCallbackListener.onCommitContent(inputContentInfo, flags, opts);
                    Log.d("TAG", "onCommitContent: "+inputContentInfo.getContentUri()+"desc:"+inputContentInfo.getDescription());
                }
                return true;
            }

        };
        return InputConnectionCompat.createWrapper(inputConnection,outAttrs,callback);
    }
    public interface KeyBoardInputCallbackListener {
        void onCommitContent(InputContentInfoCompat inputContentInfo,
                             int flags, Bundle opts);
    }
    public void setKeyBoardInputCallbackListener(KeyBoardInputCallbackListener keyBoardInputCallbackListener) {
        this.keyBoardInputCallbackListener = keyBoardInputCallbackListener;
    }

}
