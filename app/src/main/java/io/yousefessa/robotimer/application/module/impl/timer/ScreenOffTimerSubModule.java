package io.yousefessa.robotimer.application.module.impl.timer;

public class ScreenOffTimerSubModule extends SimpleTimerSubModule {
    @Override
    public void handle(final ScreenStatus screenStatus) {
        if (screenStatus == ScreenStatus.ON) {
            stopTrackingTime();
        } else if (screenStatus == ScreenStatus.OFF) {
            resetAndStartTrackingTime();
        }
    }
}