package io.yousefessa.robotimer.application.module.impl.timer.scheduler;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

import android.util.Log;
import android.widget.TextView;
import io.yousefessa.robotimer.R;
import io.yousefessa.robotimer.application.module.impl.Module;
import io.yousefessa.robotimer.application.module.impl.alarm.AlarmScreenModule;
import io.yousefessa.robotimer.application.module.impl.timer.TimerScreenModule;
import io.yousefessa.robotimer.application.module.impl.timer.TimerSubModule;
import io.yousefessa.robotimer.util.ApplicationMainLooper;

public class ScreenOnSchedulerTask extends ScreenTrackerScheduler.SchedulerTask {
    private static final int INITIAL_DELAY = 1;
    private static final int DELAY = INITIAL_DELAY;
    private static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;

    private final TimerScreenModule module;

    public ScreenOnSchedulerTask(final TimerScreenModule module) {
        super(INITIAL_DELAY, DELAY, TIME_UNIT);
        this.module = module;
    }

    @Override
    protected void handle(final TextView view) {
        final TimerSubModule timerModule = module.findSubModule(TimerSubModule.Type.SCREEN_ON_TIMER);

        final String newlyTimerText;
        if (!timerModule.shouldTrackTime()) {
            newlyTimerText = "00:00:00";
        } else {
            final Instant currentTime = Instant.now();
            final Duration activeTime = Duration.between(timerModule.activeTime(), currentTime);

            System.out.println("active time seconds: " + activeTime.getSeconds());

            final Duration elapsedTime = Duration.ofMinutes(18)
                    .minus(activeTime);

            final long minutes = elapsedTime.toMinutes() % 60;
            final long seconds = elapsedTime.getSeconds() % 60;

            final AlarmScreenModule alarmScreenModule = (AlarmScreenModule) module.handler().findModule(Module.ALARM);

            // todo: make this option configurable
            if (seconds <= 50 && !alarmScreenModule.isScreenVisible()) {
                ApplicationMainLooper.instance()
                        .post(this.module::triggerAlarm);
            }

            newlyTimerText = String.format(this.module.context()
                    .getString(R.string.timer_format), elapsedTime.toHours(), minutes, seconds);
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
