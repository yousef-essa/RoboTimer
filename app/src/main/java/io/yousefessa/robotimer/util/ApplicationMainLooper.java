package io.yousefessa.robotimer.util;

import android.os.Handler;
import android.os.Looper;

public class ApplicationMainLooper {
    private static final ApplicationMainLooper APPLICATION_MAIN_LOOPER = new ApplicationMainLooper();

    public static ApplicationMainLooper instance() {
        return APPLICATION_MAIN_LOOPER;
    }

    private final Handler handler;

    private ApplicationMainLooper() {
        this.handler = new Handler(Looper.getMainLooper());
    }

    public void post(final Runnable runnable) {
        handler.post(runnable);
    }
}
