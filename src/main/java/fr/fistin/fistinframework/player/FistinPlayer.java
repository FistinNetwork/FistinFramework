package fr.fistin.fistinframework.player;

import fr.fistin.fistinframework.configuration.LanguageContainer;
import fr.fistin.fistinframework.game.GameManager;
import fr.fistin.fistinframework.scoreboard.IScoreboard;
import fr.fistin.fistinframework.utils.Castex;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public interface FistinPlayer extends LanguageContainer, Castex<FistinPlayer>
{
    /**
     * Get the attached player to this object.
     * @return the attached player.
     */
    @NotNull Player getPlayer();

    /**
     * Define a new player for this object.
     * @param player the player to define.
     */
    void setPlayer(@NotNull Player player);

    /**
     * Get the current player's state.
     * @return the current player's state.
     */
    @NotNull PlayerState playerState();

    /**
     * Define a new player's state for the current player.
     * @param playerState the new state to define.
     */
    void setPlayerState(@NotNull PlayerState playerState);

    /**
     * Change the current player's state by another state by its name.
     * @param newState the state's name.
     */
    void changePlayerState(@NotNull String newState);

    /**
     * Change the current player's state by another state by its id.
     * @param newState the state's id.
     */
    void changePlayerState(int newState);

    /**
     * Get the scoreboard attached to this player.
     * @return the attached scoreboard
     */
    @NotNull IScoreboard scoreboard();

    /**
     * Define a new scoreboard and create it for the current player.
     * @param scoreboard the new scoreboard to define.
     */
    void setScoreboard(@NotNull IScoreboard scoreboard);

    /**
     * Shortcut to access to player's name.
     * @return the player's name.
     */
    String getName();

    /**
     * Shortcut to access to player's UUID.
     * @return the player's UUID.
     */
    UUID getUniqueId();

    /**
     * Get the attached {@link GameManager}.
     * @return the attached GameManager.
     */
    GameManager gameManager();

    /**
     * Get preferences of the player.
     * @return the preferences of the player.
     */
    Preferences preferences();
}
