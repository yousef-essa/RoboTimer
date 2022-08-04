package io.yousefessa.robotimer.application.module.impl.timer.notifier;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import io.yousefessa.robotimer.application.module.impl.timer.ScreenStatus;
import io.yousefessa.robotimer.application.module.impl.timer.TimerScreenModule;

public class DefaultScreenStatusNotifier extends ScreenStatusNotifier {
    private final ScreenStatusChangeListener listener;
    private final IntentFilter intentFilter;

    public DefaultScreenStatusNotifier(final TimerScreenModule module) {
        this.listener = new ScreenStatusListener(module);

        this.intentFilter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        this.intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
    }

    @Override
    void onReceive(final Context context, final Intent intent) {
        final ScreenStatus screenStatus;

        switch (intent.getAction()) {
            case Intent.ACTION_SCREEN_ON:
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
            module.screenStatus(screenStatus);

            switch (screenStatus) {
                case ON:
                    module.startTrackingTime();
                    break;
                case OFF:
                    module.resetAndStopTrackingTime();
                    break;
            }
        }
    }
}
