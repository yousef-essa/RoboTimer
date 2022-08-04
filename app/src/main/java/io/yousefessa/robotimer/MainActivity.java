package io.yousefessa.robotimer;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import io.yousefessa.robotimer.application.module.impl.timer.TimerScreenService;

public class MainActivity extends AppCompatActivity {
    private static MainActivity instance;
    public static MainActivity getInstance() {
        return instance;
    }

    //    public static int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE = 5469;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        instance = this;

        //        Log.println(Log.DEBUG, "UpdatedTimer", "Attempting to show the top overlay now!");

        //        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
        //            // Show alert dialog to the user saying a separate permission is needed
        //            // Launch the settings activity if the user prefers
        //            Intent myIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package: " +
        //            getPackageName()));
        //            startActivityForResult(myIntent, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE);
        //        }

        //        startService(new Intent(this, TimerScreenService.class));
        //        finish();

        //        startActivity(new Intent(this, TimerActivity.class));

        //        RoboTimerApplication.addActivity(Activity.MAIN, new DefaultApplicationContext(this));

        //        System.out.println("service: " + TimerScreenService.service);
        //
        //        new RoboTimerApplication(
        //                new DefaultApplicationContext(this),
        //                new ContextApplication(TimerScreenService.service)
        //        ).init();

        System.out.println("Starting service now...");
        startService(new Intent(this, TimerScreenService.class));
    }
}