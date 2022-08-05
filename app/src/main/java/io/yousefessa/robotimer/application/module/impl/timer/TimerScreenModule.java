package io.yousefessa.robotimer.application.module.impl.timer;

import java.util.Map;

import io.yousefessa.robotimer.application.context.ApplicationContext;
import io.yousefessa.robotimer.application.module.ApplicationModule;
import io.yousefessa.robotimer.application.module.handler.ApplicationModuleHandler;
import io.yousefessa.robotimer.application.module.impl.Module;
import io.yousefessa.robotimer.application.module.impl.timer.notifier.ScreenStatusNotifier;
import io.yousefessa.robotimer.application.module.impl.timer.scheduler.ScreenTrackerScheduler;

public abstract class TimerScreenModule extends ApplicationModule {
    private ScreenStatus screenStatus = ScreenStatus.OFF;

    public TimerScreenModule(final ApplicationModuleHandler handler, final ApplicationContext context) {
        super(Module.TIMER, handler, context);
    }

    public abstract void handle(final ScreenStatus screenStatus);

    public abstract void triggerAlarm();
    public abstract void hideAlarm();

    public abstract ScreenTrackerScheduler screenTrackerScheduler();

    public abstract ScreenStatusNotifier screenStatusNotifier();

    abstract Map<TimerSubModule.Type, TimerSubModule> modules();

    public TimerSubModule findSubModule(final TimerSubModule.Type type) {
        return modules().get(type);
    }

    protected void screenStatus(final ScreenStatus screenStatus) {
        this.screenStatus = screenStatus;
    }

    public ScreenStatus screenStatus() {
        return screenStatus;
    }

    public ApplicationContext context() {
        return this.context;
    }
}
