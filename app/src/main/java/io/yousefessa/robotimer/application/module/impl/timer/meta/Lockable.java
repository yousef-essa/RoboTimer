package io.yousefessa.robotimer.application.module.impl.timer.meta;

public interface Lockable {
    void lock();
    void unlock();
    boolean isLocked();
}
