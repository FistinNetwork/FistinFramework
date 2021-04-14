package fr.fistin.fistinframework.game;

import fr.fistin.api.plugin.PluginType;
import fr.fistin.api.plugin.providers.IPluginProvider;
import org.jetbrains.annotations.NotNull;

public interface IGamePluginProvider extends IPluginProvider
{
    GameManager gameManager();

    @Override
    default @NotNull PluginType pluginType()
    {
        return PluginType.GAME;
    }
}
