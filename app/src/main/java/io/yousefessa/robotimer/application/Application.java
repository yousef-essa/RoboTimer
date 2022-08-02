package io.yousefessa.robotimer.application;

import java.util.Collection;

import io.yousefessa.robotimer.application.module.ApplicationModule;

public abstract class Application {
    abstract void init();

    abstract Collection<ApplicationModule> modules();
}
