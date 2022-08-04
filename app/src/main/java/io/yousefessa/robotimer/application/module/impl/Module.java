package io.yousefessa.robotimer.application.module.impl;

import androidx.annotation.NonNull;
import org.jetbrains.annotations.NotNull;

public enum Module {
    TIMER, ALARM;

    @NonNull
    @NotNull
    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
