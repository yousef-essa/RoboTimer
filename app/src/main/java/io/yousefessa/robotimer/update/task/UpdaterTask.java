package io.yousefessa.robotimer.update.task;

import io.yousefessa.applicationupdater.schedule.ScheduleContext;
import io.yousefessa.applicationupdater.schedule.ScheduleTask;
import org.jetbrains.annotations.NotNull;

public class UpdaterTask implements ScheduleTask {
    @Override
    public void handle(@NotNull final ScheduleContext context) {
        System.out.println("UpdaterTask#handle: is cancelled? " + context.getCancel());


    }
}
