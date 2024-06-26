package fr.fistin.fistinframework.impl;

import fr.fistin.fistinframework.IFistinFramework;
import fr.fistin.fistinframework.player.FistinPlayer;
import fr.fistin.fistinframework.scoreboard.DefaultScoreboard;
import fr.fistin.fistinframework.utils.IBukkitPluginProvider;
import org.jetbrains.annotations.ApiStatus;

import java.util.Map;
import java.util.function.Function;

@ApiStatus.Internal
class BuiltScoreboard<P> extends DefaultScoreboard
{
    private final P parameter;
    private final Map<Integer, Function<P, String>> lines;

    public BuiltScoreboard(FistinPlayer player, String name, IBukkitPluginProvider caller, P parameter, Map<Integer, Function<P, String>> lines)
    {
        super(IFistinFramework.framework().fistinCreator().newScoreboardSign(player, name, caller));
        this.parameter = parameter;
        this.lines = lines;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateScoreboard()
    {
        this.lines.forEach((lineIn, value) -> {
            final String str = value.apply(this.parameter);
            if(!str.isEmpty())
                this.getScoreboardSign().setLine(lineIn, str);
        });
    }
}
