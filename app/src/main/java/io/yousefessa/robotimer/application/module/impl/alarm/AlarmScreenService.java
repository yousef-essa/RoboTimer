package io.yousefessa.robotimer.application.module.impl.alarm;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.Nullable;
import io.yousefessa.robotimer.TimerActivity;
import io.yousefessa.robotimer.application.RoboTimerApplication;
import io.yousefessa.robotimer.application.context.ContextApplication;
import io.yousefessa.robotimer.application.context.DefaultApplicationContext;

public class AlarmScreenService extends Service {
    @Nullable
    @Override
    public IBinder onBind(final Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        new RoboTimerApplication(new DefaultApplicationContext(TimerActivity.getInstance()),
                new ContextApplication(this)).init();
    }
}
