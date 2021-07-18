package fr.fistin.fistinframework.team;

import fr.fistin.fistinframework.player.FistinPlayer;
import fr.fistin.fistinframework.player.FistinPlayerContainer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class FistinTeam implements FistinPlayerContainer
{
    @NotNull
    private String name;

    @NotNull
    private Map<Player, FistinPlayer> players = new HashMap<>();

    public FistinTeam(@NotNull String name)
    {
        this.name = name;
    }

    public @NotNull String getName()
    {
        return this.name;
    }

    public @NotNull Map<Player, FistinPlayer> getPlayers()
    {
        return this.players;
    }

    public void setName(@NotNull String name)
    {
        this.name = name;
    }

    public void setPlayers(@NotNull Map<Player, FistinPlayer> players)
    {
        this.players = players;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;

        final FistinTeam that = (FistinTeam)o;

        return this.name.equals(that.name);
    }

    @Override
    public int hashCode()
    {
        return this.name.hashCode();
    }

    @Override
    public void add(@NotNull FistinPlayer player)
    {
        this.players.put(player.getPlayer(), player);
    }

    @Override
    public @Nullable FistinPlayer find(@NotNull Player player)
    {
        return this.players.get(player);
    }

    @Override
    public void remove(@NotNull FistinPlayer fistinPlayer)
    {
        this.players.remove(fistinPlayer.getPlayer(), fistinPlayer);
    }

    public interface SplitData
    {
        String getName();
        int getIndex();
    }
}
