package io.yousefessa.robotimer.application.module.impl.timer.scheduler;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

import android.widget.TextView;
import io.yousefessa.robotimer.BuildConfig;
import io.yousefessa.robotimer.application.module.impl.timer.SimpleTimerSubModule;
import io.yousefessa.robotimer.application.module.impl.timer.TimerScreenModule;
import io.yousefessa.robotimer.application.module.impl.timer.TimerSubModule;

import static io.yousefessa.robotimer.util.ApplicationUtil.debugLog;

public class ScreenOffSchedulerTask extends ScreenTrackerScheduler.SchedulerTask {
    private static final int SCREEN_OFF_SECONDS_INTERVAL = 60;
    private static final int DEBUG_SCREEN_OFF_SECONDS_INTERVAL = 10;

    private static final int SCREEN_OFF_BREAK_SECONDS_INTERVAL = 30;
    private static final int DEBUG_SCREEN_OFF_BREAK_SECONDS_INTERVAL = 5;

    private static final int INITIAL_DELAY = 1;
    private static final int DELAY = INITIAL_DELAY;
    private static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;

    private final TimerScreenModule module;
    private TimerSubModule screenOffModule;

    public ScreenOffSchedulerTask(final TimerScreenModule module) {
        super(INITIAL_DELAY, DELAY, TIME_UNIT);
        this.module = module;
    }

    @Override
    protected void handle(final TextView view) {
        if (screenOffModule == null) {
            this.screenOffModule = module.findSubModule(TimerSubModule.Type.SCREEN_OFF_TIMER);
        }

        if (!screenOffModule.shouldTrackTime()) {
            return;
        }

        final Instant currentTime = Instant.now();
        final Duration duration = Duration.between(screenOffModule.activeTime(), currentTime);

        debugLog("ScreenOffTask", "seconds: " + duration.getSeconds());

        final SimpleTimerSubModule screenOnModule =
                (SimpleTimerSubModule) module.findSubModule(TimerSubModule.Type.SCREEN_ON_TIMER);

        // if the reset has been reached, reset the screenOn time
        if (resetScreenOnTime(screenOnModule)) {
            debugLog("ScreenOffTask", "The reset period has been reached. Resetting the timer now!");
            screenOnModule.resetAndStopTrackingTime();
        }

        // if the alarm break has been reached, hide the alarm screen
        if (alarmBreakReached(screenOnModule)) {
            debugLog("ScreenOffTask", "The alarm break has been reached. Resetting the alarm now!");
            this.module.hideAlarm();
        }
    }

    private boolean alarmBreakReached(final SimpleTimerSubModule screenOnModule) {
        return screenOnModule.isLocked() && isAlarmBreakReached(screenOffModule.activeTime());
    }

    private boolean resetScreenOnTime(final SimpleTimerSubModule screenOnModule) {
        return !screenOnModule.isLocked() && !screenOnModule.activeTime()
                .equals(Instant.EPOCH) && isResetTimeReached(screenOffModule.activeTime());
    }

    private boolean isAlarmBreakReached(final Instant activeTime) {
        final Duration duration = Duration.between(activeTime, Instant.now());

        final int breakSeconds;
        if (BuildConfig.DEBUG) {
            breakSeconds = DEBUG_SCREEN_OFF_BREAK_SECONDS_INTERVAL;
        } else {
            breakSeconds = SCREEN_OFF_BREAK_SECONDS_INTERVAL;
        }

        return duration.getSeconds() >= breakSeconds;
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
