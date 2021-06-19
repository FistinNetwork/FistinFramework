package fr.fistin.fistinframework.impl;

import fr.fistin.api.plugin.providers.IBukkitPluginProvider;
import fr.fistin.fistinframework.player.FistinPlayer;
import fr.fistin.fistinframework.scoreboard.IScoreboard;
import fr.fistin.fistinframework.scoreboard.ScoreboardBuilder;
import fr.fistin.fistinframework.utils.FistinValidate;
import org.jetbrains.annotations.ApiStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@ApiStatus.Internal
class ScoreboardBuilderImpl<P> implements ScoreboardBuilder<P>
{
    private final Map<Integer, Function<P, String>> lines = new HashMap<>();
    private P parameter = null;
    private IBukkitPluginProvider caller = null;
    private String name = null;

    /**
     * {@inheritDoc}
     */
    @Override
    public ScoreboardBuilder<P> addLine(int line, Function<P, String> value)
    {
        FistinValidate.numberInferior(line, 14, "line cannot be superior than 14!");
        this.lines.put(line, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ScoreboardBuilder<P> setParameter(P parameter)
    {
        this.parameter = parameter;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ScoreboardBuilder<P> setCaller(IBukkitPluginProvider caller)
    {
        this.caller = caller;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ScoreboardBuilder<P> setName(String name)
    {
        this.name = name;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IScoreboard build(FistinPlayer player)
    {
        FistinValidate.notNull(this.name, "name cannot be null!");
        FistinValidate.notNull(this.parameter, "parameter cannot be null!");
        FistinValidate.notNull(this.caller, "caller cannot be null!");

        return new BuiltScoreboard<>(player, this.name, this.caller, this.parameter, this.lines);
    }
}
