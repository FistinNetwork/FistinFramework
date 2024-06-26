package fr.fistin.fistinframework.smartinvs.opener;

import fr.fistin.fistinframework.smartinvs.InventoryManager;
import fr.fistin.fistinframework.smartinvs.SmartInventory;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SpecialInventoryOpener implements InventoryOpener
{
    private static final List<InventoryType> SUPPORTED = Collections.unmodifiableList(
            Arrays.asList(
                    InventoryType.FURNACE,
                    InventoryType.WORKBENCH,
                    InventoryType.DISPENSER,
                    InventoryType.DROPPER,
                    InventoryType.ENCHANTING,
                    InventoryType.BREWING,
                    InventoryType.ANVIL,
                    InventoryType.BEACON,
                    InventoryType.HOPPER
            )
    );

    @Override
    public Inventory open(SmartInventory inv, Player player)
    {
        final InventoryManager manager = inv.getManager();
        final Inventory handle = Bukkit.createInventory(player, inv.getType(), inv.getTitle());

        this.fill(handle, manager.getContents(player).get());

        player.openInventory(handle);
        return handle;
    }

    @Override
    public boolean supports(InventoryType type) {
        return SUPPORTED.contains(type);
    }
}
