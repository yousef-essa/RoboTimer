package io.yousefessa.robotimer.update;

import java.util.concurrent.TimeUnit;

import io.yousefessa.applicationupdater.ApplicationUpdater;
import io.yousefessa.applicationupdater.SimpleApplicationUpdater;
import io.yousefessa.applicationupdater.adapter.ApplicationAdapter;
import io.yousefessa.applicationupdater.destination.Destination;
import io.yousefessa.applicationupdater.destination.GitHubReleaseDestination;
import io.yousefessa.applicationupdater.schedule.ScheduleTask;
import io.yousefessa.robotimer.update.adapter.UpdaterAdapter;
import io.yousefessa.robotimer.update.task.UpdaterTask;

public class DefaultUpdateFactory implements UpdateFactory {
    @Override
    public ApplicationUpdater createUpdater(final Destination destination, final ApplicationAdapter adapter,
            final ScheduleTask task, final String localVersion, final long initialDelay, final long delay,
            final TimeUnit timeUnit) {
        return new SimpleApplicationUpdater(
                destination,
                adapter,
                task,
                localVersion,
                initialDelay,
                delay,
                timeUnit
        );
    }

    @Override
    public Destination createDestination(final String username, final String repository, final String fileName) {
        return new GitHubReleaseDestination(username, repository, fileName);
    }

    @Override
    public ApplicationAdapter createAdapter() {
        return new UpdaterAdapter();
    }

    @Override
    public ScheduleTask createTask() {
        return new UpdaterTask();
    }
}
