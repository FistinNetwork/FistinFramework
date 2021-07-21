package fr.fistin.fistinframework;

import fr.fistin.api.plugin.PluginType;
import fr.fistin.api.plugin.providers.PluginProviders;
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
import fr.fistin.fistinframework.smartinvs.InventoryManager;
import fr.fistin.fistinframework.utils.*;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public interface IFistinFramework extends IBukkitPluginProvider
{
    static IFistinFramework framework()
    {
        return PluginProviders.getProvider(IFistinFramework.class);
    }

    @NotNull String NAMESPACE = "fistinframework";

    @NotNull AnvilGUI.Builder anvilGUI();
    @NotNull AutomaticRegisterer automaticRegisterer();
    @NotNull ConfigurationMappings configurationMappings();
    @NotNull FireworkFactory fireworkFactory();
    @NotNull FistinCreator fistinCreator();
    @NotNull FistinEventBus<Supplier<? extends FistinEvent>> fistinEventBus();
    @NotNull FistinItems fistinItems();
    @NotNull HostConfigurationManager hostConfigurationManager();
    @NotNull InventoryManager inventoryManager();
    @NotNull LanguageManager languageManager();
    @NotNull ListenerManager listenerManager();
    @NotNull LuckPermsToFistin luckPermsToFistin();
    @NotNull Messages messages();
    @NotNull PlayerHelper playerHelper();

    @Override
    default @NotNull PluginType pluginType()
    {
        return PluginType.UTILITY;
    }
}
