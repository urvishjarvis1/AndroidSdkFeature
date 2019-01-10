package com.example.volansys.alarmmanager;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.AlarmClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import static android.provider.AlarmClock.ACTION_SET_TIMER;

public class MainActivity extends AppCompatActivity {
    private static final String ACTION_NOTIFY = "com.example.volansys.alarmmanager.ACTION_NOTIFY";
    final int NOTIFICATION_ID = 1;
    private ToggleButton mtoggleAlarm;
    private String channel = "Wake Up";
    private String channelId = "wakupAlarm";
    private String channelDesc = "WakeUP";
    private NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mtoggleAlarm = findViewById(R.id.toggleAlarm);
        final AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this, MyReceiver.class);
        final PendingIntent pendingIntent = PendingIntent.getBroadcast(this, NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        mtoggleAlarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //createNotification("Alarm On!!",getApplicationContext());
                    long stime = SystemClock.elapsedRealtime() + 10 * 1000;
                    long dtime = 10 * 1000;
                    Log.d("str", "stime:" + stime + "  dtime:" + dtime);
                    alarmManager.setAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME, stime, pendingIntent);
                    Toast.makeText(MainActivity.this, "Alarm set", Toast.LENGTH_SHORT).show();
                } else {

                    alarmManager.cancel(pendingIntent);
                    Toast.makeText(MainActivity.this, "Alarm off", Toast.LENGTH_SHORT).show();
                }
            }
        });
        if (getIntent().getAction().equals(ACTION_SET_TIMER)) {
            intent = getIntent();
            int timer = intent.getIntExtra(AlarmClock.EXTRA_LENGTH, 0);
            Log.d("TAG", "timer  " +timer);
            alarmManager.setExact(AlarmManager.ELAPSED_REALTIME, timer*1000, pendingIntent);
        }
    }


}

