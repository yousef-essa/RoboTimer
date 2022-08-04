package io.yousefessa;

import android.app.Application;

public class AndroidApplication extends Application {
    private static AndroidApplication instance;

    public static AndroidApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        //        System.out.println("Starting service now...");
        //        startService(new Intent(this, TimerScreenService.class));
    }
}
