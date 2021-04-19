package fr.fistin.fistinframework.player;

import fr.fistin.fistinframework.IFistinFramework;
import fr.fistin.fistinframework.event.PlayerStateChangedEvent;
import fr.fistin.fistinframework.scoreboard.IScoreboard;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public abstract class DefaultFistinPlayer implements FistinPlayer
{
    protected Player player;
    protected PlayerState playerState;
    protected IScoreboard scoreboard;

    public DefaultFistinPlayer(Player player)
    {
        this.player = player;
    }

    @Override
    public @NotNull Player getPlayer()
    {
        return this.player;
    }

    @Override
    public void setPlayer(@NotNull Player player)
    {
        this.player = player;
    }

    @Override
    public @NotNull PlayerState playerState()
    {
        return this.playerState;
    }

    @Override
    public void setPlayerState(@NotNull PlayerState playerState)
    {
        IFistinFramework.framework().eventBus().handleEvent(() -> new PlayerStateChangedEvent(this.playerState, this.playerState = playerState, this));
    }

    @Override
    public @NotNull IScoreboard scoreboard()
    {
        return this.scoreboard;
    }

    @Override
    public void setScoreboard(@NotNull IScoreboard scoreboard)
    {
        this.scoreboard = scoreboard;
        this.scoreboard.createScoreboard();
    }
}
