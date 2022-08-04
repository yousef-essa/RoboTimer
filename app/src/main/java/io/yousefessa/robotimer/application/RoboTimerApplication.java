package io.yousefessa.robotimer.application;

import io.yousefessa.robotimer.application.context.ApplicationContext;
import io.yousefessa.robotimer.application.module.handler.ApplicationModuleHandler;
import io.yousefessa.robotimer.application.module.handler.RoboTimerModuleHandler;

// todo: make this closed for modifications but open for extension
public class RoboTimerApplication extends Application {
    private final ApplicationModuleHandler applicationModuleHandler;

    public RoboTimerApplication(final ApplicationContext mainContext, final ApplicationContext timerContext) {
        this.applicationModuleHandler = new RoboTimerModuleHandler(mainContext, timerContext);
    }

    public void init() {
        super.init();
    }

    @Override
    ApplicationModuleHandler applicationModuleHandler() {
        return this.applicationModuleHandler;
    }
}
