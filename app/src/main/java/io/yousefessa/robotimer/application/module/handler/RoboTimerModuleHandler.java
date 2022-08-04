package io.yousefessa.robotimer.application.module.handler;

import java.util.HashMap;
import java.util.Map;

import io.yousefessa.robotimer.application.context.ApplicationContext;
import io.yousefessa.robotimer.application.module.ApplicationModule;
import io.yousefessa.robotimer.application.module.impl.Module;
import io.yousefessa.robotimer.application.module.impl.screentracker.DefaultScreenTrackerModule;
import io.yousefessa.robotimer.application.module.impl.timer.DefaultTimerScreenModule;
import io.yousefessa.robotimer.application.module.impl.timer.TimerScreenModule;

public class RoboTimerModuleHandler extends ApplicationModuleHandler {
    private final ApplicationContext mainContext;
    private final ApplicationContext timerContext;

    private final Map<Module, ApplicationModule> moduleMap;

    public RoboTimerModuleHandler(final ApplicationContext mainContext, final ApplicationContext timerContext) {
        this.mainContext = mainContext;
        this.timerContext = timerContext;

        this.moduleMap = new HashMap<>();
        this.moduleMap.put(Module.SCREEN_TRACKER, new DefaultScreenTrackerModule(this, this.mainContext));
        this.moduleMap.put(Module.TIMER_SCREEN, new DefaultTimerScreenModule(this, this.timerContext));
    }

    @Override
    public void init() {
        for (final ApplicationModule module : moduleMap.values()) {
            module.init();
        }
    }

    @Override
    Map<Module, ApplicationModule> modules() {
        return this.moduleMap;
    }
}
