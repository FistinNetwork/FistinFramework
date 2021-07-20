package fr.fistin.fistinframework.utils;

import fr.fistin.fistinframework.addon.FistinAddon;

import java.util.List;

public interface IAddonProvider extends IBukkitPluginProvider
{
    List<FistinAddon<?>> addons();
    void addons(List<FistinAddon<?>> addons);
}
