package fr.fistin.fistinframework.utils;

import fr.fistin.fistinframework.map.GameMap;
import fr.fistin.fistinframework.player.FistinPlayer;
import fr.fistin.fistinframework.scoreboard.IScoreboardSign;
import fr.fistin.fistinframework.scoreboard.ScoreboardBuilder;
import fr.fistin.fistinframework.team.TeamManager;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

public interface FistinCreator
{
    @NotNull GameMap newLocalGameMap(Path worldFolder, String worldName, boolean loadOnInit);
    @NotNull <P> ScoreboardBuilder<P> scoreboardBuilder();
    @NotNull IScoreboardSign newScoreboardSign(FistinPlayer player, String objectiveName, IBukkitPluginProvider caller);
    @NotNull TeamManager newTeamManager();
}
