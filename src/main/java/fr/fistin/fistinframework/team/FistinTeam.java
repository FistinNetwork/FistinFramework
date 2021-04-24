package fr.fistin.fistinframework.team;

import fr.fistin.fistinframework.player.FistinPlayer;
import fr.fistin.fistinframework.player.FistinPlayerContainer;
import fr.fistin.fistinframework.utils.FistinFrameworkException;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class FistinTeam implements FistinPlayerContainer
{
    private String name;
    private Map<Player, FistinPlayer> players = new HashMap<>();

    public FistinTeam(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }

    public void transferToAnotherTeam(FistinTeam anotherTeam, boolean overrideName)
    {
        if(this.equals(anotherTeam))
            throw new FistinFrameworkException("anotherTeam cannot be same!");

        if(overrideName)
            anotherTeam.name = this.name;
        anotherTeam.players = this.players;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FistinTeam that = (FistinTeam)o;

        if (!name.equals(that.name)) return false;
        return players.equals(that.players);
    }

    @Override
    public int hashCode()
    {
        int result = name.hashCode();
        result = 31 * result + players.hashCode();
        return result;
    }

    @Override
    public @Nullable FistinPlayer findPlayer(@NotNull Player player)
    {
        return this.players.get(player);
    }

    @Override
    public void addNewPlayer(@NotNull FistinPlayer player)
    {
        this.players.put(player.getPlayer(), player);
    }
}
