package io.yousefessa.robotimer.util;

import android.util.Log;

public final class LoggerHandler {
    public static void log(final Object current, final String content) {
        Log.d(current.getClass().getSimpleName(), content);
    }
}
