package fr.fistin.fistinframework.eventbus;

import org.jetbrains.annotations.NotNull;

public interface IFistinEvent
{
    default @NotNull String getName()
    {
        return this.getClass().getSimpleName();
    }
}
