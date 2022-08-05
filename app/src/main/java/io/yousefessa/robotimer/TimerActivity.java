package io.yousefessa.robotimer;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import io.yousefessa.robotimer.application.module.impl.alarm.AlarmScreenService;

public class TimerActivity extends AppCompatActivity {
    private static TimerActivity instance;
    public static TimerActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        instance = this;

        System.out.println("Starting service now...");
        startService(new Intent(this, AlarmScreenService.class));
    }
}