package fr.fistin.fistinframework.impl;

import fr.fistin.api.utils.PluginLocation;
import fr.fistin.fistinframework.IFistinFramework;
import fr.fistin.fistinframework.item.FistinItem;
import fr.fistin.fistinframework.item.FistinItems;
import org.jetbrains.annotations.ApiStatus;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@ApiStatus.Internal
class FistinItemsImpl implements FistinItems
{
    private final Map<PluginLocation, FistinItem> items = new HashMap<>();

    @Override
    public void registerItem(Supplier<? extends FistinItem> itemSup)
    {
        final FistinItem item = itemSup.get();
        final PluginLocation location = item.location();
        if(!this.items.containsKey(location))
        {
            IFistinFramework.framework().getLogger().info("Registered new item with id (" + location.getFinalPath() + ')');
            this.items.put(location, item);
        }
    }

    @Override
    public FistinItem getItem(PluginLocation location)
    {
        return this.items.get(location);
    }

    @Override
    public Set<String> getRegisteredItemsName()
    {
        return Collections.unmodifiableSet(this.items.values().stream().map(FistinItem::displayName).collect(Collectors.toSet()));
    }

    @Override
    public Map<String, PluginLocation> nameToLocation()
    {
        final Map<String, PluginLocation> result = new HashMap<>();
        this.items.keySet().forEach(location -> result.put(this.items.get(location).displayName(), location));
        return Collections.unmodifiableMap(result);
    }

    @Override
    public void clean()
    {
        this.items.clear();
    }
}
