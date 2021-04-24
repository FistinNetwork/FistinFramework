package fr.fistin.fistinframework.impl.team;

import fr.fistin.api.plugin.providers.IBukkitPluginProvider;
import fr.fistin.fistinframework.team.FistinTeam;
import fr.fistin.fistinframework.team.TeamManager;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

@ApiStatus.Internal
public class TeamManagerImpl implements TeamManager
{
    private final Map<IBukkitPluginProvider, Map<String, FistinTeam>> teams = new HashMap<>();

    public void addTeam(@NotNull IBukkitPluginProvider caller, @NotNull FistinTeam team)
    {
        if(this.teams.containsKey(caller))
            this.teams.get(caller).put(team.getName(), team);
        else
        {
            final Map<String, FistinTeam> toPut = new HashMap<>();
            toPut.put(team.getName(), team);
            this.teams.put(caller, toPut);
        }
    }

    public void removeAll(@NotNull IBukkitPluginProvider caller)
    {
        this.getTeams(caller).clear();
        this.teams.remove(caller);
    }

    public @NotNull Map<String, FistinTeam> getTeams(IBukkitPluginProvider caller)
    {
        return this.teams.getOrDefault(caller, new HashMap<>());
    }

    public @Nullable FistinTeam getTeam(IBukkitPluginProvider caller, String teamName)
    {
        return this.getTeams(caller).get(teamName);
    }
}
