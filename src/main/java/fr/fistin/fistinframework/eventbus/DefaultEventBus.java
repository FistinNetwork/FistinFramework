package fr.fistin.fistinframework.eventbus;

import fr.fistin.fistinframework.utils.FistinFrameworkException;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.Supplier;

public class DefaultEventBus implements FistinEventBus<Supplier<? extends FistinEvent>>
{
    private static final Map<DefaultEventBus, EventExecution> EVENT_EXECUTIONS = new HashMap<>();

    private final Set<Class<? extends FistinEvent>> registeredEvents = new HashSet<>();
    private final Set<FistinEventListener> listeners = new HashSet<>();

    @Override
    public @NotNull Set<Class<? extends FistinEvent>> registeredEvents()
    {
        return Collections.unmodifiableSet(this.registeredEvents);
    }

    @Override
    public @NotNull Set<FistinEventListener> listeners()
    {
        return Collections.unmodifiableSet(this.listeners);
    }

    @Override
    public void registerEvent(Class<? extends FistinEvent> eventClass)
    {
        this.registeredEvents.add(eventClass);
    }

    @Override
    public void addListener(FistinEventListener listener)
    {
        this.listeners.add(listener);
    }

    @Override
    public void handleEvent(Supplier<? extends FistinEvent> eventSup)
    {
        EVENT_EXECUTIONS.put(this, new EventExecution(eventSup.get().getName(), System.currentTimeMillis()));
        final FistinEvent event = eventSup.get();
        if(this.registeredEvents.contains(event.getClass()))
        {
            this.listeners.forEach(listener -> {
                final Class<? extends FistinEventListener> clazz = listener.getClass();
                Arrays.stream(clazz.getDeclaredMethods())
                        .filter(method -> method.isAnnotationPresent(FistinEventHandler.class))
                        .filter(method -> method.getParameterCount() == 1)
                        .filter(method -> method.getParameterTypes()[0] == event.getClass())
                        .forEach(method -> {
                            try
                            {
                                method.setAccessible(true);
                                method.invoke(listener, event);
                            } catch (IllegalAccessException | InvocationTargetException e)
                            {
                                throw new FistinFrameworkException(e);
                            }
                        });
            });
        }
    }

    @Override
    public void clean()
    {
        this.registeredEvents.clear();
        this.listeners.clear();
        EVENT_EXECUTIONS.remove(this);
    }

    @Override
    public String implName()
    {
        return "default";
    }

    public static Map<DefaultEventBus, EventExecution> getEventExecutions()
    {
        return Collections.unmodifiableMap(EVENT_EXECUTIONS);
    }

    public static class EventExecution
    {
        private final String name;
        private final long timestamp;

        public EventExecution(String name, long timestamp)
        {
            this.name = name;
            this.timestamp = timestamp;
        }

        public String getName()
        {
            return this.name;
        }

        public long getTimestamp()
        {
            return this.timestamp;
        }
    }
}
