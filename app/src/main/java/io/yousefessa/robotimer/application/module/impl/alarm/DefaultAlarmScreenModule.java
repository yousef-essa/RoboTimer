package io.yousefessa.robotimer.application.module.impl.alarm;

import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import io.yousefessa.robotimer.R;
import io.yousefessa.robotimer.application.context.ApplicationContext;
import io.yousefessa.robotimer.application.module.ApplicationModule;
import io.yousefessa.robotimer.application.module.handler.ApplicationModuleHandler;
import io.yousefessa.robotimer.application.module.impl.Module;
import io.yousefessa.robotimer.application.module.impl.timer.SimpleTimerSubModule;
import io.yousefessa.robotimer.application.module.impl.timer.TimerScreenModule;
import io.yousefessa.robotimer.application.module.impl.timer.TimerSubModule;

import static android.content.Context.WINDOW_SERVICE;

public class DefaultAlarmScreenModule extends AlarmScreenModule {
    public DefaultAlarmScreenModule(final ApplicationModuleHandler handler, final ApplicationContext context) {
        super(handler, context);
    }

    private WindowManager windowManager;
    private View sourceView;
    private Button hideButton;

    private boolean visible = false;

    @Override
    public void init() {
        this.windowManager = (WindowManager) context.getSystemService(WINDOW_SERVICE);
        System.out.println("windowManager: " + windowManager);

        this.sourceView = context.getLayoutInflater()
                .inflate(R.layout.activity_alarm, null);
        System.out.println("sourceView: " + sourceView);

        this.hideButton = this.sourceView.findViewById(R.id.hide_button);
        this.hideButton.setOnClickListener(this);
    }

    @Override
    public void showScreen() {
        if (visible) {
            throw new IllegalStateException("You cannot show a screen that is not visible.");
        }
        this.visible = true;

        final int params;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            params = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            params = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        }

        final WindowManager.LayoutParams windowManagerParams =
                new WindowManager.LayoutParams(params);

        Log.println(Log.DEBUG, "UpdatedTimer", "Showing overlay now!");

        this.windowManager.addView(this.sourceView, windowManagerParams);
    }

    @Override
    public void hideScreen() {
        if (!visible) {
            throw new IllegalStateException("You cannot hide a screen that is not invisible.");
        }
        this.visible = false;

        this.windowManager.removeView(this.sourceView);
        System.out.println("The alarm has been hid.");
    }

    @Override
    public boolean isScreenVisible() {
        return this.visible;
    }

    @Override
    public void onClick(final View v) {
        if (!(v instanceof Button)) {
            return;
        }

        final Button button = (Button) v;
        if (this.hideButton.equals(button)) {
            hideScreen();

            final TimerScreenModule timerModule = (TimerScreenModule) this.handler.findModule(Module.TIMER);
            final SimpleTimerSubModule subModule = (SimpleTimerSubModule) timerModule.findSubModule(TimerSubModule.Type.SCREEN_ON_TIMER);
            subModule.unlock();
            subModule.resetAndStartTrackingTime();
        }
    }
}
