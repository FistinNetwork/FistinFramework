package fr.fistin.fistinframework.hostconfig;

import fr.fistin.fistinframework.utils.Cleanable;
import fr.fistin.fistinframework.utils.IBukkitPluginProvider;

import java.util.Map;

public interface HostConfigurationManager extends Cleanable
{
    void registerConfig(HostConfig hostConfig);
    Map<IBukkitPluginProvider, HostConfig> getConfigs();
}
