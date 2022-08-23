package io.yousefessa.robotimer;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import io.yousefessa.robotimer.application.RoboTimerApplication;
import io.yousefessa.robotimer.application.context.ContextApplication;
import io.yousefessa.robotimer.application.context.DefaultApplicationContext;
import io.yousefessa.robotimer.update.handler.factory.DefaultUpdateHandlerFactory;

public class AppService extends Service {
    public static final String NOTIFICATION_ID = "notification_id";

    @Nullable
    @Override
    public IBinder onBind(final Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForeignService();
        } else {
            startForeground(1, new Notification());
        }

        new RoboTimerApplication(new DefaultApplicationContext(TimerActivity.getInstance()),
                new ContextApplication(this)).init();
        new DefaultUpdateHandlerFactory().createUpdaterHandler().init();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startForeignService() {
        final String notificationName = getResources().getString(R.string.notification_name);
        final String notificationDescription = getResources().getString(R.string.notification_description);

        final NotificationChannel channel = new NotificationChannel(NOTIFICATION_ID, notificationName,
                NotificationManager.IMPORTANCE_NONE);
        channel.setLightColor(Color.BLUE);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        final NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);

        final Notification notification =
                new Notification.Builder(this, NOTIFICATION_ID).setContentText(notificationDescription)
                .setSmallIcon(R.drawable.ic_launcher)
                .setPriority(Notification.PRIORITY_MIN)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();

        startForeground(1, notification);
    }
}
