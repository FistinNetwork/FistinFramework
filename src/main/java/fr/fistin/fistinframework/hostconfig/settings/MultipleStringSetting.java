package fr.fistin.fistinframework.hostconfig.settings;

import fr.fistin.fistinframework.IFistinFramework;
import fr.fistin.fistinframework.anvilgui.AnvilGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

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
        return e -> {
            if(e.isShiftClick()) this.openAnvilGUI(e);
            else this.setValue(this.getValue() - 1);
        };
    }

    @Override
    public Consumer<InventoryClickEvent> leftClickConsumer()
    {
        return e -> {
            if(e.isShiftClick()) this.openAnvilGUI(e);
            else this.setValue(this.getValue() + 1);
        };
    }

    private void openAnvilGUI(InventoryClickEvent e)
    {
        IFistinFramework.framework().anvilGUI()
                .title(this.getDisplayName())
                .plugin(this.getParent().getPlugin())
                .itemLeft(new ItemStack(this.getDisplayItem()))
                .text(this.strings.get(this.getValue()))
                .onComplete((player, s) -> {
                    this.strings.set(this.getValue(), s);
                    return AnvilGUI.Response.openInventory(e.getInventory());
                })
                .open((Player)e.getWhoClicked());
    }
}
