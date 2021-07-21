package fr.fistin.fistinframework.utils;

public interface AutomaticRegisterer
{
    void register(IBukkitPluginProvider plugin, String packageName, Type type);

    enum Type {
        ADDON,
        COMMAND,
        EVENT,
        ITEM,
        LISTENER
    }
}
