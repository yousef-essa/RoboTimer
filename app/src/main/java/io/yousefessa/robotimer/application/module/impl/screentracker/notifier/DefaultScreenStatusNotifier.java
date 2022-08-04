package io.yousefessa.robotimer.application.module.impl.screentracker.notifier;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import io.yousefessa.robotimer.application.module.impl.screentracker.ScreenStatus;
import io.yousefessa.robotimer.application.module.impl.screentracker.ScreenTrackerModule;

public class DefaultScreenStatusNotifier extends ScreenStatusNotifier {
    private final ScreenStatusChangeListener listener;
    private final IntentFilter intentFilter;

    public DefaultScreenStatusNotifier(final ScreenTrackerModule module) {
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
        private final ScreenTrackerModule module;

        public ScreenStatusListener(final ScreenTrackerModule module) {
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
                    module.stopTrackingTime();
                    break;
            }
        }
    }
}
