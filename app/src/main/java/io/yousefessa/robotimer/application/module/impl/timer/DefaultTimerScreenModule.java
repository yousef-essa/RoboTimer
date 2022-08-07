package io.yousefessa.robotimer.application.module.impl.timer;

import java.util.HashMap;
import java.util.Map;

import android.widget.TextView;
import io.yousefessa.robotimer.R;
import io.yousefessa.robotimer.application.context.ApplicationContext;
import io.yousefessa.robotimer.application.module.handler.ApplicationModuleHandler;
import io.yousefessa.robotimer.application.module.impl.Module;
import io.yousefessa.robotimer.application.module.impl.alarm.AlarmScreenModule;
import io.yousefessa.robotimer.application.module.impl.timer.notifier.DefaultScreenStatusNotifier;
import io.yousefessa.robotimer.application.module.impl.timer.notifier.ScreenStatusNotifier;
import io.yousefessa.robotimer.application.module.impl.timer.scheduler.DefaultScreenTrackerScheduler;
import io.yousefessa.robotimer.application.module.impl.timer.scheduler.ScreenTrackerScheduler;

import static io.yousefessa.robotimer.util.ApplicationUtil.isDevicePoweredOn;

public class DefaultTimerScreenModule extends TimerScreenModule {
    private final ScreenTrackerScheduler screenTrackerScheduler;
    private final ScreenStatusNotifier screenStatusNotifier;

    private final Map<TimerSubModule.Type, TimerSubModule> subModuleMap;

    public DefaultTimerScreenModule(final ApplicationModuleHandler handler, final ApplicationContext context) {
        super(handler, context);

        this.screenTrackerScheduler = new DefaultScreenTrackerScheduler(this);
        this.screenStatusNotifier = new DefaultScreenStatusNotifier(this);

        this.subModuleMap = new HashMap<>();
        this.subModuleMap.put(TimerSubModule.Type.SCREEN_ON_TIMER, new ScreenOnTimerSubModule());
        this.subModuleMap.put(TimerSubModule.Type.SCREEN_OFF_TIMER, new ScreenOffTimerSubModule());
    }

    @Override
    public void handle(final ScreenStatus screenStatus) {
        this.screenStatus(screenStatus);

        for (final TimerSubModule timerSubModule : subModuleMap.values()) {
            timerSubModule.handle(screenStatus);
        }
    }

    @Override
    public void init() {
        if (isDevicePoweredOn(context.getLayoutInflater()
                .getContext())) {
            handle(ScreenStatus.ON);
        }

        System.out.println("context: " + context);

        screenStatusNotifier().init(context);

        final TextView timerText = context.findViewById(R.id.timer_text);
        screenTrackerScheduler().init(timerText);
    }

    @Override
    public void triggerAlarm() {
        final SimpleTimerSubModule timerSubModule = (SimpleTimerSubModule) this.findSubModule(TimerSubModule.Type.SCREEN_ON_TIMER);
        timerSubModule.resetAndStopTrackingTime();
        timerSubModule.lock();

        final AlarmScreenModule timerScreen = (AlarmScreenModule) this.handler.findModule(Module.ALARM);
        timerScreen.showScreen();
    }

    @Override
    public void hideAlarm() {
        final SimpleTimerSubModule timerSubModule = (SimpleTimerSubModule) this.findSubModule(TimerSubModule.Type.SCREEN_ON_TIMER);
        timerSubModule.unlock();

        final AlarmScreenModule timerScreen = (AlarmScreenModule) this.handler.findModule(Module.ALARM);
        timerScreen.hideScreen();
    }

    @Override
    public ScreenTrackerScheduler screenTrackerScheduler() {
        return this.screenTrackerScheduler;
    }

    @Override
    public ScreenStatusNotifier screenStatusNotifier() {
        return this.screenStatusNotifier;
    }

    @Override
    Map<TimerSubModule.Type, TimerSubModule> modules() {
        return this.subModuleMap;
    }
}
