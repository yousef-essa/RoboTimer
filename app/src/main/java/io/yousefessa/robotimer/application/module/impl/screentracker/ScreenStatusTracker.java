package io.yousefessa.robotimer.application.module.impl.screentracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import io.yousefessa.robotimer.application.context.ApplicationContext;

// create listener for the screen status change
public class ScreenStatusTracker {
    private final ScreenStatusChangeListener statusChangeListener;

    public ScreenStatusTracker(final ScreenStatusChangeListener statusChangeListener) {
        this.statusChangeListener = statusChangeListener;
    }

    public void init(final ApplicationContext context) {
        final IntentFilter intentFilter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        context.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (intent.getAction()) {
                    case Intent.ACTION_SCREEN_OFF:
                        statusChangeListener.handle(ScreenStatus.OFF);
                        break;
                    case Intent.ACTION_SCREEN_ON:
                        statusChangeListener.handle(ScreenStatus.ON);
                        break;
                }
            }
        }, intentFilter);
    }

    public static abstract class ScreenStatusChangeListener {
        public abstract void handle(final ScreenStatus screenStatus);
    }
}
