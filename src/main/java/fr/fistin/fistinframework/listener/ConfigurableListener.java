package fr.fistin.fistinframework.listener;

import fr.fistin.api.plugin.providers.IBukkitPluginProvider;
import org.bukkit.event.Listener;

public interface ConfigurableListener<L extends Listener>
{
    void register(IBukkitPluginProvider plugin);

    ConfigurableListener<L> reset();
    ConfigurableListener<L> enableAll();
    ConfigurableListener<L> disableAll();
    ConfigurableListener<L> enable(String parameter);
    ConfigurableListener<L> disable(String parameter);
    ConfigurableListener<L> enable(String... parameters);
    ConfigurableListener<L> disable(String... parameters);
}
