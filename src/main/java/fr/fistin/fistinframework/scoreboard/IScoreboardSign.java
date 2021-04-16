package fr.fistin.fistinframework.scoreboard;

import fr.fistin.api.plugin.providers.IBukkitPluginProvider;

import java.util.List;
import java.util.function.Supplier;

/**
 * Prevent problems at compilation due to used NMS classes in implementation.
 */
public interface IScoreboardSign
{
    void create();
    void destroy();
    void setObjectiveName(String name);
    void setLine(int line, String value);
    void removeLine(int line);
    String getLine(int line);
    IVirtualTeam getTeam(int line);
    IBukkitPluginProvider getCaller();

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
