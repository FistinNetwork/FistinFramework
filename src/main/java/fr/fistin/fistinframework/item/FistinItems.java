package fr.fistin.fistinframework.item;

import fr.fistin.api.utils.PluginLocation;
import fr.fistin.fistinframework.utils.Cleanable;

import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public interface FistinItems extends Cleanable
{
    void registerItem(Supplier<? extends FistinItem> itemSup);
    FistinItem getItem(PluginLocation location);
    Set<String> getRegisteredItemsName();
    Map<String, PluginLocation> nameToLocation();
}
