package fr.fistin.fistinframework.impl.scoreboard;

import fr.fistin.api.plugin.providers.IBukkitPluginProvider;
import fr.fistin.api.utils.triapi.TriConsumer;
import fr.fistin.fistinframework.scoreboard.IScoreboard;
import fr.fistin.fistinframework.scoreboard.ScoreboardBuilder;
import fr.fistin.fistinframework.utils.FistinFrameworkException;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

@ApiStatus.Internal
public class ScoreboardBuilderImpl<P> implements ScoreboardBuilder<P>
{
    private final Map<Predicate<P>, TriConsumer<IScoreboard, Integer, P>> scoreboardActions = new HashMap<>();
    private P parameter = null;
    private IBukkitPluginProvider caller = null;
    private String name = null;

    @Override
    public ScoreboardBuilder<P> addAction(Predicate<P> condition, TriConsumer<IScoreboard, Integer, P> action)
    {
        this.scoreboardActions.put(condition, action);
        return this;
    }

    @Override
    public ScoreboardBuilder<P> setParameter(P parameter)
    {
        this.parameter = parameter;
        return this;
    }

    @Override
    public ScoreboardBuilder<P> setCaller(IBukkitPluginProvider caller)
    {
        this.caller = caller;
        return this;
    }

    @Override
    public ScoreboardBuilder<P> setName(String name)
    {
        this.name = name;
        return this;
    }

    @Override
    public IScoreboard build(Player player)
    {
        if(this.name == null) throw new FistinFrameworkException("name cannot be null!");
        if(this.parameter == null) throw new FistinFrameworkException("parameter cannot be null!");
        if(this.caller == null) throw new FistinFrameworkException("caller cannot be null!");

        return new BuiltScoreboard<>(player, this.name, this.caller, this.parameter, this.scoreboardActions);
    }
}
