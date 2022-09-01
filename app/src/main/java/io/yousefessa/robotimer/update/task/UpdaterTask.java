package io.yousefessa.robotimer.update.task;

import io.yousefessa.applicationupdater.schedule.ScheduleContext;
import io.yousefessa.applicationupdater.schedule.ScheduleTask;
import io.yousefessa.robotimer.util.LoggerHandler;
import org.jetbrains.annotations.NotNull;

public class UpdaterTask implements ScheduleTask {
    @Override
    public void handle(@NotNull final ScheduleContext context) {
        LoggerHandler.log(this, "UpdaterTask#handle: is cancelled? " + context.getCancel());
    }
}
