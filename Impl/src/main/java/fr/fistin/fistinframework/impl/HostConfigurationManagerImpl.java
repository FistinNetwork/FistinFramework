package fr.fistin.fistinframework.impl;

import fr.fistin.api.plugin.providers.IBukkitPluginProvider;
import fr.fistin.fistinframework.hostconfig.HostConfig;
import fr.fistin.fistinframework.hostconfig.HostConfigurationManager;
import org.jetbrains.annotations.ApiStatus;

import java.util.HashMap;
import java.util.Map;

@ApiStatus.Internal
class HostConfigurationManagerImpl implements HostConfigurationManager
{
    private final Map<IBukkitPluginProvider, HostConfig> configs = new HashMap<>();

    @Override
    public void registerConfig(HostConfig hostConfig)
    {
        this.configs.putIfAbsent(hostConfig.getPlugin(), hostConfig);
    }

    @Override
    public Map<IBukkitPluginProvider, HostConfig> getConfigs()
    {
        return this.configs;
    }
}
