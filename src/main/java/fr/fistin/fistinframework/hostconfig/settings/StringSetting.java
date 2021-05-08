package fr.fistin.fistinframework.hostconfig.settings;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.Inventory;

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
        return e -> {};
    }

    @Override
    public Consumer<InventoryClickEvent> leftClickConsumer()
    {
        return this.rightClickConsumer();
    }
}
