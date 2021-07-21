package fr.fistin.fistinframework.utils;

import fr.fistin.fistinframework.addon.FistinAddon;

import java.util.Set;

public interface IAddonProvider extends IBukkitPluginProvider
{
    Set<FistinAddon<?>> addons();
    void addons(Set<FistinAddon<?>> addons);
}
