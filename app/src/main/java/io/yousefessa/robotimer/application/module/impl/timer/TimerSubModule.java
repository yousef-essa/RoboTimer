package io.yousefessa.robotimer.application.module.impl.timer;

import java.time.Instant;

public abstract class TimerSubModule {
    public abstract void handle(final ScreenStatus screenStatus);

    public abstract void startTrackingTime();
    public abstract void resetAndStartTrackingTime();

    public abstract void stopTrackingTime();
    public abstract void resetAndStopTrackingTime();

    public abstract boolean shouldTrackTime();
    public abstract Instant activeTime();

    public static enum Type {
        SCREEN_ON_TIMER,
        SCREEN_OFF_TIMER;
    }
}
