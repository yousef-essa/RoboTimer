package io.yousefessa.robotimer.application.module.impl;

import androidx.annotation.NonNull;
import org.jetbrains.annotations.NotNull;

public enum Module {
    SCREEN_TRACKER, TIMER_SCREEN;

    @NonNull
    @NotNull
    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
