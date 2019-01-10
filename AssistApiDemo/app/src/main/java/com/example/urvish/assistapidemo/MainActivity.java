package com.example.urvish.assistapidemo;

import android.app.assist.AssistContent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public void onProvideAssistContent(AssistContent outContent) {
        super.onProvideAssistContent(outContent);
        String structuredJson=null;
        try {
             structuredJson = new JSONObject()
                    .put("@type", "MusicRecording")
                    .put("@id", "https://www.hbo.com/game-of-thrones")
                    .put("name", "game of thrones")
                    .toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        outContent.setStructuredData(structuredJson);
    }
}
