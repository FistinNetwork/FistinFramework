package fr.fistin.fistinframework.player;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface FistinPlayerContainer
{
    @Nullable FistinPlayer findPlayer(@NotNull Player player);
    void addNewPlayer(@NotNull FistinPlayer player);
}
