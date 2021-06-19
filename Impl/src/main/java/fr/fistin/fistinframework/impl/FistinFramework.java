package fr.fistin.fistinframework.impl;

import fr.fistin.api.plugin.providers.IBukkitPluginProvider;
import fr.fistin.api.plugin.providers.PluginProviders;
import fr.fistin.fistinframework.IFistinFramework;
import fr.fistin.fistinframework.addon.AddonProcessor;
import fr.fistin.fistinframework.anvilgui.AnvilGUI;
import fr.fistin.fistinframework.configuration.ConfigurationMappings;
import fr.fistin.fistinframework.configuration.LanguageManager;
import fr.fistin.fistinframework.configuration.Messages;
import fr.fistin.fistinframework.event.GameManagerInitEvent;
import fr.fistin.fistinframework.event.GameStateChangedEvent;
import fr.fistin.fistinframework.event.InnerListenerEvent;
import fr.fistin.fistinframework.event.PlayerStateChangedEvent;
import fr.fistin.fistinframework.eventbus.DefaultEventBus;
import fr.fistin.fistinframework.eventbus.IFistinEvent;
import fr.fistin.fistinframework.eventbus.IFistinEventBus;
import fr.fistin.fistinframework.grade.LuckPermsToFistin;
import fr.fistin.fistinframework.hostconfig.HostConfigurationManager;
import fr.fistin.fistinframework.item.IFistinItems;
import fr.fistin.fistinframework.listener.ListenerManager;
import fr.fistin.fistinframework.player.FistinPlayer;
import fr.fistin.fistinframework.scoreboard.IScoreboardSign;
import fr.fistin.fistinframework.scoreboard.ScoreboardBuilder;
import fr.fistin.fistinframework.smartinvs.InventoryManager;
import fr.fistin.fistinframework.team.TeamManager;
import fr.fistin.fistinframework.utils.FireworkFactory;
import fr.fistin.fistinframework.utils.PlayerHelper;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.function.Supplier;

@ApiStatus.Internal
public final class FistinFramework extends JavaPlugin implements IFistinFramework
{
    private AddonProcessor addonProcessor;
    private ConfigurationMappings mappings;
    private IFistinEventBus<Supplier<? extends IFistinEvent>> eventBus;
    private FireworkFactory fireworkFactory;
    private HostConfigurationManager hostConfigurationManager;
    private IFistinItems items;
    private LanguageManager languageManager;
    private ListenerManager listenerManager;
    private LuckPermsToFistin luckPermsToFistin;
    private Messages messages;
    private PlayerHelper playerHelper;
    private InventoryManager smartInvsManager;

    @Override
    public void onEnable()
    {
        this.getLogger().info("Starting Fistin Framework...");

        PluginProviders.setProvider(IFistinFramework.class, this);
        this.init();
        this.postInit();
    }

    private void init()
    {
        this.addonProcessor = new AddonProcessorImpl();
        this.mappings = new ConfigurationMappingsImpl();
        this.eventBus = new DefaultEventBus();
        this.fireworkFactory = new FireworkFactoryImpl();
        this.hostConfigurationManager = new HostConfigurationManagerImpl();
        this.items = new FistinItemsImpl();
        this.languageManager = new LanguageManagerImpl();
        this.listenerManager = new ListenerManagerImpl();
        this.luckPermsToFistin = new LuckPermsToFistinImpl();
        this.messages = new MessagesImpl();
        this.playerHelper = new PlayerHelperImpl();
        this.smartInvsManager = new InventoryManagerImpl(this, InventoryContentsImpl::new);

        this.languageManager.load(this, Locale.FRENCH);

        this.eventBus.registerEvent(GameManagerInitEvent.class);
        this.eventBus.registerEvent(GameStateChangedEvent.class);
        this.eventBus.registerEvent(InnerListenerEvent.class);
        this.eventBus.registerEvent(PlayerStateChangedEvent.class);

        this.smartInvsManager.init();
    }

    private void postInit()
    {
        this.getServer().getPluginManager().registerEvents(new ItemListener(), this);
        this.getCommand("fistindebug").setExecutor(new DebugCommand(this));
        this.getCommand("fgive").setExecutor(new FGiveCommand());
        this.getCommand("ffirework").setExecutor(new FFireworkCommand());
    }

    @Override
    public void onDisable()
    {
        this.fireworkFactory.clear();
        this.eventBus.clear();
        this.items.clear();
        this.languageManager.clear();

        super.onDisable();
        this.getLogger().info("Stopped Fistin Framework, have a nice day !");
    }

    @Override
    public @NotNull AddonProcessor addonProcessor()
    {
        return this.addonProcessor;
    }

    @Override
    public @NotNull AnvilGUI.Builder anvilGUI()
    {
        return new AnvilGUIImpl.BuilderImpl();
    }

    @Override
    public @NotNull ConfigurationMappings mappings()
    {
        return this.mappings;
    }

    @Override
    public @NotNull IFistinEventBus<Supplier<? extends IFistinEvent>> eventBus()
    {
        return this.eventBus;
    }

    @Override
    public @NotNull IFistinEventBus<Supplier<? extends IFistinEvent>> newEventBus()
    {
        return new DefaultEventBus();
    }

    @Override
    public @NotNull FireworkFactory fireworkFactory()
    {
        return this.fireworkFactory;
    }

    @Override
    public @NotNull HostConfigurationManager hostConfigurationManager()
    {
        return this.hostConfigurationManager;
    }

    @Override
    public @NotNull IFistinItems items()
    {
        return this.items;
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
    public @NotNull InventoryManager smartInvsManager()
    {
        return this.smartInvsManager;
    }

    @Override
    public @NotNull TeamManager teamManager()
    {
        return new TeamManagerImpl();
    }
}