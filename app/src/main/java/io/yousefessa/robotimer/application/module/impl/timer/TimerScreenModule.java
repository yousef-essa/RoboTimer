package io.yousefessa.robotimer.application.module.impl.timer;

import java.time.Instant;

import io.yousefessa.robotimer.application.context.ApplicationContext;
import io.yousefessa.robotimer.application.module.ApplicationModule;
import io.yousefessa.robotimer.application.module.handler.ApplicationModuleHandler;
import io.yousefessa.robotimer.application.module.impl.Module;
import io.yousefessa.robotimer.application.module.impl.timer.notifier.ScreenStatusNotifier;
import io.yousefessa.robotimer.application.module.impl.timer.scheduler.ScreenTrackerScheduler;
import io.yousefessa.robotimer.application.module.impl.alarm.AlarmScreenModule;

public abstract class TimerScreenModule extends ApplicationModule {
    private ScreenStatus screenStatus = ScreenStatus.OFF;
    private Instant activeTime = Instant.EPOCH;
    private boolean trackTime = false;

    public TimerScreenModule(final ApplicationModuleHandler handler, final ApplicationContext context) {
        super(Module.TIMER, handler, context);
    }

    public abstract ScreenTrackerScheduler screenTrackerScheduler();
    public abstract ScreenStatusNotifier screenStatusNotifier();

    public ApplicationContext context() {
        return this.context;
    }

    public void triggerAlarm() {
        resetAndStopTrackingTime();

        final AlarmScreenModule timerScreen = (AlarmScreenModule) this.handler.findModule(Module.ALARM);
        timerScreen.showScreen();
    }

    public void screenStatus(final ScreenStatus screenStatus) {
        this.screenStatus = screenStatus;
    }

    public void startTrackingTime() {
        this.trackTime = true;
        this.activeTime = Instant.now();
    }

    public void resetAndStopTrackingTime() {
        this.trackTime = false;
        this.activeTime = Instant.EPOCH;
    }

    public boolean shouldTrackTime() {
        return trackTime;
    }

    public Instant activeTime() {
        return this.activeTime;
    }
}
