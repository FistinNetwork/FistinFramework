package fr.fistin.fistinframework.listener;

import fr.fistin.api.plugin.providers.IBukkitPluginProvider;
import org.bukkit.event.Listener;

import java.util.function.Consumer;

public interface ListenerManager
{
    /**
     * ===================== Parameters =====================
     *                      blockBroken
     *                      blockPlaced
     *                      blockBurn
     *                      weatherChange
     *                      foodChange
     *                      playerClickInventory
     *                      playerOpenInventory
     *                      playerDrop
     *                      playerTakeDamage
     * ===================== Parameters =====================
     * @param plugin plugin.
     * @param configurable configurable listener.
     */
    void enableWorldProtectionListener(IBukkitPluginProvider plugin, Consumer<ConfigurableListener<? extends Listener>> configurable);
}
