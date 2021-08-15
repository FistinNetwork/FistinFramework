package fr.fistin.fistinframework.impl;

import fr.fistin.fistinframework.map.GameMap;
import fr.fistin.fistinframework.player.FistinPlayer;
import fr.fistin.fistinframework.scoreboard.IScoreboardSign;
import fr.fistin.fistinframework.scoreboard.ScoreboardBuilder;
import fr.fistin.fistinframework.team.TeamManager;
import fr.fistin.fistinframework.utils.FistinCreator;
import fr.fistin.fistinframework.utils.IBukkitPluginProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

@ApiStatus.Internal
class FistinCreatorImpl implements FistinCreator
{
    @Override
    public @NotNull GameMap newLocalGameMap(Path worldFolder, String worldName, boolean loadOnInit)
    {
        return new GameMapImpl(worldFolder, worldName, loadOnInit);
    }

    @Override
    public @NotNull <P> ScoreboardBuilder<P> scoreboardBuilder()
    {
        return new ScoreboardBuilderImpl<>();
    }

    @Override
    public @NotNull IScoreboardSign newScoreboardSign(FistinPlayer player, String objectiveName, IBukkitPluginProvider caller)
    {
        return new ScoreboardSignImpl(player, objectiveName, caller);
    }

    @Override
    public @NotNull TeamManager newTeamManager()
    {
        return new TeamManagerImpl();
    }
}
