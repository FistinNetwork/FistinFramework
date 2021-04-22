package fr.fistin.fistinframework.scoreboard;

import fr.fistin.api.plugin.providers.IBukkitPluginProvider;
import fr.fistin.api.utils.triapi.TriConsumer;
import org.bukkit.entity.Player;

import java.util.function.Predicate;

public interface ScoreboardBuilder<P>
{
    ScoreboardBuilder<P> addAction(Predicate<P> condition, TriConsumer<IScoreboard, Integer, P> action);
    ScoreboardBuilder<P> setParameter(P parameter);
    ScoreboardBuilder<P> setCaller(IBukkitPluginProvider caller);
    ScoreboardBuilder<P> setName(String name);
    IScoreboard build(Player player);
}
