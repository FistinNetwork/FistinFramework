package fr.fistin.fistinframework.runnable;

import java.util.concurrent.atomic.AtomicLong;

@FunctionalInterface
public interface FistinRunnableTimer
{
    default void onTimerPass(CancellableRunnable cancellableRunnable, long timer) {}
    default void onTimerEnd(CancellableRunnable cancellableRunnable) {}

    AtomicLong timer();

    interface CancellableRunnable
    {
        void cancel();
    }
}
