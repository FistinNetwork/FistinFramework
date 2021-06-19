package fr.fistin.fistinframework.player;

import fr.fistin.fistinframework.configuration.LanguageContainer;
import fr.fistin.fistinframework.game.GameManager;
import fr.fistin.fistinframework.scoreboard.IScoreboard;
import fr.fistin.fistinframework.utils.Castex;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface FistinPlayer extends LanguageContainer, Castex<FistinPlayer>
{
    @NotNull Player getPlayer();
    void setPlayer(@NotNull Player player);
    @NotNull PlayerState playerState();
    void setPlayerState(@NotNull PlayerState playerState);

    void changePlayerState(@NotNull String newState);
    void changePlayerState(int newState);

    @NotNull IScoreboard scoreboard();
    void setScoreboard(@NotNull IScoreboard scoreboard);

    String getName();

    GameManager gameManager();
}
