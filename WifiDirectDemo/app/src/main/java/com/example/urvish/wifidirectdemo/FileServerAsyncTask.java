package com.example.urvish.wifidirectdemo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class FileServerAsyncTask extends AsyncTask<Void,Void,String> {
    private Context mContext;
    private TextView textView;
    private static final String TAG="WifiDirectDemoLog";
    public FileServerAsyncTask(Context mContext, View textView) {
        this.mContext = mContext;
        this.textView = (TextView) textView;
    }

    @Override
    protected String doInBackground(Void... objects) {
        try{
            ServerSocket serverSocket=new ServerSocket(7271);
            Socket socket=serverSocket.accept();

            File file=new File(Environment.getExternalStorageDirectory()+"/"+mContext.getPackageName()+"/wifidirectdemo-"+System.currentTimeMillis()+".jpg");
            File dirs=new File(file.getParent());
            if(!dirs.exists())
                dirs.mkdir();
            file.createNewFile();
            InputStream inputStream=socket.getInputStream();
            serverSocket.close();
            return file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "doInBackground: ",e );
            return null;
        }
    }

    @Override
    protected void onPostExecute(String o) {
        super.onPostExecute(o);
        textView.setText("File Copied -"+o);
        Intent intent= new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse("file://"+o),"image/*");
        mContext.startActivity(intent);

    }
}
