package fr.fistin.fistinframework;

import fr.fistin.api.plugin.PluginType;
import fr.fistin.api.plugin.providers.IBukkitPluginProvider;
import fr.fistin.api.plugin.providers.PluginProviders;
import fr.fistin.fistinframework.eventbus.IFistinEvent;
import fr.fistin.fistinframework.eventbus.IFistinEventBus;
import fr.fistin.fistinframework.item.IFistinItems;
import fr.fistin.fistinframework.scoreboard.IScoreboardSign;
import fr.fistin.fistinframework.smartinvs.InventoryManager;
import fr.fistin.fistinframework.utils.FireworkFactory;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public interface IFistinFramework extends IBukkitPluginProvider
{
    static IFistinFramework framework()
    {
        return Cache.cache();
    }

    @ApiStatus.Internal
    final class Cache
    {
        private static IFistinFramework cache;

        private static IFistinFramework cache()
        {
            return cache != null ? cache : (cache = PluginProviders.getProvider(IFistinFramework.class));
        }
    }

    String NAMESPACE = "fistinframework";
    String BUNGEE_CORD_CHANNEL = "BungeeCord";

    @NotNull IFistinEventBus<Supplier<? extends IFistinEvent>> eventBus();
    @NotNull IFistinEventBus<Supplier<? extends IFistinEvent>> newEventBus();
    @NotNull FireworkFactory fireworkFactory();
    @NotNull IScoreboardSign newScoreboardSign(Player player, String objectiveName);
    @NotNull InventoryManager smartInvsManager();
    @NotNull IFistinItems items();

    @Override
    default @NotNull PluginType pluginType()
    {
        return PluginType.UTILITY;
    }
}
