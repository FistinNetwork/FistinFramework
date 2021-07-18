package fr.fistin.fistinframework.impl;

import fr.fistin.fistinframework.hostconfig.HostConfig;
import fr.fistin.fistinframework.hostconfig.HostConfigurationManager;
import fr.fistin.fistinframework.utils.IBukkitPluginProvider;
import org.jetbrains.annotations.ApiStatus;

import java.util.Collections;
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
        return Collections.unmodifiableMap(this.configs);
    }

    @Override
    public void clean()
    {
        this.configs.values().forEach(HostConfig::clean);
        this.configs.clear();
    }
}
