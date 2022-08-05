package io.yousefessa.robotimer.application.module;

import io.yousefessa.robotimer.application.context.ApplicationContext;
import io.yousefessa.robotimer.application.module.handler.ApplicationModuleHandler;
import io.yousefessa.robotimer.application.module.impl.Module;

public abstract class ApplicationModule {
    protected final ApplicationModuleHandler handler;
    protected final ApplicationContext context;
    private final Module name;

    protected ApplicationModule(final Module name, final ApplicationModuleHandler handler,
            final ApplicationContext context) {
        this.name = name;
        this.handler = handler;
        this.context = context;
    }

    public abstract void init();

    public Module name() {
        return name;
    }

    public ApplicationModuleHandler handler() {
        return handler;
    }
}
