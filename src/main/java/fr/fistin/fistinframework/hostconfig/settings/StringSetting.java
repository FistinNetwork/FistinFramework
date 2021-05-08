package fr.fistin.fistinframework.hostconfig.settings;

import fr.fistin.fistinframework.IFistinFramework;
import fr.fistin.fistinframework.anvilgui.AnvilGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.function.Consumer;

public class StringSetting extends AbstractSetting<String>
{
    public StringSetting(String id, String displayName, Material displayItem, List<String> lore)
    {
        this(id, displayName, displayItem, lore, "");
    }

    public StringSetting(String id, String displayName, Material displayItem, List<String> lore, String value)
    {
        super(id, displayName, displayItem, lore, value);
    }

    @Override
    public SettingType getType()
    {
        return SettingType.STRING;
    }

    @Override
    public Consumer<InventoryClickEvent> rightClickConsumer()
    {
        return e -> {
            if(e.getWhoClicked() instanceof Player)
            {
                IFistinFramework.framework().anvilGUI()
                        .title(this.getDisplayName())
                        .plugin(this.getParent().getPlugin())
                        .itemLeft(new ItemStack(this.getDisplayItem()))
                        .text(this.getDefaultValue())
                        .onComplete((player, s) -> {
                            this.setValue(s);
                            return AnvilGUI.Response.openInventory(e.getInventory());
                        })
                        .open((Player)e.getWhoClicked());
            }
        };
    }

    @Override
    public Consumer<InventoryClickEvent> leftClickConsumer()
    {
        return this.rightClickConsumer();
    }
}
