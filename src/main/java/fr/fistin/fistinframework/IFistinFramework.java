package fr.fistin.fistinframework;

import fr.fistin.api.plugin.PluginType;
import fr.fistin.api.plugin.providers.PluginProviders;
import fr.fistin.fistinframework.addon.AddonProcessor;
import fr.fistin.fistinframework.anvilgui.AnvilGUI;
import fr.fistin.fistinframework.configuration.ConfigurationMappings;
import fr.fistin.fistinframework.configuration.LanguageManager;
import fr.fistin.fistinframework.configuration.Messages;
import fr.fistin.fistinframework.eventbus.FistinEvent;
import fr.fistin.fistinframework.eventbus.FistinEventBus;
import fr.fistin.fistinframework.grade.LuckPermsToFistin;
import fr.fistin.fistinframework.hostconfig.HostConfigurationManager;
import fr.fistin.fistinframework.item.FistinItems;
import fr.fistin.fistinframework.listener.ListenerManager;
import fr.fistin.fistinframework.player.FistinPlayer;
import fr.fistin.fistinframework.scoreboard.IScoreboardSign;
import fr.fistin.fistinframework.scoreboard.ScoreboardBuilder;
import fr.fistin.fistinframework.smartinvs.InventoryManager;
import fr.fistin.fistinframework.team.TeamManager;
import fr.fistin.fistinframework.utils.AutomaticRegisterer;
import fr.fistin.fistinframework.utils.FireworkFactory;
import fr.fistin.fistinframework.utils.IBukkitPluginProvider;
import fr.fistin.fistinframework.utils.PlayerHelper;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public interface IFistinFramework extends IBukkitPluginProvider
{
    static IFistinFramework framework()
    {
        return PluginProviders.getProvider(IFistinFramework.class);
    }

    @NotNull String NAMESPACE = "fistinframework";

    @NotNull AddonProcessor addonProcessor();
    @NotNull AnvilGUI.Builder anvilGUI();
    @NotNull AutomaticRegisterer automaticRegisterer();
    @NotNull ConfigurationMappings configurationMappings();
    @NotNull FireworkFactory fireworkFactory();
    @NotNull FistinEventBus<Supplier<? extends FistinEvent>> fistinEventBus();
    @NotNull FistinEventBus<Supplier<? extends FistinEvent>> newFistinEventBus();
    @NotNull FistinItems fistinItems();
    @NotNull HostConfigurationManager hostConfigurationManager();
    @NotNull InventoryManager inventoryManager();
    @NotNull LanguageManager languageManager();
    @NotNull ListenerManager listenerManager();
    @NotNull LuckPermsToFistin luckPermsToFistin();
    @NotNull Messages messages();
    @NotNull PlayerHelper playerHelper();
    @NotNull <P> ScoreboardBuilder<P> scoreboardBuilder();
    @NotNull <P> ScoreboardBuilder<P> scoreboardBuilder(Class<P> paramClass);
    @NotNull IScoreboardSign newScoreboardSign(FistinPlayer player, String objectiveName, IBukkitPluginProvider caller);
    @NotNull TeamManager newTeamManager();

    @Override
    default @NotNull PluginType pluginType()
    {
        return PluginType.UTILITY;
    }
}
