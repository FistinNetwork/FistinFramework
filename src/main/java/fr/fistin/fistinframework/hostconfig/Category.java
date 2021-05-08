package fr.fistin.fistinframework.hostconfig;

import fr.fistin.fistinframework.hostconfig.settings.AbstractSetting;
import fr.fistin.fistinframework.item.ItemStackGenerator;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class Category implements ItemStackGenerator
{
    private final String id;
    private final String displayName;
    private final Material displayItem;
    private final List<String> lore;

    private final List<AbstractSetting<?>> settings = new ArrayList<>();

    public Category(String id, String displayName, Material displayItem, List<String> lore)
    {
        this.id = id;
        this.displayName = displayName;
        this.displayItem = displayItem;
        this.lore = lore;
    }

    public <S> void addSetting(AbstractSetting<S> setting)
    {
        setting.setParent(this);
        this.settings.add(setting);
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

    public List<AbstractSetting<?>> getSettings()
    {
        return this.settings;
    }
}
