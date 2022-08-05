package io.yousefessa.robotimer.application.module.impl.alarm;

import android.view.View;
import io.yousefessa.robotimer.application.context.ApplicationContext;
import io.yousefessa.robotimer.application.module.ApplicationModule;
import io.yousefessa.robotimer.application.module.handler.ApplicationModuleHandler;
import io.yousefessa.robotimer.application.module.impl.Module;

public abstract class AlarmScreenModule extends ApplicationModule implements View.OnClickListener {
    public AlarmScreenModule(final ApplicationModuleHandler handler, final ApplicationContext context) {
        super(Module.ALARM, handler, context);
    }

    public abstract void showScreen();
    public abstract void hideScreen();

    public abstract boolean isScreenVisible();
}
