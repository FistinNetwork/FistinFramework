package fr.fistin.fistinframework.game;

import fr.fistin.fistinframework.player.FistinPlayer;
import fr.fistin.fistinframework.utils.Castex;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public interface Game extends Castex<Game>
{
    void start();
    void stop();
    int maxPlayers();
    int minPlayers();
    int players();
    int timerBeforeGameStart();

    void addNewPlayer(@NotNull Player player);
    @NotNull Map<Player, FistinPlayer> inLobbyPlayers();
    @NotNull Map<Player, FistinPlayer> playingPlayers();
    @NotNull Map<Player, FistinPlayer> spectatingPlayers();
    @Nullable FistinPlayer findPlayer(@NotNull Player player);

    @NotNull GameState gameState();
    void setGameState(@NotNull GameState gameState);
}
