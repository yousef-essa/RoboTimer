package io.yousefessa.robotimer.application.module.impl.timer.scheduler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.widget.TextView;

public abstract class ScreenTrackerScheduler {
    protected final ScheduledExecutorService executorService;

    public ScreenTrackerScheduler() {
        this.executorService = Executors.newSingleThreadScheduledExecutor();
    }

    abstract SchedulerTask schedulerTask();

    public final void init(final TextView targetView) {
        schedulerTask().schedule(executorService, targetView);
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

            executorService.scheduleWithFixedDelay(() -> {
                handle(view);
            }, initialDelay, delay, timeUnit);
        }

        protected abstract void handle(TextView view);
    }
}
