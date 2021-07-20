package fr.fistin.fistinframework.impl;

import fr.fistin.api.plugin.providers.PluginProviders;
import fr.fistin.fistinframework.IFistinFramework;
import fr.fistin.fistinframework.anvilgui.AnvilGUI;
import fr.fistin.fistinframework.configuration.ConfigurationMappings;
import fr.fistin.fistinframework.configuration.LanguageManager;
import fr.fistin.fistinframework.configuration.Messages;
import fr.fistin.fistinframework.eventbus.DefaultEventBus;
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
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

@ApiStatus.Internal
public final class FistinFramework extends JavaPlugin implements IFistinFramework
{
    private AutomaticRegisterer automaticRegisterer;
    private ConfigurationMappings configurationMappings;
    private FireworkFactory fireworkFactory;
    private FistinEventBus<Supplier<? extends FistinEvent>> fistinEventBus;
    private FistinItems fistinItems;
    private HostConfigurationManager hostConfigurationManager;
    private LanguageManager languageManager;
    private ListenerManager listenerManager;
    private LuckPermsToFistin luckPermsToFistin;
    private Messages messages;
    private PlayerHelper playerHelper;
    private InventoryManager inventoryManager;

    @Override
    public void onEnable()
    {
        this.getLogger().info("Starting Fistin Framework...");

        PluginProviders.setProvider(IFistinFramework.class, this);
        this.saveDefaultConfig();

        this.init();
        this.postInit();
    }

    private void init()
    {
        this.getLogger().info("Initializing APIs' Framework and Framework's official implementation...");
        this.automaticRegisterer = new AutomaticRegistererImpl();
        this.configurationMappings = new ConfigurationMappingsImpl();
        this.fistinEventBus = new DefaultEventBus();
        this.fireworkFactory = new FireworkFactoryImpl();
        this.hostConfigurationManager = new HostConfigurationManagerImpl();
        this.fistinItems = new FistinItemsImpl();
        this.languageManager = new LanguageManagerImpl();
        this.listenerManager = new ListenerManagerImpl();
        this.luckPermsToFistin = new LuckPermsToFistinImpl();
        this.messages = new MessagesImpl();
        this.playerHelper = new PlayerHelperImpl();
        this.inventoryManager = new InventoryManagerImpl(this, InventoryContentsImpl::new);

        this.getLogger().info("Loading languages...");
        this.languageManager.load(this, this.getConfig().getString("global_language"));

        this.automaticRegisterer.register(this, "fr.fistin.fistinframework.event", AutomaticRegisterer.Type.EVENT);

        this.inventoryManager.init();
    }

    private void postInit()
    {
        this.automaticRegisterer.register(this, "fr.fistin.fistinframework.impl", AutomaticRegisterer.Type.LISTENER);
        this.automaticRegisterer.register(this, "fr.fistin.fistinframework.impl", AutomaticRegisterer.Type.COMMAND);
    }

    @Override
    public void onDisable()
    {
        this.getLogger().info("Cleaning up Framework...");
        this.configurationMappings.clean();
        this.fireworkFactory.clean();
        this.fistinEventBus.clean();
        this.fistinItems.clean();
        this.hostConfigurationManager.clean();
        this.languageManager.clean();
        this.listenerManager.clean();
        this.inventoryManager.clean();

        this.getLogger().info("Shutdown Fistin Framework, have a nice day!");
    }

    @Override
    public @NotNull AnvilGUI.Builder anvilGUI()
    {
        return new AnvilGUIImpl.BuilderImpl();
    }

    @Override
    public @NotNull AutomaticRegisterer automaticRegisterer()
    {
        return this.automaticRegisterer;
    }

    @Override
    public @NotNull ConfigurationMappings configurationMappings()
    {
        return this.configurationMappings;
    }

    @Override
    public @NotNull FireworkFactory fireworkFactory()
    {
        return this.fireworkFactory;
    }

    @Override
    public @NotNull FistinEventBus<Supplier<? extends FistinEvent>> fistinEventBus()
    {
        return this.fistinEventBus;
    }

    @Override
    public @NotNull FistinEventBus<Supplier<? extends FistinEvent>> newFistinEventBus()
    {
        return new DefaultEventBus();
    }

    @Override
    public @NotNull FistinItems fistinItems()
    {
        return this.fistinItems;
    }

    @Override
    public @NotNull HostConfigurationManager hostConfigurationManager()
    {
        return this.hostConfigurationManager;
    }

    @Override
    public @NotNull InventoryManager inventoryManager()
    {
        return this.inventoryManager;
    }

    @Override
    public @NotNull LanguageManager languageManager()
    {
        return this.languageManager;
    }

    @Override
    public @NotNull ListenerManager listenerManager()
    {
        return this.listenerManager;
    }

    @Override
    public @NotNull LuckPermsToFistin luckPermsToFistin()
    {
        return this.luckPermsToFistin;
    }

    @Override
    public @NotNull Messages messages()
    {
        return this.messages;
    }

    @Override
    public @NotNull PlayerHelper playerHelper()
    {
        return this.playerHelper;
    }

    @Override
    public @NotNull <P> ScoreboardBuilder<P> scoreboardBuilder()
    {
        return new ScoreboardBuilderImpl<>();
    }

    @Override
    public @NotNull <P> ScoreboardBuilder<P> scoreboardBuilder(Class<P> paramClass)
    {
        return new ScoreboardBuilderImpl<>();
    }

    @Override
    public @NotNull IScoreboardSign newScoreboardSign(FistinPlayer player, String objectiveName, IBukkitPluginProvider caller)
    {
        return new ScoreboardSignImpl(player, objectiveName, caller);
    }

    @Override
    public @NotNull TeamManager newTeamManager()
    {
        return new TeamManagerImpl();
    }
}
