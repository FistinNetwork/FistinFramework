package fr.fistin.fistinframework.player;

import fr.fistin.api.utils.IIdentifiable;
import org.jetbrains.annotations.NotNull;

public class PlayerState implements IIdentifiable
{
    private final String name;
    private final int id;

    public PlayerState(String name, int id)
    {
        this.name = name;
        this.id = id;
    }

    @Override
    public @NotNull String getName()
    {
        return this.name;
    }

    @Override
    public int getID()
    {
        return this.id;
    }
}
