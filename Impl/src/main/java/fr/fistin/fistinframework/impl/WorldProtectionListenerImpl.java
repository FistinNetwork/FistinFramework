package fr.fistin.fistinframework.impl;

import fr.fistin.api.plugin.providers.IBukkitPluginProvider;
import fr.fistin.fistinframework.listener.ConfigurableListener;
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

import java.util.HashMap;
import java.util.Map;

@ApiStatus.Internal
class WorldProtectionListenerImpl implements Listener
{
    private final Map<String, Boolean> configuration = new HashMap<>();

    private WorldProtectionListenerImpl(Map<String, Boolean> configuration)
    {
        this.configuration.putAll(configuration);
    }

    @EventHandler
    public void onBlockBroken(BlockBreakEvent event)
    {
        if (this.configuration.get("blockBroken") && event.getPlayer().getGameMode() != GameMode.CREATIVE) event.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlaced(final BlockPlaceEvent event)
    {
        if(this.configuration.get("blockPlaced") && event.getPlayer().getGameMode() != GameMode.CREATIVE)
            event.setCancelled(true);
    }

    @EventHandler
    public void onBlockBurn(final BlockBurnEvent event)
    {
        if(this.configuration.get("blockBurn"))
            event.setCancelled(true);
    }

    @EventHandler
    public void onWeatherChange(final WeatherChangeEvent event)
    {
        if(this.configuration.get("weatherChange"))
            event.setCancelled(true);
    }

    @EventHandler
    public void onFoodChange(final FoodLevelChangeEvent event)
    {
        if(this.configuration.get("foodChange"))
        {
            event.setCancelled(true);
            event.setFoodLevel(20);
        }
    }

    @EventHandler
    public void onPlayerClickInventory(final InventoryClickEvent event)
    {
        if(this.configuration.get("playerClickInventory") && event.getWhoClicked().getGameMode() != GameMode.CREATIVE)
            event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerOpenInventory(final InventoryOpenEvent event)
    {
        if(this.configuration.get("playerOpenInventory") && event.getPlayer().getGameMode() != GameMode.CREATIVE)
            if (!(event.getInventory() instanceof PlayerInventory))
                event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerDrop(final PlayerDropItemEvent event)
    {
        if(this.configuration.get("playerDrop") && event.getPlayer().getGameMode() != GameMode.CREATIVE)
            event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerTakeDamage(final EntityDamageEvent event)
    {
        if(this.configuration.get("playerTakeDamage"))
        {
            event.setDamage(0);
            if (event.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK) event.setCancelled(true);
        }
    }

    public static class Builder implements ConfigurableListener<Listener>
    {
        private final Map<String, Boolean> configuration = new HashMap<>();

        public Builder()
        {
            this.configuration.put("blockBroken", true);
            this.configuration.put("blockPlaced", true);
            this.configuration.put("blockBurn", true);
            this.configuration.put("weatherChange", true);
            this.configuration.put("foodChange", false);
            this.configuration.put("playerClickInventory", false);
            this.configuration.put("playerOpenInventory", false);
            this.configuration.put("playerDrop", false);
            this.configuration.put("playerTakeDamage", false);
        }

        @Override
        public void register(IBukkitPluginProvider plugin)
        {
            plugin.getServer().getPluginManager().registerEvents(new WorldProtectionListenerImpl(this.configuration), plugin);
        }

        @Override
        public ConfigurableListener<Listener> reset()
        {
            return new Builder();
        }

        @Override
        public ConfigurableListener<Listener> enableAll()
        {
            this.configuration.replaceAll((s, aBoolean) -> true);
            return this;
        }

        @Override
        public ConfigurableListener<Listener> disableAll()
        {
            this.configuration.replaceAll((s, aBoolean) -> false);
            return this;
        }

        @Override
        public ConfigurableListener<Listener> enable(String parameter)
        {
            this.configuration.replace(parameter, true);
            return this;
        }

        @Override
        public ConfigurableListener<Listener> disable(String parameter)
        {
            this.configuration.replace(parameter, false);
            return this;
        }

        @Override
        public ConfigurableListener<Listener> enable(String... parameters)
        {
            for (String parameter : parameters)
                this.configuration.replace(parameter, true);
            return this;
        }

        @Override
        public ConfigurableListener<Listener> disable(String... parameters)
        {
            for (String parameter : parameters)
                this.configuration.replace(parameter, false);
            return this;
        }
    }
}
