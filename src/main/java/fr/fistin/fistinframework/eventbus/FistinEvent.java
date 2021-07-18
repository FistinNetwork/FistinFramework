package fr.fistin.fistinframework.eventbus;

import org.jetbrains.annotations.NotNull;

public interface FistinEvent
{
    default @NotNull String getName()
    {
        return this.getClass().getSimpleName();
    }
}
