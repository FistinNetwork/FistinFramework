package fr.fistin.fistinframework.event;

import fr.fistin.fistinframework.eventbus.FistinEvent;
import fr.fistin.fistinframework.game.Game;
import fr.fistin.fistinframework.game.GameState;
import org.jetbrains.annotations.NotNull;

public class GameStateChangedEvent implements FistinEvent
{
    private final GameState oldState;
    private final GameState newState;
    private final Game game;

    public GameStateChangedEvent(GameState oldState, GameState newState, Game game)
    {
        this.oldState = oldState;
        this.newState = newState;
        this.game = game;
    }

    public GameState oldState()
    {
        return this.oldState;
    }

    public GameState newState()
    {
        return this.newState;
    }

    public Game game()
    {
        return this.game;
    }

    @Override
    public @NotNull String getName()
    {
        return this.getClass().getSimpleName();
    }
}
