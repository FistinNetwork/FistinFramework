package fr.fistin.fistinframework.game;

import fr.fistin.fistinframework.player.FistinPlayer;
import fr.fistin.fistinframework.player.FistinPlayerContainer;
import fr.fistin.fistinframework.utils.Castex;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public interface Game extends Castex<Game>, FistinPlayerContainer
{
    void start();
    void time();
    void stop();
    int maxPlayers();
    int minPlayers();
    int players();
    int timerBeforeGameStart();
    boolean checkPlayersCount();

    @NotNull Map<Player, FistinPlayer> inLobbyPlayers();
    @NotNull Map<Player, FistinPlayer> playingPlayers();
    @NotNull Map<Player, FistinPlayer> spectatingPlayers();

    @NotNull GameManager gameManager();

    @NotNull GameState gameState();
    void setGameState(@NotNull GameState gameState);
    void changeGameState(@NotNull String newState);
    void changeGameState(int newState);
}
