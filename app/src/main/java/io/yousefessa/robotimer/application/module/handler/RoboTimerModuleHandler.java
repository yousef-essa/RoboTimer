package io.yousefessa.robotimer.application.module.handler;

import java.util.HashMap;
import java.util.Map;

import io.yousefessa.robotimer.application.context.ApplicationContext;
import io.yousefessa.robotimer.application.module.ApplicationModule;
import io.yousefessa.robotimer.application.module.impl.Module;
import io.yousefessa.robotimer.application.module.impl.alarm.DefaultAlarmScreenModule;
import io.yousefessa.robotimer.application.module.impl.timer.DefaultTimerScreenModule;

public class RoboTimerModuleHandler extends ApplicationModuleHandler {
    private final Map<Module, ApplicationModule> moduleMap;

    public RoboTimerModuleHandler(final ApplicationContext mainContext, final ApplicationContext timerContext) {
        this.moduleMap = new HashMap<>();
        this.moduleMap.put(Module.TIMER, new DefaultTimerScreenModule(this, mainContext));
        this.moduleMap.put(Module.ALARM, new DefaultAlarmScreenModule(this, timerContext));
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
