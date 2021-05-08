package fr.fistin.fistinframework.hostconfig.settings;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.List;
import java.util.function.Consumer;

public class IntegerSetting extends AbstractSetting<Integer>
{
    public IntegerSetting(String id, String displayName, Material displayItem, List<String> lore)
    {
        this(id, displayName, displayItem, lore, 0);
    }

    public IntegerSetting(String id, String displayName, Material displayItem, List<String> lore, int value)
    {
        super(id, displayName, displayItem, lore, value);
    }

    @Override
    public SettingType getType()
    {
        return SettingType.INTEGER;
    }

    @Override
    public Consumer<InventoryClickEvent> rightClickConsumer()
    {
        return e -> this.setValue(this.getValue() - 1);
    }

    @Override
    public Consumer<InventoryClickEvent> leftClickConsumer()
    {
        return e -> this.setValue(this.getValue() + 1);
    }
}