package fr.fistin.fistinframework;

import fr.fistin.api.plugin.PluginType;
import fr.fistin.api.plugin.providers.IBukkitPluginProvider;
import fr.fistin.api.plugin.providers.PluginProviders;
import fr.fistin.fistinframework.addon.AddonProcessor;
import fr.fistin.fistinframework.anvilgui.AnvilGUI;
import fr.fistin.fistinframework.configuration.ConfigurationMappings;
import fr.fistin.fistinframework.configuration.LanguageManager;
import fr.fistin.fistinframework.configuration.Messages;
import fr.fistin.fistinframework.eventbus.IFistinEvent;
import fr.fistin.fistinframework.eventbus.IFistinEventBus;
import fr.fistin.fistinframework.game.IGamePluginProvider;
import fr.fistin.fistinframework.grade.LuckPermsToFistin;
import fr.fistin.fistinframework.hostconfig.HostConfigurationManager;
import fr.fistin.fistinframework.item.IFistinItems;
import fr.fistin.fistinframework.listener.ListenerManager;
import fr.fistin.fistinframework.scoreboard.IScoreboardSign;
import fr.fistin.fistinframework.scoreboard.ScoreboardBuilder;
import fr.fistin.fistinframework.smartinvs.InventoryManager;
import fr.fistin.fistinframework.team.TeamManager;
import fr.fistin.fistinframework.utils.FireworkFactory;
import fr.fistin.fistinframework.utils.PlayerHelper;
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

    @Override
    default void onDisable()
    {
        Cache.cache = null;
    }

    @NotNull String NAMESPACE = "fistinframework";

    @NotNull AddonProcessor addonProcessor();
    @NotNull AnvilGUI.Builder anvilGUI();
    @NotNull ConfigurationMappings mappings();
    @NotNull IFistinEventBus<Supplier<? extends IFistinEvent>> eventBus();
    @NotNull IFistinEventBus<Supplier<? extends IFistinEvent>> newEventBus();
    @NotNull FireworkFactory fireworkFactory();
    @NotNull HostConfigurationManager hostConfigurationManager();
    @NotNull IFistinItems items();
    @NotNull LanguageManager languageManager();
    @NotNull ListenerManager listenerManager();
    @NotNull LuckPermsToFistin luckPermsToFistin();
    @NotNull Messages messages();
    @NotNull PlayerHelper playerHelper();
    @NotNull <P> ScoreboardBuilder<P> scoreboardBuilder();
    @NotNull <P> ScoreboardBuilder<P> scoreboardBuilder(Class<P> paramClass);
    @NotNull IScoreboardSign newScoreboardSign(Player player, String objectiveName, IBukkitPluginProvider caller);
    @NotNull InventoryManager smartInvsManager();
    @NotNull TeamManager teamManager();

    @Override
    default @NotNull PluginType pluginType()
    {
        return PluginType.UTILITY;
    }
}
