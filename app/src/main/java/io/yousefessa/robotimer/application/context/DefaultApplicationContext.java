package io.yousefessa.robotimer.application.context;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.LayoutInflater;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import org.jetbrains.annotations.NotNull;

public class DefaultApplicationContext implements ApplicationContext {
    private final Context context;
    private final Activity activity;
    private final LayoutInflater layoutInflater;

    public DefaultApplicationContext(final Activity activity) {
        this.context = activity;
        this.activity = activity;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public Intent registerReceiver(
            @Nullable @org.jetbrains.annotations.Nullable final BroadcastReceiver receiver, final IntentFilter filter) {
        return context.registerReceiver(receiver, filter);
    }

    @Override
    public <T extends View> T findViewById(final int id) {
        return this.activity.findViewById(id);
    }

    @NonNull
    @Override
    public String getString(final int resId) {
        return this.context.getString(resId);
    }

    @Override
    public Object getSystemService(final String name) {
        return context.getSystemService(name);
    }

    @NonNull
    @NotNull
    @Override
    public LayoutInflater getLayoutInflater() {
        return layoutInflater;
    }
}
