package fr.fistin.fistinframework.event;

import fr.fistin.fistinframework.eventbus.IFistinEvent;
import fr.fistin.fistinframework.game.GameManager;
import org.jetbrains.annotations.NotNull;

public class GameManagerInitEvent implements IFistinEvent
{
    private final GameManager gameManager;

    public GameManagerInitEvent(GameManager gameManager)
    {
        this.gameManager = gameManager;
    }

    public GameManager gameManager()
    {
        return this.gameManager;
    }

    @Override
    public @NotNull String getName()
    {
        return this.getClass().getSimpleName();
    }
}
