package fr.fistin.fistinframework.impl;

import fr.fistin.api.utils.PluginLocation;
import fr.fistin.fistinframework.IFistinFramework;
import fr.fistin.fistinframework.item.FistinItem;
import fr.fistin.fistinframework.item.FistinItems;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
class ItemListener implements Listener
{
    @EventHandler(priority = EventPriority.HIGH)
    public void onItemClickEvent(PlayerInteractEvent event)
    {
        final ItemStack item = event.getItem();

        if(item == null || item.getType() == Material.AIR) return;

        final String name = item.getItemMeta().getDisplayName();
        final FistinItems items = IFistinFramework.framework().fistinItems();

        if (name == null || !items.getRegisteredItemsName().contains(name)) return;

        final PluginLocation location = items.nameToLocation().get(name);

        if (location == null || !item.getItemMeta().getLore().contains("Location: " + location.getFinalPath())) return;

        final FistinItem fistinItem = items.getItem(location);

        if(fistinItem == null || item.getType() != fistinItem.enclosingItemType()) return;

        final Action action = event.getAction();

        if(action == Action.PHYSICAL) return;

        final FistinItem.ClickType clickType = action == Action.RIGHT_CLICK_AIR || action == Action.LEFT_CLICK_AIR ? FistinItem.ClickType.AIR : FistinItem.ClickType.BLOCK;
        final Player player = event.getPlayer();
        final Block block = event.getClickedBlock();

        if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) fistinItem.onItemRightClick(player, clickType, block);
        else if (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK) fistinItem.onItemLeftClick(player, clickType, block);
    }
}
