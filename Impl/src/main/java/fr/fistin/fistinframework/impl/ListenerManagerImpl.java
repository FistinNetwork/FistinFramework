package fr.fistin.fistinframework.impl;

import fr.fistin.fistinframework.listener.ConfigurableListener;
import fr.fistin.fistinframework.listener.ListenerManager;
import fr.fistin.fistinframework.listener.WorldProtectionListener;
import fr.fistin.fistinframework.utils.IBukkitPluginProvider;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@ApiStatus.Internal
class ListenerManagerImpl implements ListenerManager
{
    private final List<Class<? extends Listener>> registered = new ArrayList<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public void enableWorldProtectionListener(@NotNull IBukkitPluginProvider plugin, @NotNull Consumer<ConfigurableListener<WorldProtectionListener>> configurable)
    {
        if(this.enable(WorldProtectionListener.class))
            configurable.andThen(configurableListener -> configurableListener.register(plugin))
                    .accept(new WorldProtectionListener.Builder());
    }

    private boolean enable(Class<? extends Listener> listener)
    {
        if(this.registered.contains(listener))
            return false;
        else this.registered.add(listener);
        return true;
    }

    @Override
    public void clean()
    {
        this.registered.clear();
    }
}
