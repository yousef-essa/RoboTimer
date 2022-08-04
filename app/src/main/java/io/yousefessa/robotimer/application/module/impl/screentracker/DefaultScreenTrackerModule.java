package io.yousefessa.robotimer.application.module.impl.screentracker;

import android.widget.TextView;
import io.yousefessa.robotimer.R;
import io.yousefessa.robotimer.application.context.ApplicationContext;
import io.yousefessa.robotimer.application.module.handler.ApplicationModuleHandler;
import io.yousefessa.robotimer.application.module.impl.screentracker.notifier.DefaultScreenStatusNotifier;
import io.yousefessa.robotimer.application.module.impl.screentracker.notifier.ScreenStatusNotifier;
import io.yousefessa.robotimer.application.module.impl.screentracker.scheduler.DefaultScreenTrackerScheduler;
import io.yousefessa.robotimer.application.module.impl.screentracker.scheduler.ScreenTrackerScheduler;

public class DefaultScreenTrackerModule extends ScreenTrackerModule {
    private final ScreenTrackerScheduler screenTrackerScheduler;
    private final ScreenStatusNotifier screenStatusNotifier;

    public DefaultScreenTrackerModule(final ApplicationModuleHandler handler, final ApplicationContext context) {
        super(handler, context);

        this.screenTrackerScheduler = new DefaultScreenTrackerScheduler(this);
        this.screenStatusNotifier = new DefaultScreenStatusNotifier(this);
    }

    @Override
    public void init() {
        // todo: check the current display status
        //  and set the status & stamp accordingly
        screenStatus(ScreenStatus.ON);
        startTrackingTime();

        System.out.println("context: " + context);

        screenStatusNotifier().init(context);

        final TextView timerText = context.findViewById(R.id.timer_ticking);
        screenTrackerScheduler().init(timerText);
    }

    @Override
    public ScreenTrackerScheduler screenTrackerScheduler() {
        return this.screenTrackerScheduler;
    }

    @Override
    public ScreenStatusNotifier screenStatusNotifier() {
        return this.screenStatusNotifier;
    }
}
