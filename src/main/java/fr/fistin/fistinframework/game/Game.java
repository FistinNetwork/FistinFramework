package fr.fistin.fistinframework.game;

import fr.fistin.fistinframework.player.FistinPlayer;
import fr.fistin.fistinframework.player.FistinPlayerContainer;
import fr.fistin.fistinframework.utils.Castex;
import fr.fistin.fistinframework.utils.Cleanable;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public interface Game extends Castex<Game>, Cleanable, FistinPlayerContainer
{
    /**
     * Start the game.
     */
    void start();

    /**
     * Launch timer before first round of the game.
     */
    void time();

    /**
     * Stop the game.
     */
    void stop();

    /**
     * Return the max numbers of players.
     * @return the max numbers of players.
     */
    int maxPlayers();

    /**
     * Return the min numbers of players.
     * @return the min numbers of players.
     */
    int minPlayers();

    /**
     * Return the current numbers of players.
     * @return the current numbers of players.
     */
    int players();

    int timerBeforeGameStart();
    boolean checkPlayersCount();

    /**
     * Return all players who are currently waiting in lobby.
     * @return all players who are currently waiting in lobby.
     */
    @NotNull Map<Player, FistinPlayer> inLobbyPlayers();

    /**
     * Return all players who are currently playing.
     * @return all players who are currently playing.
     */
    @NotNull Map<Player, FistinPlayer> playingPlayers();

    /**
     * Return all players who are currently spectating.
     * @return all players who are currently spectating.
     */
    @NotNull Map<Player, FistinPlayer> spectatingPlayers();

    /**
     * Return the {@link GameManager} attached at this Game.
     * @return the {@link GameManager} attached at this Game.
     */
    @NotNull GameManager gameManager();

    /**
     * Return the current state of the game.
     * @return the current state of the game.
     */
    @NotNull GameState gameState();

    /**
     * Define the current state of the game.
     * @param gameState the new state of the game.
     */
    void setGameState(@NotNull GameState gameState);

    /**
     * Define the current state of the game.
     * @param newState the name of the new state of the game.
     */
    void changeGameState(@NotNull String newState);

    /**
     * Define the current state of the game.
     * @param newState the id of the new state of the game.
     */
    void changeGameState(int newState);
}
