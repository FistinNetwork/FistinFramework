package fr.fistin.fistinframework.utils;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class PlayerHelper
{
    public void clear(Player player)
    {
        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);
        player.getInventory().clear();
        player.updateInventory();
    }

    public void restore(Player player)
    {
        player.setHealth(20);
        player.setFoodLevel(20);
        player.setSaturation(20);
        player.setGameMode(GameMode.ADVENTURE);
    }
}
