package fr.fistin.fistinframework.smartinvs.opener;

import fr.fistin.fistinframework.smartinvs.InventoryManager;
import fr.fistin.fistinframework.smartinvs.SmartInventory;
import fr.fistin.fistinframework.utils.FistinValidate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class ChestInventoryOpener implements InventoryOpener
{
    @Override
    public Inventory open(SmartInventory inv, Player player)
    {
        FistinValidate.equals(inv.getColumns(), 9, "The column count for the chest inventory must be 9, found: %s.", inv.getColumns());
        FistinValidate.assertTrue(inv.getRows() >= 1 && inv.getRows() <= 6, "The row count for the chest inventory must be between 1 and 6, found: %s", inv.getRows());

        final InventoryManager manager = inv.getManager();
        final Inventory handle = Bukkit.createInventory(player, inv.getRows() * inv.getColumns(), inv.getTitle());

        this.fill(handle, manager.getContents(player).get());

        player.openInventory(handle);
        return handle;
    }

    @Override
    public boolean supports(InventoryType type)
    {
        return type == InventoryType.CHEST || type == InventoryType.ENDER_CHEST;
    }
}
