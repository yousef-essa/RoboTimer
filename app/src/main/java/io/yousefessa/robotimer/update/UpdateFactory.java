package io.yousefessa.robotimer.update;

import java.util.concurrent.TimeUnit;

import io.yousefessa.applicationupdater.ApplicationUpdater;
import io.yousefessa.applicationupdater.adapter.ApplicationAdapter;
import io.yousefessa.applicationupdater.destination.Destination;
import io.yousefessa.applicationupdater.schedule.ScheduleTask;

public interface UpdateFactory {
    ApplicationUpdater createUpdater(final Destination destination, final ApplicationAdapter adapter,
            final ScheduleTask task, final String localVersion, final long initialDelay, final long delay,
            final TimeUnit timeUnit);
    Destination createDestination(final String username, final String repository, final String fileName);
    ApplicationAdapter createAdapter();
    ScheduleTask createTask();
}
