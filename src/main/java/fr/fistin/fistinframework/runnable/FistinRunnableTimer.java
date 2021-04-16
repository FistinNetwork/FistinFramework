package fr.fistin.fistinframework.runnable;

import java.util.concurrent.atomic.AtomicLong;

@FunctionalInterface
public interface FistinRunnableTimer
{
    default void onTimerPass(long timer) {}
    default void onTimerEnd() {}

    AtomicLong timer();
}
