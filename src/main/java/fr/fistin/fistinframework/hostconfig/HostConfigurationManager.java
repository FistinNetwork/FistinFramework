package fr.fistin.fistinframework.hostconfig;

import fr.fistin.api.plugin.providers.IBukkitPluginProvider;

import java.util.Map;

public interface HostConfigurationManager
{
    void registerConfig(HostConfig hostConfig);
    Map<IBukkitPluginProvider, HostConfig> getConfigs();
}
