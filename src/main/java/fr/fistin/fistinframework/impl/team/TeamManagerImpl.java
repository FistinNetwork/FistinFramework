package fr.fistin.fistinframework.impl.team;

import fr.fistin.fistinframework.player.FistinPlayer;
import fr.fistin.fistinframework.team.FistinTeam;
import fr.fistin.fistinframework.team.TeamManager;
import fr.fistin.fistinframework.utils.FistinFrameworkException;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@ApiStatus.Internal
public class TeamManagerImpl implements TeamManager
{
    @NotNull
    private final Map<String, FistinTeam> teams = new HashMap<>();

    @Override
    public void addTeam(@NotNull FistinTeam team)
    {
        this.teams.put(team.getName(), team);
    }

    public void remove(@NotNull FistinTeam team)
    {
        this.teams.remove(team.getName());
    }

    @Override
    public void removeAll()
    {
        this.teams.clear();
    }

    @Override
    public @Nullable FistinTeam getTeam(@NotNull String teamName)
    {
        return this.teams.get(teamName);
    }

    @Override
    public void transfer(@NotNull FistinTeam origin, @NotNull FistinTeam destination, boolean overrideName)
    {
        if(origin.equals(destination))
            throw new FistinFrameworkException("origin == destination");

        if(overrideName)
            destination.setName(origin.getName());
        destination.setPlayers(origin.getPlayers());

        this.remove(origin);
        this.addTeam(destination);
    }

    @Override
    public FistinTeam merge(@NotNull FistinTeam... toMerge)
    {
        if(toMerge.length < 2)
            throw new FistinFrameworkException("toMerge < 2! (%d)", toMerge.length);
        return this.merge(toMerge[0].getName(), toMerge);
    }

    @Override
    public FistinTeam merge(@NotNull String name, @NotNull FistinTeam... toMerge)
    {
        if(toMerge.length < 2)
            throw new FistinFrameworkException("toMerge < 2! (%d)", toMerge.length);

        final FistinTeam merged = new FistinTeam(name);

        for (FistinTeam fistinTeam : toMerge)
        {
            fistinTeam.getPlayers().forEach((player, fistinPlayer) -> merged.addNewPlayer(fistinPlayer));
            this.remove(fistinTeam);
        }

        this.addTeam(merged);
        return merged;
    }

    @Override
    public FistinTeam[] split(@NotNull FistinTeam origin, @NotNull Function<FistinPlayer, FistinTeam.SplitData> fillFunction, int numberOf)
    {
        final FistinTeam[] result = new FistinTeam[numberOf];
        for (FistinPlayer player : origin.getPlayers().values())
        {
            final FistinTeam.SplitData data = fillFunction.apply(player);
            final int index = data.getIndex();
            if(result[index] == null)
                result[index] = new FistinTeam(data.getName());
            result[index].addNewPlayer(player);
        }

        this.remove(origin);
        for (FistinTeam fistinTeam : result)
            this.addTeam(fistinTeam);

        return result;
    }
}
