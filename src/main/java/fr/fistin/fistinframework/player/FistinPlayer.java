package fr.fistin.fistinframework.player;

import fr.fistin.fistinframework.scoreboard.IScoreboard;
import fr.fistin.fistinframework.utils.Castex;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface FistinPlayer extends Castex<FistinPlayer>
{
    @NotNull Player getPlayer();
    void setPlayer(@NotNull Player player);
    @NotNull PlayerState playerState();
    void setPlayerState(@NotNull PlayerState playerState);
    @NotNull IScoreboard scoreboard();
    void setScoreboard(@NotNull IScoreboard scoreboard);
}
