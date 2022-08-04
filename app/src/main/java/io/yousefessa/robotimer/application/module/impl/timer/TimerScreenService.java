package io.yousefessa.robotimer.application.module.impl.timer;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.Nullable;
import io.yousefessa.robotimer.MainActivity;
import io.yousefessa.robotimer.application.RoboTimerApplication;
import io.yousefessa.robotimer.application.context.ContextApplication;
import io.yousefessa.robotimer.application.context.DefaultApplicationContext;

public class TimerScreenService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();

        new RoboTimerApplication(new DefaultApplicationContext(MainActivity.mainActivity),
                new ContextApplication(this)).init();
    }

    @Nullable
    @Override
    public IBinder onBind(final Intent intent) {
        return null;
    }
}
