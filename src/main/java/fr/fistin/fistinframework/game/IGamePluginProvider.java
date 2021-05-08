package fr.fistin.fistinframework.game;

import fr.fistin.api.plugin.PluginType;
import fr.fistin.api.plugin.providers.IBukkitPluginProvider;
import fr.fistin.fistinframework.IFistinFramework;
import fr.fistin.fistinframework.team.TeamManager;
import org.jetbrains.annotations.NotNull;

public interface IGamePluginProvider extends IBukkitPluginProvider
{
    GameManager gameManager();

    default TeamManager teamManager()
    {
        return Cache.cache();
    }

    final class Cache
    {
        private static TeamManager cache;

        private static TeamManager cache()
        {
            return cache != null ? cache : (cache = IFistinFramework.framework().teamManager());
        }
    }

    @Override
    default void onDisable()
    {
        Cache.cache = null;
    }

    @Override
    default @NotNull PluginType pluginType()
    {
        return PluginType.GAME;
    }
}
