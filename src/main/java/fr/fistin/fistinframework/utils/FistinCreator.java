package fr.fistin.fistinframework.utils;

import fr.fistin.fistinframework.player.FistinPlayer;
import fr.fistin.fistinframework.scoreboard.IScoreboardSign;
import fr.fistin.fistinframework.scoreboard.ScoreboardBuilder;
import fr.fistin.fistinframework.team.TeamManager;
import org.jetbrains.annotations.NotNull;

public interface FistinCreator
{
    @NotNull <P> ScoreboardBuilder<P> scoreboardBuilder();
    @NotNull IScoreboardSign newScoreboardSign(FistinPlayer player, String objectiveName, IBukkitPluginProvider caller);
    @NotNull TeamManager newTeamManager();
}
