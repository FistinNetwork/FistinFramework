package fr.fistin.fistinframework.game;

import fr.fistin.api.plugin.PluginType;
import fr.fistin.fistinframework.team.TeamManager;
import fr.fistin.fistinframework.utils.Castex;
import fr.fistin.fistinframework.utils.IBukkitPluginProvider;
import org.jetbrains.annotations.NotNull;

public interface IGamePluginProvider extends IBukkitPluginProvider, Castex<IGamePluginProvider>
{
    GameManager gameManager();

    default TeamManager teamManager()
    {
        return null;
    }

    @Override
    default @NotNull PluginType pluginType()
    {
        return PluginType.GAME;
    }
}
