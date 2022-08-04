package io.yousefessa.robotimer.application.context;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.LayoutInflater;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import org.jetbrains.annotations.NotNull;

public class ContextApplication implements ApplicationContext {
    private final Context context;
    private final LayoutInflater layoutInflater;

    public ContextApplication(final Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public Intent registerReceiver(
            @Nullable @org.jetbrains.annotations.Nullable final BroadcastReceiver receiver, final IntentFilter filter) {
        return context.registerReceiver(receiver, filter);
    }

    @Override
    public <T extends View> T findViewById(final int id) {
        throw new UnsupportedOperationException("A service does not have view elements.");
    }

    @NonNull
    @Override
    public String getString(final int resId) {
        return context.getString(resId);
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
