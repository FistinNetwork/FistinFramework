package fr.fistin.fistinframework.impl.listener;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public final class PreparedWorldProtectionListener implements Listener
{
    @EventHandler
    public void onBreakBlock(BlockBreakEvent event)
    {
        if(event.getPlayer().getGameMode() != GameMode.CREATIVE)
            event.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlaced(final BlockPlaceEvent event)
    {
        if(event.getPlayer().getGameMode() != GameMode.CREATIVE)
            event.setCancelled(true);
    }

    @EventHandler
    public void onBlockBurn(final BlockBurnEvent event)
    {
        event.setCancelled(true);
    }

    @EventHandler
    public void onWeatherChange(final WeatherChangeEvent event)
    {
        event.setCancelled(true);
    }

    @EventHandler
    public void onFoodChange(final FoodLevelChangeEvent event)
    {
        event.setCancelled(true);
        event.setFoodLevel(20);
    }

    @EventHandler
    public void onPlayerClickInventory(final InventoryClickEvent event)
    {
        if(event.getWhoClicked().getGameMode() != GameMode.CREATIVE)
            event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerOpenInventory(final InventoryOpenEvent event)
    {
        if(event.getPlayer().getGameMode() != GameMode.CREATIVE)
            if (!(event.getInventory() instanceof PlayerInventory))
                event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerDrop(final PlayerDropItemEvent event)
    {
        if(event.getPlayer().getGameMode() != GameMode.CREATIVE)
            event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerTakeDamage(final EntityDamageEvent event)
    {
        event.setDamage(0);
        if(event.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK) event.setCancelled(true);
    }
}
