package fr.fistin.fistinframework.scoreboard;

import fr.fistin.api.plugin.providers.IBukkitPluginProvider;
import fr.fistin.fistinframework.IFistinFramework;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.function.Supplier;

/**
 * Prevent problems at compilation due to NMS classes used in implementation.
 * Represent a (low-level) Scoreboard in FistinFramework.
 *
 * @see ScoreboardBuilder
 * @see IFistinFramework#newScoreboardSign(Player, String, IBukkitPluginProvider)

 */
public interface IScoreboardSign
{
    /**
     * Create the scoreboard.
     */
    void create();

    /**
     * Destroy the scoreboard.
     */
    void destroy();

    /**
     * Define a new objective name.
     * @param name the new objective name.
     */
    void setObjectiveName(String name);

    /**
     * Define a value at a specified line.
     * @param line the line to define.
     * @param value the value to set.
     */
    void setLine(int line, String value);

    /**
     * Remove the specified line from the Scoreboard.
     * @param line the line to remove.
     */
    void removeLine(int line);

    /**
     * Get the value of the specified line.
     * @param line the specified line
     * @return the value at the line.
     */
    String getLine(int line);

    /**
     * Get the {@link IVirtualTeam} at the specified line.
     * @param line the line where a team can be found.
     * @return the virtual team.
     */
    IVirtualTeam getTeam(int line);

    /**
     * Get the {@link IBukkitPluginProvider} who created the scoreboard.
     * @return the caller.
     */
    IBukkitPluginProvider getCaller();

    /**
     * Represent a virtual team in a {@link IScoreboardSign}.
     */
    interface IVirtualTeam
    {
        String getPrefix();
        String getSuffix();
        String getValue();
        String getCurrentPlayer();
        Supplier<?> createTeam();
        Supplier<?> updateTeam();
        Supplier<?> removeTeam();
        Supplier<?> changePlayer();
        Supplier<?> addOrRemovePlayer(int mode, String playerName);
        List<?> sendLine();
        void setPlayer(String name);
        void reset();
        void setValue(String value);
        void setSuffix(String prefix);
        void setPrefix(String prefix);
    }
}
