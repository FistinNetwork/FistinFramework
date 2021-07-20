package fr.fistin.fistinframework.utils;

import fr.fistin.api.plugin.providers.IPluginProvider;

public interface AutomaticRegisterer
{
    void register(IBukkitPluginProvider plugin, String packageName, Type type);

    enum Type {
        ADDON,
        COMMAND,
        EVENT,
        LISTENER
    }
}
