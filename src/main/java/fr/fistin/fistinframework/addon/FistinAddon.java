package fr.fistin.fistinframework.addon;

import fr.fistin.fistinframework.utils.IBukkitPluginProvider;

/**
 * This object is the parent class of all addons. All addons should extend this abstract class to be recognized correctly.
 * @param <C> The config object. Check {@link AddonConfig} for more information.
 */
public abstract class FistinAddon<C>
{
    private String name;
    private String version;
    private boolean logging;
    private IBukkitPluginProvider parent;
    private AddonConfig<C> config;

    /**
     * The first step executed in an addons' lifecycle.
     */
    public abstract void onEnable();

    /**
     * The last step executed in an addon's lifecycle. Take care to clean all your objects correctly!
     */
    public abstract void onDisable();

    /**
     * Define the name of the addon.
     * @param name the name of the addon.
     */
    public final void name(String name)
    {
        this.name = name;
    }

    /**
     * Define the version of the addon.
     * @param version the version of the addon.
     */
    public final void version(String version)
    {
        this.version = version;
    }

    /**
     * Define if parent plugin should log some things about the addon.
     * @param logging true if it should, false if it shouldn't.
     */
    public final void logging(boolean logging)
    {
        this.logging = logging;
    }

    /**
     * Define the parent plugin of this addon.
     * @param parent the parent plugin of this addon.
     */
    public final void parent(IBukkitPluginProvider parent)
    {
        this.parent = parent;
    }

    /**
     * Define the {@link AddonConfig} object.
     * @param config the {@link AddonConfig} object.
     */
    public final void config(AddonConfig<C> config)
    {
        this.config = config;
    }

    /**
     * The name of the addon.
     * @return the name of the addon.
     */
    public final String name()
    {
        return this.name;
    }

    /**
     * The version of the addon.
     * @return the version of the addon.
     */
    public final String version()
    {
        return this.version;
    }

    /**
     * Should an object log some things about the addon?
     * @return true if it should, false if it shouldn't.
     */
    public final boolean logging()
    {
        return this.logging;
    }

    /**
     * The parent plugin of this addon.
     * @return the parent plugin of this addon.
     */
    public final IBukkitPluginProvider parent()
    {
        return this.parent;
    }

    /**
     * The {@link AddonConfig} object.
     * @return the {@link AddonConfig} object.
     */
    public final AddonConfig<C> config()
    {
        return this.config;
    }
}
