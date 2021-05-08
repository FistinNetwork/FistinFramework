package fr.fistin.fistinframework.hostconfig.settings;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.List;
import java.util.function.Consumer;

public class BooleanSetting extends AbstractSetting<Boolean>
{
    public BooleanSetting(String id, String displayName, Material displayItem, List<String> lore)
    {
        this(id, displayName, displayItem, lore, false);
    }

    public BooleanSetting(String id, String displayName, Material displayItem, List<String> lore, boolean value)
    {
        super(id, displayName, displayItem, lore, value);
    }

    @Override
    public SettingType getType()
    {
        return SettingType.BOOLEAN;
    }

    @Override
    public Consumer<InventoryClickEvent> rightClickConsumer()
    {
        return this.leftClickConsumer();
    }

    @Override
    public Consumer<InventoryClickEvent> leftClickConsumer()
    {
        return e -> this.setValue(!this.getValue());
    }
}
