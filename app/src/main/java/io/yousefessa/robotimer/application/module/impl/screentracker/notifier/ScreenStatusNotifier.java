package io.yousefessa.robotimer.application.module.impl.screentracker.notifier;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import io.yousefessa.robotimer.application.context.ApplicationContext;
import io.yousefessa.robotimer.application.module.impl.screentracker.ScreenStatus;

public abstract class ScreenStatusNotifier {
    public final void init(final ApplicationContext context) {
        context.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(final Context context, final Intent intent) {
                ScreenStatusNotifier.this.onReceive(context, intent);
            }
        }, intentFilter());
    }

    abstract IntentFilter intentFilter();

    abstract void onReceive(final Context context, final Intent intent);

    public static abstract class ScreenStatusChangeListener {
        public abstract void handle(final ScreenStatus screenStatus);
    }
}
