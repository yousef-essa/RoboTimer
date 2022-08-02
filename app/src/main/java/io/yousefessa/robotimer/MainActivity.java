package io.yousefessa.robotimer;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import io.yousefessa.robotimer.application.RoboTimerApplication;
import io.yousefessa.robotimer.application.context.ApplicationContext;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new RoboTimerApplication(new RoboTimerContext(this)).init();
    }

    static class RoboTimerContext implements ApplicationContext {
        private final Context context;
        private final Activity activity;

        RoboTimerContext(final AppCompatActivity activity) {
            this.context = activity;
            this.activity = activity;
        }

        @Override
        public Intent registerReceiver(@Nullable @org.jetbrains.annotations.Nullable
        final BroadcastReceiver receiver, final IntentFilter filter) {
            return context.registerReceiver(receiver, filter);
        }

        @Override
        public <T extends View> T findViewById(final int id) {
            return this.activity.findViewById(id);
        }

        @NonNull
        @Override
        public String getString(final int resId) {
            return this.context.getString(resId);
        }
    }
}