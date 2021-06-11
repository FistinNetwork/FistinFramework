package fr.fistin.fistinframework.hostconfig;

import fr.fistin.api.plugin.providers.IBukkitPluginProvider;
import fr.fistin.fistinframework.hostconfig.settings.AbstractSetting;
import fr.fistin.fistinframework.item.ItemStackGenerator;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Category implements ItemStackGenerator
{
    private final String id;
    private final String displayName;
    private final Material displayItem;
    private final List<String> lore;

    private final Map<String, AbstractSetting<?>> settings = new HashMap<>();
    private IBukkitPluginProvider plugin;

    public Category(String id, String displayName, Material displayItem, List<String> lore)
    {
        this.id = id.toLowerCase();
        this.displayName = displayName;
        this.displayItem = displayItem;
        this.lore = lore;
    }

    public <S> void addSetting(AbstractSetting<S> setting)
    {
        setting.setParent(this);
        this.settings.put(setting.getId(), setting);
    }

    public void setPlugin(IBukkitPluginProvider plugin)
    {
        this.plugin = plugin;
    }

    public IBukkitPluginProvider getPlugin()
    {
        return this.plugin;
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

    @SuppressWarnings("unchecked")
    public <S> AbstractSetting<S> getSettingByID(String id)
    {
        return (AbstractSetting<S>)this.settings.get(id);
    }

    public Map<String, AbstractSetting<?>> getSettings()
    {
        return this.settings;
    }
}
