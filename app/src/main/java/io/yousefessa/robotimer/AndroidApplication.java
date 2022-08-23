package io.yousefessa.robotimer;

import android.app.Application;

public class AndroidApplication extends Application {
    private static AndroidApplication instance;
    private static final String LOCAL_VERSION = "0.1.0" + (BuildConfig.BUILD_TYPE.equals("release") ? "" :
            "-" + BuildConfig.BUILD_TYPE);

    public static AndroidApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
    }

    public String getLocalVersion() {
        return LOCAL_VERSION;
    }
}
