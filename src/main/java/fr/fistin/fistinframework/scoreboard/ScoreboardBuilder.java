package fr.fistin.fistinframework.scoreboard;

import fr.fistin.api.plugin.providers.IBukkitPluginProvider;
import org.bukkit.entity.Player;

import java.util.function.Function;

/**
 * Utility-class to create easily {@link IScoreboard}.
 * @param <P> the parameter given at each action.
 *
 * @see IScoreboard
 * @see IScoreboardSign
 */
public interface ScoreboardBuilder<P>
{
    /**
     * Add a line to the scoreboard.
     * @param line the index of the line.
     * @param value a Function who return the value for a given {@link P} object.
     * @return the scoreboard builder.
     */
    ScoreboardBuilder<P> addLine(int line, Function<P, String> value);

    /**
     * Define the parameter of the Scoreboard.
     * @param parameter the parameter.
     * @return the scoreboard builder.
     */
    ScoreboardBuilder<P> setParameter(P parameter);

    /**
     * Set the {@link IBukkitPluginProvider} who is creating the scoreboard.
     * @return the scoreboard builder.
     */
    ScoreboardBuilder<P> setCaller(IBukkitPluginProvider caller);

    /**
     * Set the default objective name of the Scoreboard.
     * @param name the name.
     * @return the scoreboard builder.
     */
    ScoreboardBuilder<P> setName(String name);

    /**
     * Build the scoreboard for a player.
     * @param player the player attached to the scoreboard.
     * @return the built {@link IScoreboard}.
     */
    IScoreboard build(Player player);
}
