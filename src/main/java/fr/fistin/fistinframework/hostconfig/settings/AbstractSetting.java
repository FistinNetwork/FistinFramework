package fr.fistin.fistinframework.hostconfig.settings;

import fr.fistin.fistinframework.hostconfig.Category;
import fr.fistin.fistinframework.item.ItemStackGenerator;
import fr.fistin.fistinframework.smartinvs.ClickableItem;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.List;
import java.util.function.Consumer;

public abstract class AbstractSetting<S> implements ItemStackGenerator
{
    private final String id;
    private final String displayName;
    private final Material displayItem;
    private final List<String> lore;
    private final S defaultValue;

    private Category parent;
    private S value;

    protected AbstractSetting(String id, String displayName, Material displayItem, List<String> lore)
    {
        this.id = id.toLowerCase();
        this.displayName = displayName;
        this.displayItem = displayItem;
        this.lore = lore;
        this.defaultValue = null;
    }

    protected AbstractSetting(String id, String displayName, Material displayItem, List<String> lore, S value)
    {
        this.id = id.toLowerCase();
        this.displayName = displayName;
        this.displayItem = displayItem;
        this.lore = lore;
        this.value = this.defaultValue = value;
    }

    public void setValue(S value)
    {
        this.value = value;
    }

    public S getValue()
    {
        return this.value;
    }

    public void setParent(Category parent)
    {
        this.parent = parent;
    }

    public Category getParent()
    {
        return this.parent;
    }

    public S getDefaultValue()
    {
        return this.defaultValue;
    }

    public String getId()
    {
        return this.id;
    }

    @Override
    public String getDisplayName()
    {
        return this.displayName;
    }

    @Override
    public Material getDisplayItem()
    {
        return this.displayItem;
    }

    @Override
    public List<String> getLore()
    {
        return this.lore;
    }

    @Override
    public ClickableItem toClickableItem()
    {
        return ClickableItem.of(this.genItemStack(), inventoryClickEvent -> {
            if(inventoryClickEvent.getClick() == ClickType.MIDDLE)
                this.resetConsumer().accept(inventoryClickEvent);
            if(inventoryClickEvent.isLeftClick())
                this.leftClickConsumer().accept(inventoryClickEvent);
            else if(inventoryClickEvent.isRightClick())
                this.rightClickConsumer().accept(inventoryClickEvent);
        });
    }

    public abstract SettingType getType();
    public abstract Consumer<InventoryClickEvent> rightClickConsumer();
    public abstract Consumer<InventoryClickEvent> leftClickConsumer();

    public Consumer<InventoryClickEvent> resetConsumer()
    {
        return e -> this.setValue(this.defaultValue);
    }
}
