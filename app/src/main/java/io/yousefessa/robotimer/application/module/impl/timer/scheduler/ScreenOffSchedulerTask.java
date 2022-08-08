package io.yousefessa.robotimer.application.module.impl.timer.scheduler;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

import android.widget.TextView;
import io.yousefessa.robotimer.application.module.impl.timer.SimpleTimerSubModule;
import io.yousefessa.robotimer.application.module.impl.timer.TimerScreenModule;
import io.yousefessa.robotimer.application.module.impl.timer.TimerSubModule;

public class ScreenOffSchedulerTask extends ScreenTrackerScheduler.SchedulerTask {
    private static final int SCREEN_OFF_SECONDS_INTERVAL = 1 * 60;
    private static final int SCREEN_OFF_BREAK_SECONDS_INTERVAL = 60 / 2;

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

        final SimpleTimerSubModule screenOnModule =
                (SimpleTimerSubModule) module.findSubModule(TimerSubModule.Type.SCREEN_ON_TIMER);

        if (!isAlarmBreakReached(screenOnModule, screenOffModule.activeTime())) {
            return;
        }

        if (!isResetTimeReached(screenOffModule.activeTime())) {
            return;
        }

        this.module.hideAlarm();
    }

    private boolean isAlarmBreakReached(final SimpleTimerSubModule screenOnModule, final Instant activeTime) {
        final Duration duration = Duration.between(activeTime, Instant.now());

        final int breakSeconds;
        if (BuildConfig.DEBUG) {
            breakSeconds = DEBUG_SCREEN_OFF_BREAK_SECONDS_INTERVAL;
        } else {
            breakSeconds = SCREEN_OFF_BREAK_SECONDS_INTERVAL;
        }

        return !screenOnModule.isLocked() && duration.getSeconds() >= breakSeconds;
    }

    private boolean isResetTimeReached(final Instant activeTime) {
        final Duration duration = Duration.between(activeTime, Instant.now());

        final int resetSeconds;
        if (BuildConfig.DEBUG) {
            resetSeconds = DEBUG_SCREEN_OFF_SECONDS_INTERVAL;
        } else {
            resetSeconds = SCREEN_OFF_SECONDS_INTERVAL;
        }

        return duration.getSeconds() >= resetSeconds;
    }
}
