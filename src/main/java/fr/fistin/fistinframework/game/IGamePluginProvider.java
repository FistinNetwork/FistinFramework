package fr.fistin.fistinframework.game;

import fr.fistin.api.plugin.PluginType;
import fr.fistin.api.plugin.providers.IBukkitPluginProvider;
import fr.fistin.fistinframework.team.TeamManager;
import org.jetbrains.annotations.NotNull;

public interface IGamePluginProvider extends IBukkitPluginProvider
{
    GameManager gameManager();
    TeamManager teamManager();

    @Override
    default @NotNull PluginType pluginType()
    {
        return PluginType.GAME;
    }
}
