package io.yousefessa.robotimer.application.module.impl.timer;

import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import io.yousefessa.robotimer.R;
import io.yousefessa.robotimer.application.context.ApplicationContext;
import io.yousefessa.robotimer.application.module.ApplicationModule;
import io.yousefessa.robotimer.application.module.handler.ApplicationModuleHandler;
import io.yousefessa.robotimer.application.module.impl.Module;

import static android.content.Context.WINDOW_SERVICE;

public class TimerScreenModule extends ApplicationModule implements View.OnClickListener {
    private WindowManager windowManager;
    private View sourceView;
    private Button hideButton;

    public TimerScreenModule(final ApplicationModuleHandler handler, final ApplicationContext context) {
        super(Module.TIMER_SCREEN, handler, context);
    }

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

    public void showOverlay() {
        final WindowManager.LayoutParams windowManagerParams =
                new WindowManager.LayoutParams(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);

        Log.println(Log.DEBUG, "UpdatedTimer", "Showing overlay now!");

        this.windowManager.addView(this.sourceView, windowManagerParams);
    }

    public void hideOverlay() {
        this.windowManager.removeView(this.sourceView);
    }

    @Override
    public void onClick(final View v) {
        if (!(v instanceof Button)) {
            return;
        }

        final Button button = (Button) v;
        if (this.hideButton.equals(button)) {
            hideOverlay();
        }
    }
}
