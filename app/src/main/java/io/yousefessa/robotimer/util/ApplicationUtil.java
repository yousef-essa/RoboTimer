package io.yousefessa.robotimer.util;

import android.app.KeyguardManager;
import android.content.Context;
import android.os.PowerManager;
import android.util.Log;

public class ApplicationUtil {
    public static boolean isDeviceRestricted(final Context context) {
        final KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        return keyguardManager.inKeyguardRestrictedInputMode();
    }

    public static boolean isDevicePoweredOn(final Context context) {
        final PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        return powerManager.isInteractive();
    }

    public static void debugLog(final String tag, final String message) {
        ApplicationMainLooper.instance().post(() -> {
            Log.println(Log.DEBUG, tag, message);
        });
    }
}
