package io.yousefessa.robotimer.application.module.impl.screentracker;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.widget.TextView;

public class ScreenTrackerScheduler {
    private final SchedulerTask schedulerTask;
    private final ScheduledExecutorService executorService;

    public ScreenTrackerScheduler(final SchedulerTask task) {
        this.schedulerTask = task;
        this.executorService = Executors.newSingleThreadScheduledExecutor();
    }

    public void init(final TextView targetView) {
        executorService.scheduleWithFixedDelay(() -> {
            this.schedulerTask.handle(targetView);
        }, 1, 1, TimeUnit.SECONDS);
    }

    static abstract class SchedulerTask {
        abstract void handle(final TextView view);
    }
}
