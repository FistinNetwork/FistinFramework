package fr.fistin.fistinframework.map;

import org.bukkit.World;

public interface GameMap
{
    boolean load();
    boolean unload();
    boolean restoreFromSource();
    boolean isLoaded();
    World getWorld();
}
