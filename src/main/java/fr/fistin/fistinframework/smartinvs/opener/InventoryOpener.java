package fr.fistin.fistinframework.smartinvs.opener;

import fr.fistin.fistinframework.smartinvs.ClickableItem;
import fr.fistin.fistinframework.smartinvs.SmartInventory;
import fr.fistin.fistinframework.smartinvs.content.InventoryContents;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public interface InventoryOpener
{
    Inventory open(SmartInventory inv, Player player);
    boolean supports(InventoryType type);

    default void fill(Inventory handle, InventoryContents contents)
    {
        final ClickableItem[][] items = contents.all();

        for(int row = 0; row < items.length; row++)
        {
            for(int column = 0; column < items[row].length; column++)
            {
                if(items[row][column] != null)
                    handle.setItem(9 * row + column, items[row][column].getItem());
            }
        }
    }
}
