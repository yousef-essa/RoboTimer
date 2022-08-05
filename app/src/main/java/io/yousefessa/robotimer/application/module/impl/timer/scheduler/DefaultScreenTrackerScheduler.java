package io.yousefessa.robotimer.application.module.impl.timer.scheduler;

import io.yousefessa.robotimer.application.module.impl.timer.TimerScreenModule;

public class DefaultScreenTrackerScheduler extends ScreenTrackerScheduler {
    private final SchedulerTask[] schedulerTask;

    public DefaultScreenTrackerScheduler(final TimerScreenModule module) {
        this.schedulerTask = new SchedulerTask[]{
                new ScreenOnSchedulerTask(module),
                new ScreenOffSchedulerTask(module)
        };
    }

    @Override
    SchedulerTask[] schedulerTasks() {
        return this.schedulerTask;
    }
}
