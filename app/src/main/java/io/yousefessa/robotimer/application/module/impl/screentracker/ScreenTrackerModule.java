package io.yousefessa.robotimer.application.module.impl.screentracker;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import android.util.Log;
import android.widget.TextView;
import io.yousefessa.robotimer.R;
import io.yousefessa.robotimer.application.context.ApplicationContext;
import io.yousefessa.robotimer.application.module.ApplicationModule;
import io.yousefessa.robotimer.util.ApplicationMainLooper;

public class ScreenTrackerModule extends ApplicationModule {
    private final ScheduledExecutorService executorService;
    private final ScreenStatusTracker screenStatusTracker;
    private final ScreenTrackerScheduler screenTrackerScheduler;

    private TextView timerText;
    private ScreenStatus screenStatus = ScreenStatus.OFF;
    private Instant activeTime = Instant.EPOCH;

    public ScreenTrackerModule(final ApplicationContext context) {
        super(context);
        this.executorService = Executors.newSingleThreadScheduledExecutor();
        this.screenStatusTracker = new ScreenStatusTracker(new ScreenStatusListener(this));
        this.screenTrackerScheduler = new ScreenTrackerScheduler(new ScreenSchedulerTask(this));
    }

    @Override
    public void init() {
        // todo: check the current display status
        //  and set the status & stamp accordingly
        this.screenStatus = ScreenStatus.ON;
        this.activeTime = Instant.now();

        this.screenStatusTracker.init(this.context);
        this.timerText = context.findViewById(R.id.textView);

        this.screenTrackerScheduler.init(timerText);
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
            if (this.module.screenStatus == ScreenStatus.OFF) {
                newlyTimerText = "00:00:00";
            } else {
                final Instant currentTime = Instant.now();
                final Duration screenOnDuration = Duration.between(this.module.activeTime, currentTime);

                newlyTimerText = String.format(this.module.context.getString(R.string.timer_format),
                        screenOnDuration.toHours(),
                        screenOnDuration.toMinutes() % 60, screenOnDuration.getSeconds() % 60);
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

    static class ScreenStatusListener extends ScreenStatusTracker.ScreenStatusChangeListener {
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
