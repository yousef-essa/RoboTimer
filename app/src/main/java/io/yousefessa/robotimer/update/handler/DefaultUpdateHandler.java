package io.yousefessa.robotimer.update.handler;

import java.util.concurrent.TimeUnit;

import io.yousefessa.applicationupdater.ApplicationUpdater;
import io.yousefessa.applicationupdater.adapter.ApplicationAdapter;
import io.yousefessa.applicationupdater.destination.Destination;
import io.yousefessa.applicationupdater.schedule.ScheduleTask;
import io.yousefessa.robotimer.AndroidApplication;
import io.yousefessa.robotimer.update.DefaultUpdateFactory;
import io.yousefessa.robotimer.update.UpdateFactory;

public class DefaultUpdateHandler implements UpdateHandler {
    public static final String GITHUB_USERNAME = "yousef-essa";
    public static final String GITHUB_REPOSITORY = "RoboTimer";
    public static final String GITHUB_RELEASE_FILE_NAME = "app-release.apk";

    private final ApplicationUpdater applicationUpdater;

    public DefaultUpdateHandler() {
        final UpdateFactory updateFactory = new DefaultUpdateFactory();

        final Destination destination = updateFactory.createDestination(GITHUB_USERNAME, GITHUB_REPOSITORY, GITHUB_RELEASE_FILE_NAME);
        final ApplicationAdapter adapter = updateFactory.createAdapter();
        final ScheduleTask scheduleTask = updateFactory.createTask();
        final String localVersion = AndroidApplication.getInstance()
                .getLocalVersion();

        this.applicationUpdater = updateFactory.createUpdater(
                destination,
                adapter,
                scheduleTask,
                localVersion,
                1L,
                (60 * 60) * 60, TimeUnit.SECONDS
        );
    }

    @Override
    public void init() {
        this.applicationUpdater.init();
    }
}
