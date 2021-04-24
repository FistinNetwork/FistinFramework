package fr.fistin.fistinframework.player;

import fr.fistin.fistinframework.configuration.LanguageContainer;
import fr.fistin.fistinframework.scoreboard.IScoreboard;
import fr.fistin.fistinframework.utils.Castex;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public interface FistinPlayer extends LanguageContainer, Castex<FistinPlayer>
{
    @NotNull Player getPlayer();
    void setPlayer(@NotNull Player player);
    @NotNull PlayerState playerState();
    void setPlayerState(@NotNull PlayerState playerState);

    @ApiStatus.Experimental
    void changePlayerState(@NotNull String newState);

    @NotNull IScoreboard scoreboard();
    void setScoreboard(@NotNull IScoreboard scoreboard);
}
