package io.yousefessa.robotimer.application.module.impl.timer;

import java.time.Instant;

public class ScreenOnTimerSubModule extends SimpleTimerSubModule {
    @Override
    public void handle(final ScreenStatus screenStatus) {
        if (screenStatus == ScreenStatus.ON && !isLocked()) {
            if (this.activeTime().equals(Instant.EPOCH)) {
                this.resetAndStartTrackingTime();
            } else {
                this.startTrackingTime();
            }
        } else if (screenStatus == ScreenStatus.OFF) {
            this.stopTrackingTime();
        }
    }
}
