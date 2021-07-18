package fr.fistin.fistinframework.listener;

import fr.fistin.fistinframework.utils.Cleanable;
import fr.fistin.fistinframework.utils.IBukkitPluginProvider;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public interface ListenerManager extends Cleanable
{
    /**
     * Enable the world protection listener.
     * @param plugin plugin.
     * @param configurable configurable listener.
     * @see WorldProtectionListener.Builder for more details on parameters.
     */
    void enableWorldProtectionListener(@NotNull IBukkitPluginProvider plugin, @NotNull Consumer<ConfigurableListener<WorldProtectionListener>> configurable);
}
