package fr.fistin.fistinframework.impl;

import fr.fistin.fistinframework.utils.PlayerHelper;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class PlayerHelperImpl implements PlayerHelper
{
    @Override
    public void clearPlayer(Player player)
    {
        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);
        player.getInventory().clear();
        player.updateInventory();
    }

    @Override
    public void restorePlayerHealth(Player player)
    {
        player.setHealth(20);
        player.setFoodLevel(20);
        player.setSaturation(20);
        player.setGameMode(GameMode.ADVENTURE);
    }
}
