package io.yousefessa.robotimer.application.module;

import io.yousefessa.robotimer.application.context.ApplicationContext;

public abstract class ApplicationModule {
    protected final ApplicationContext context;

    protected ApplicationModule(final ApplicationContext context) {
        this.context = context;
    }

    public abstract void init();
}
