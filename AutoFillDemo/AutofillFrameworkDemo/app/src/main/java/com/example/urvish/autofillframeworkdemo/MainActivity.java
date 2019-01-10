package com.example.urvish.autofillframeworkdemo;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void saveEmailAddresses(View view) {
        SharedPreferences.Editor editor=getSharedPreferences("EMAIL_STORAGE",MODE_PRIVATE).edit();
        String primaryEmailAddres=((EditText)findViewById(R.id.primary)).getText().toString();
        String secondaryEmailAddres=((EditText)findViewById(R.id.secondary)).getText().toString();
        editor.putString("PRIMARY",primaryEmailAddres);
        editor.putString("SECONDARY",secondaryEmailAddres);
        editor.apply();
        Toast.makeText(this, "Data Saved!", Toast.LENGTH_SHORT).show();

    }
}
