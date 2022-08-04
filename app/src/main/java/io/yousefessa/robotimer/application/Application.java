package io.yousefessa.robotimer.application;

import io.yousefessa.robotimer.application.module.handler.ApplicationModuleHandler;

public abstract class Application {
    protected Application() {}

    public void init() {
        applicationModuleHandler().init();
    }

    abstract ApplicationModuleHandler applicationModuleHandler();
}
