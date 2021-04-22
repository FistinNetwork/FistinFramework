package fr.fistin.fistinframework.impl.scoreboard;

import fr.fistin.api.plugin.providers.IBukkitPluginProvider;
import fr.fistin.api.utils.triapi.TriConsumer;
import fr.fistin.fistinframework.IFistinFramework;
import fr.fistin.fistinframework.scoreboard.DefaultScoreboard;
import fr.fistin.fistinframework.scoreboard.IScoreboard;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

class BuiltScoreboard<P> extends DefaultScoreboard
{
    private final P parameter;
    private final Map<Predicate<P>, TriConsumer<IScoreboard, Integer, P>> scoreboardActions;

    public BuiltScoreboard(Player player, String name, IBukkitPluginProvider caller, P parameter, Map<Predicate<P>, TriConsumer<IScoreboard, Integer, P>> scoreboardActions)
    {
        super(IFistinFramework.framework().newScoreboardSign(player, name, caller));
        this.parameter = parameter;
        this.scoreboardActions = scoreboardActions;
    }

    @Override
    public void updateScoreboard()
    {
        final AtomicInteger line = new AtomicInteger(0);
        this.scoreboardActions.forEach((pPredicate, iScoreboardConsumer) -> {
            if (pPredicate.test(this.parameter))
                iScoreboardConsumer.accept(this, line.getAndIncrement(), this.parameter);
        });
    }
}
