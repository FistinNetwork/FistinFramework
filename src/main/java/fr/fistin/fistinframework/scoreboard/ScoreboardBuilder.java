package fr.fistin.fistinframework.scoreboard;

import fr.fistin.api.plugin.providers.IBukkitPluginProvider;
import org.bukkit.entity.Player;

import java.util.function.Function;

public interface ScoreboardBuilder<P>
{
    ScoreboardBuilder<P> addLine(int line, Function<P, String> value);
    ScoreboardBuilder<P> setParameter(P parameter);
    ScoreboardBuilder<P> setCaller(IBukkitPluginProvider caller);
    ScoreboardBuilder<P> setName(String name);
    IScoreboard build(Player player);
}
