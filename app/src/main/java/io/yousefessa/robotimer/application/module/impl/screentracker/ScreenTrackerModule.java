package io.yousefessa.robotimer.application.module.impl.screentracker;

import java.time.Instant;

import io.yousefessa.robotimer.application.context.ApplicationContext;
import io.yousefessa.robotimer.application.module.ApplicationModule;
import io.yousefessa.robotimer.application.module.handler.ApplicationModuleHandler;
import io.yousefessa.robotimer.application.module.impl.Module;
import io.yousefessa.robotimer.application.module.impl.screentracker.notifier.ScreenStatusNotifier;
import io.yousefessa.robotimer.application.module.impl.screentracker.scheduler.ScreenTrackerScheduler;
import io.yousefessa.robotimer.application.module.impl.timer.TimerScreenModule;

public abstract class ScreenTrackerModule extends ApplicationModule {
    private ScreenStatus screenStatus = ScreenStatus.OFF;
    private Instant activeTime = Instant.EPOCH;
    private boolean trackTime = false;

    public ScreenTrackerModule(final ApplicationModuleHandler handler, final ApplicationContext context) {
        super(Module.SCREEN_TRACKER, handler, context);
    }

    public abstract ScreenTrackerScheduler screenTrackerScheduler();
    public abstract ScreenStatusNotifier screenStatusNotifier();

    public ApplicationContext context() {
        return this.context;
    }

    public void triggerAlarm() {
        stopTrackingTime();

        final TimerScreenModule timerScreen = (TimerScreenModule) this.handler.findModule(Module.TIMER_SCREEN);
        timerScreen.showScreen();
    }

    public void screenStatus(final ScreenStatus screenStatus) {
        this.screenStatus = screenStatus;
    }

    public void startTrackingTime() {
        this.trackTime = true;
        this.activeTime = Instant.now();
    }

    public void stopTrackingTime() {
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
