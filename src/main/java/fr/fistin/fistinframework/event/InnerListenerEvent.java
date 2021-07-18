package fr.fistin.fistinframework.event;

import fr.fistin.fistinframework.eventbus.FistinEvent;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

public class InnerListenerEvent implements FistinEvent, ICancellable
{
    private final String listenerName;
    private final Event processingEvent;
    private final Object[] parameters;

    private boolean cancelled = false;

    public InnerListenerEvent(String listenerName, Event processingEvent, Object... parameters)
    {
        this.listenerName = listenerName;
        this.processingEvent = processingEvent;
        this.parameters = parameters;
    }

    public String listenerName()
    {
        return this.listenerName;
    }

    public Event processingEvent()
    {
        return this.processingEvent;
    }

    public Object[] parameters()
    {
        return this.parameters;
    }

    @Override
    public void setCancelled(boolean cancelled)
    {
        this.cancelled = cancelled;
    }

    @Override
    public boolean isCancelled()
    {
        return this.cancelled;
    }

    @Override
    public @NotNull String getName()
    {
        return this.getClass().getSimpleName();
    }
}
