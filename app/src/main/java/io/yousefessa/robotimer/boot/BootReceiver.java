package io.yousefessa.robotimer.boot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import io.yousefessa.robotimer.TimerActivity;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, final Intent intent) {
        final Intent activityIntent = new Intent(context, TimerActivity.class);
        activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(activityIntent);
    }
}
