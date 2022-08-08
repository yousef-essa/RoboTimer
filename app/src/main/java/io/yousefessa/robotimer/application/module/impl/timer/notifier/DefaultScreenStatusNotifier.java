package io.yousefessa.robotimer.application.module.impl.timer.notifier;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import io.yousefessa.robotimer.application.module.impl.timer.ScreenStatus;
import io.yousefessa.robotimer.application.module.impl.timer.TimerScreenModule;
import io.yousefessa.robotimer.util.ApplicationUtil;

public class DefaultScreenStatusNotifier extends ScreenStatusNotifier {
    private final ScreenStatusChangeListener listener;
    private final IntentFilter intentFilter;

    public DefaultScreenStatusNotifier(final TimerScreenModule module) {
        this.listener = new ScreenStatusListener(module);

        this.intentFilter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        this.intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        this.intentFilter.addAction(Intent.ACTION_USER_PRESENT);
    }

    @Override
    void onReceive(final Context context, final Intent intent) {
        final ScreenStatus screenStatus;

        switch (intent.getAction()) {
            case Intent.ACTION_SCREEN_ON:
                if (ApplicationUtil.isDeviceRestricted(context)) {
                    return;
                }
            case Intent.ACTION_USER_PRESENT:
                screenStatus = ScreenStatus.ON;
                break;
            case Intent.ACTION_SCREEN_OFF:
            default:
                screenStatus = ScreenStatus.OFF;
        }

        listener.handle(screenStatus);
    }

    @Override
    IntentFilter intentFilter() {
        return this.intentFilter;
    }

    static class ScreenStatusListener extends ScreenStatusNotifier.ScreenStatusChangeListener {
        private final TimerScreenModule module;

        public ScreenStatusListener(final TimerScreenModule module) {
            this.module = module;
        }

        @Override
        public void handle(final ScreenStatus screenStatus) {
            module.handle(screenStatus);
        }
    }
}
