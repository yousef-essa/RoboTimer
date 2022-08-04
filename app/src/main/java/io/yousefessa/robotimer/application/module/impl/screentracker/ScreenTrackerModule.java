package io.yousefessa.robotimer.application.module.impl.screentracker;

import java.time.Duration;
import java.time.Instant;

import android.util.Log;
import android.widget.TextView;
import io.yousefessa.robotimer.R;
import io.yousefessa.robotimer.application.context.ApplicationContext;
import io.yousefessa.robotimer.application.module.ApplicationModule;
import io.yousefessa.robotimer.application.module.handler.ApplicationModuleHandler;
import io.yousefessa.robotimer.application.module.impl.Module;
import io.yousefessa.robotimer.application.module.impl.timer.TimerScreenModule;
import io.yousefessa.robotimer.util.ApplicationMainLooper;

public class ScreenTrackerModule extends ApplicationModule {
    private final ScreenStatusNotifier screenStatusNotifier;
    private final ScreenTrackerScheduler screenTrackerScheduler;

    private ScreenStatus screenStatus = ScreenStatus.OFF;
    private Instant activeTime = Instant.EPOCH;

    public ScreenTrackerModule(final ApplicationModuleHandler handler, final ApplicationContext context) {
        super(Module.SCREEN_TRACKER, handler, context);
        this.screenStatusNotifier = new ScreenStatusNotifier(new ScreenStatusListener(this));
        this.screenTrackerScheduler = new ScreenTrackerScheduler(new ScreenSchedulerTask(this));
    }

    @Override
    public void init() {
        // todo: check the current display status
        //  and set the status & stamp accordingly
        this.screenStatus = ScreenStatus.ON;
        this.activeTime = Instant.now();

        System.out.println("context: " + context);

        this.screenStatusNotifier.init(context);
        final TextView timerText = context.findViewById(R.id.timer_ticking);

        this.screenTrackerScheduler.init(timerText);
    }

    public void triggerTimer() {
        final TimerScreenModule timerScreen = (TimerScreenModule) this.handler.findModule(Module.TIMER_SCREEN);

        screenStatus(ScreenStatus.OFF);
        resetActiveTime();

        timerScreen.showOverlay();
    }

    public ScreenStatus screenStatus() {
        return screenStatus;
    }

    public void screenStatus(final ScreenStatus screenStatus) {
        this.screenStatus = screenStatus;
    }

    public void startActiveTime() {
        this.activeTime = Instant.now();
    }

    public void resetActiveTime() {
        this.activeTime = Instant.EPOCH;
    }

    static class ScreenSchedulerTask extends ScreenTrackerScheduler.SchedulerTask {
        private final ScreenTrackerModule module;

        public ScreenSchedulerTask(final ScreenTrackerModule module) {
            this.module = module;
        }

        @Override
        void handle(final TextView view) {
            final String newlyTimerText;
            if (this.module.screenStatus() == ScreenStatus.OFF) {
                newlyTimerText = "00:00:00";
            } else {
                final Instant currentTime = Instant.now();
                final Duration screenOnDuration = Duration.between(this.module.activeTime, currentTime);

                final long minutes = screenOnDuration.toMinutes() % 60;
                final long seconds = screenOnDuration.getSeconds() % 60;

                // todo: make this option configurable
                if (seconds > 2) {
                    ApplicationMainLooper.instance()
                            .post(this.module::triggerTimer);
                }

                newlyTimerText = String.format(this.module.context.getString(R.string.timer_format),
                        screenOnDuration.toHours(), minutes, seconds);
            }

            ApplicationMainLooper.instance()
                    .post(() -> {
                        try {
                            Log.println(Log.DEBUG, "UpdatedTimer", "Updated timer text with " + newlyTimerText);
                            view.setText(newlyTimerText);
                        } catch (final Exception exception) {
                            exception.printStackTrace();
                        }
                    });
        }
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
                    module.startActiveTime();
                    break;
                case OFF:
                    module.resetActiveTime();
                    break;
            }
        }
    }
}
