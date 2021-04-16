package fr.fistin.fistinframework.impl;

import fr.fistin.api.plugin.providers.IBukkitPluginProvider;
import fr.fistin.fistinframework.impl.listener.WorldProtectionListenerImpl;
import fr.fistin.fistinframework.listener.ConfigurableListener;
import fr.fistin.fistinframework.listener.ListenerManager;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ListenerManagerImpl implements ListenerManager
{
    private final List<Class<? extends Listener>> registered = new ArrayList<>();

    @Override
    public void enableWorldProtectionListener(IBukkitPluginProvider plugin, Consumer<ConfigurableListener<? extends Listener>> configurable)
    {
        if(this.enable(WorldProtectionListenerImpl.class))
            configurable.andThen(configurableListener -> configurableListener.register(plugin))
                    .accept(new WorldProtectionListenerImpl.Builder());
    }

    private boolean enable(Class<? extends Listener> listener)
    {
        if(this.registered.contains(listener))
            return false;
        else this.registered.add(listener);
        return true;
    }
}
