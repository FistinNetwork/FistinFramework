package fr.fistin.fistinframework.addon;

import fr.fistin.fistinframework.utils.IBukkitPluginProvider;

import java.util.Collection;
import java.util.List;

public interface AddonProcessor
{
    List<AddonInfo> detectAddons(String searchingPackage);
    List<FistinAddon<?>> getAddons(Collection<AddonInfo> infos, IBukkitPluginProvider plugin);
}
