package io.yousefessa.robotimer.application.module.handler;

import java.util.Map;

import io.yousefessa.robotimer.application.module.ApplicationModule;
import io.yousefessa.robotimer.application.module.impl.Module;

public abstract class ApplicationModuleHandler {
    public abstract void init();

    public ApplicationModule findModule(final Module module) {
        return this.modules()
                .get(module);
    }

    abstract Map<Module, ApplicationModule> modules();
}
