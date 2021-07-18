package fr.fistin.fistinframework.event;

import fr.fistin.fistinframework.eventbus.FistinEvent;
import fr.fistin.fistinframework.player.FistinPlayer;
import fr.fistin.fistinframework.player.PlayerState;
import org.jetbrains.annotations.NotNull;

public class PlayerStateChangedEvent implements FistinEvent
{
    private final PlayerState oldState;
    private final PlayerState newState;
    private final FistinPlayer player;

    public PlayerStateChangedEvent(PlayerState oldState, PlayerState newState, FistinPlayer player)
    {
        this.oldState = oldState;
        this.newState = newState;
        this.player = player;
    }

    public PlayerState oldState()
    {
        return this.oldState;
    }

    public PlayerState newState()
    {
        return this.newState;
    }

    public FistinPlayer player()
    {
        return this.player;
    }

    @Override
    public @NotNull String getName()
    {
        return this.getClass().getSimpleName();
    }
}
