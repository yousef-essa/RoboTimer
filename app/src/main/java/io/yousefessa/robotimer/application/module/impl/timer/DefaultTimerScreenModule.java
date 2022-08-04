package io.yousefessa.robotimer.application.module.impl.timer;

import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import io.yousefessa.robotimer.R;
import io.yousefessa.robotimer.application.context.ApplicationContext;
import io.yousefessa.robotimer.application.module.handler.ApplicationModuleHandler;

import static android.content.Context.WINDOW_SERVICE;

public class DefaultTimerScreenModule extends TimerScreenModule {
    public DefaultTimerScreenModule(final ApplicationModuleHandler handler, final ApplicationContext context) {
        super(handler, context);
    }

    private WindowManager windowManager;
    private View sourceView;
    private Button hideButton;

    @Override
    public void init() {
        this.windowManager = (WindowManager) context.getSystemService(WINDOW_SERVICE);
        System.out.println("windowManager: " + windowManager);

        this.sourceView = context.getLayoutInflater()
                .inflate(R.layout.activity_timer, null);
        System.out.println("sourceView: " + sourceView);

        this.hideButton = this.sourceView.findViewById(R.id.hide);
        this.hideButton.setOnClickListener(this);
    }

    @Override
    public void showScreen() {
        final WindowManager.LayoutParams windowManagerParams =
                new WindowManager.LayoutParams(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);

        Log.println(Log.DEBUG, "UpdatedTimer", "Showing overlay now!");

        this.windowManager.addView(this.sourceView, windowManagerParams);
    }

    @Override
    public void hideScreen() {
        this.windowManager.removeView(this.sourceView);
    }

    @Override
    public void onClick(final View v) {
        if (!(v instanceof Button)) {
            return;
        }

        final Button button = (Button) v;
        if (this.hideButton.equals(button)) {
            hideScreen();
        }
    }
}