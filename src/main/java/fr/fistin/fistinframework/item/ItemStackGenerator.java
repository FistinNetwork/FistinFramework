package fr.fistin.fistinframework.item;

import fr.fistin.fistinframework.smartinvs.ClickableItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public interface ItemStackGenerator
{
    default ItemStack genItemStack()
    {
        final ItemStack itemStack = new ItemStack(this.getDisplayItem());
        final ItemMeta meta = itemStack.getItemMeta();

        meta.setDisplayName(this.getDisplayName());
        meta.setLore(this.getLore());
        itemStack.setItemMeta(meta);

        return itemStack;
    }

    default ClickableItem toClickableItem()
    {
        return ClickableItem.empty(this.genItemStack());
    }

    String getDisplayName();
    Material getDisplayItem();
    List<String> getLore();
}
