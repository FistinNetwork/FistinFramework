package fr.fistin.fistinframework.listener;

import fr.fistin.fistinframework.utils.IBukkitPluginProvider;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.ApiStatus;

public interface ConfigurableListener<L extends Listener>
{
    @ApiStatus.Internal
    void register(IBukkitPluginProvider plugin);

    ConfigurableListener<L> reset();
    ConfigurableListener<L> enableAll();
    ConfigurableListener<L> disableAll();
    ConfigurableListener<L> enable(String parameter);
    ConfigurableListener<L> disable(String parameter);
    ConfigurableListener<L> enable(String... parameters);
    ConfigurableListener<L> disable(String... parameters);
}
