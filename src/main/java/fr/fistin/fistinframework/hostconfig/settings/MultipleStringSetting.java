package fr.fistin.fistinframework.hostconfig.settings;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MultipleStringSetting extends AbstractSetting<Integer>
{
    private final List<String> strings;

    public MultipleStringSetting(String id, String displayName, Material displayItem, List<String> lore, List<String> strings)
    {
        this(id, displayName, displayItem, lore, strings, 0);
    }

    public MultipleStringSetting(String id, String displayName, Material displayItem, List<String> lore, List<String> strings, Integer value)
    {
        super(id, displayName, displayItem, lore, value);
        this.strings = strings;
    }

    @Override
    public SettingType getType()
    {
        return SettingType.MULTIPLE_STRING;
    }

    public List<String> getStrings()
    {
        return this.strings;
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
