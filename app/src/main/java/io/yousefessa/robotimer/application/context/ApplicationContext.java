package io.yousefessa.robotimer.application.context;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

public interface ApplicationContext {
    Intent registerReceiver(@Nullable BroadcastReceiver receiver, IntentFilter filter);

    <T extends View> T findViewById(@IdRes int id);

    @NonNull
    String getString(@StringRes int resId);
}
