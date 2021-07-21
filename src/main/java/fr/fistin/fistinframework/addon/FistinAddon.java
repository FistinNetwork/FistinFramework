package fr.fistin.fistinframework.addon;

import fr.fistin.fistinframework.utils.IBukkitPluginProvider;

public abstract class FistinAddon<C>
{
    private String name;
    private String version;
    private boolean logging;
    private IBukkitPluginProvider parent;
    private AddonConfig<C> config;

    public abstract void onEnable();
    public abstract void onDisable();

    public final void name(String name)
    {
        this.name = name;
    }

    public final void version(String version)
    {
        this.version = version;
    }

    public final void logging(boolean logging)
    {
        this.logging = logging;
    }

    public final void parent(IBukkitPluginProvider parent)
    {
        this.parent = parent;
    }

    public final void config(AddonConfig<C> config)
    {
        this.config = config;
    }

    public final String name()
    {
        return this.name;
    }

    public final String version()
    {
        return this.version;
    }

    public final boolean logging()
    {
        return this.logging;
    }

    public final IBukkitPluginProvider parent()
    {
        return this.parent;
    }

    public final AddonConfig<C> config()
    {
        return this.config;
    }
}
