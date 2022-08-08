package io.yousefessa.robotimer.application.module.impl.timer.scheduler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.widget.TextView;

import static io.yousefessa.robotimer.util.ApplicationUtil.debugLog;

public abstract class ScreenTrackerScheduler {
    private final ScheduledExecutorService executorService = Executors
            .newSingleThreadScheduledExecutor();

    abstract SchedulerTask[] schedulerTasks();

    public final void init(final TextView targetView) {
        for (final SchedulerTask task : schedulerTasks()) {
            task.schedule(executorService, targetView);
        }
    }

    public static abstract class SchedulerTask {
        private final long initialDelay, delay;
        private final TimeUnit timeUnit;

        protected SchedulerTask() {
            this(-1, -1, TimeUnit.MICROSECONDS);
        }

        protected SchedulerTask(final long initialDelay, final long delay, final TimeUnit timeUnit) {
            this.initialDelay = initialDelay;
            this.delay = delay;
            this.timeUnit = timeUnit;
        }

        public void schedule(final ExecutorService executorService, final TextView view) {
            executorService.execute(() -> {
                handle(view);
            });
        }

        public void schedule(final ScheduledExecutorService executorService, final TextView view) {
            if (initialDelay == -1 || delay == -1) {
                throw new UnsupportedOperationException("This task cannot run periodical schedules.");
            }

            debugLog("ScreenTrackerScheduler",
                    "Scheduling " + this.getClass().getSimpleName() + " for " + initialDelay + ", " + delay + ", " + timeUnit);
            executorService.scheduleWithFixedDelay(() -> {
                try {
                    handle(view);
                } catch (final Exception exception) {
                    exception.printStackTrace();
                }
            }, initialDelay, delay, timeUnit);
        }

        protected abstract void handle(TextView view);
    }
}
