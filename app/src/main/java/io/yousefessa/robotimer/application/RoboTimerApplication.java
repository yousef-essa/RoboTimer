package io.yousefessa.robotimer.application;

import java.util.Collection;
import java.util.Collections;

import io.yousefessa.robotimer.application.context.ApplicationContext;
import io.yousefessa.robotimer.application.module.ApplicationModule;
import io.yousefessa.robotimer.application.module.impl.screentracker.ScreenTrackerModule;

public class RoboTimerApplication extends Application {
    private final ApplicationContext context;

    public RoboTimerApplication(final ApplicationContext context) {
        this.context = context;
    }

    public void init() {
        for (final ApplicationModule module : modules()) {
            module.init();
        }
    }

    @Override
    Collection<ApplicationModule> modules() {
        return Collections.singleton(new ScreenTrackerModule(context));
    }
}
