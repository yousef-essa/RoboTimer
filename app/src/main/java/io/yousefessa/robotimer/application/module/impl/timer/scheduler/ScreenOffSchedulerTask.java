package io.yousefessa.robotimer.application.module.impl.timer.scheduler;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

import android.widget.TextView;
import io.yousefessa.robotimer.application.module.impl.timer.TimerScreenModule;
import io.yousefessa.robotimer.application.module.impl.timer.TimerSubModule;

public class ScreenOffSchedulerTask extends ScreenTrackerScheduler.SchedulerTask {
    private static final int INITIAL_DELAY = 1;
    private static final int DELAY = INITIAL_DELAY;
    private static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;

    private final TimerScreenModule module;

    public ScreenOffSchedulerTask(final TimerScreenModule module) {
        super(INITIAL_DELAY, DELAY, TIME_UNIT);
        this.module = module;
    }

    @Override
    protected void handle(final TextView view) {
        final TimerSubModule timerModule = module.findSubModule(TimerSubModule.Type.SCREEN_OFF_TIMER);

        if (!timerModule.shouldTrackTime()) {
            return;
        }

        final Instant currentTime = Instant.now();
        final Duration activeTime = Duration.between(timerModule.activeTime(), currentTime);

        final long minutes = activeTime.toMinutes() % 60;
        final long seconds = activeTime.getSeconds() % 60;

        if (seconds < 5) {
            return;
        }

//        if (minutes < 3) {
//            return;
//        }

        this.module.hideAlarm();
    }
}
