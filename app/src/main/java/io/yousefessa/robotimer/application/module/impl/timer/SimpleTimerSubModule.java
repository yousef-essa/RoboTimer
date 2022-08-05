package io.yousefessa.robotimer.application.module.impl.timer;

import java.time.Duration;
import java.time.Instant;

import io.yousefessa.robotimer.application.module.impl.timer.meta.Lockable;

public abstract class SimpleTimerSubModule extends TimerSubModule implements Lockable {
    private Instant activeTime = Instant.EPOCH;
    private Instant pauseTime = Instant.EPOCH;
    private boolean trackTime = false;
    private boolean locked = false;

    @Override
    public void startTrackingTime() {
        this.trackTime = true;

        if (!pauseTime.equals(Instant.EPOCH)) {
            this.activeTime = activeTime.plusSeconds(Duration.between(pauseTime, Instant.now()).getSeconds());
            this.pauseTime = Instant.EPOCH;
        }
    }

    @Override
    public void resetAndStartTrackingTime() {
        this.trackTime = true;
        this.activeTime = Instant.now();
        this.pauseTime = Instant.EPOCH;
    }

    @Override
    public void stopTrackingTime() {
        this.trackTime = false;
        this.pauseTime = Instant.now();
    }

    @Override
    public void resetAndStopTrackingTime() {
        this.trackTime = false;
        this.activeTime = Instant.EPOCH;
        this.pauseTime = Instant.EPOCH;
    }

    @Override
    public void lock() {
        this.locked = true;
    }

    @Override
    public void unlock() {
        this.locked = false;
    }

    @Override
    public boolean isLocked() {
        return locked;
    }

    @Override
    public boolean shouldTrackTime() {
        return trackTime && !locked;
    }

    @Override
    public Instant activeTime() {
        return activeTime;
    }
}
