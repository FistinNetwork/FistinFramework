package fr.fistin.fistinframework.team;

import fr.fistin.fistinframework.player.FistinPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public interface TeamManager
{
    void addTeam(@NotNull FistinTeam team);
    void removeAll();
    @Nullable FistinTeam getTeam(@NotNull String teamName);
    void transfer(@NotNull FistinTeam origin, @NotNull FistinTeam destination, boolean overrideName);
    FistinTeam merge(@NotNull FistinTeam... toMerge);
    FistinTeam merge(@NotNull String name, @NotNull FistinTeam... toMerge);
    FistinTeam[] split(@NotNull FistinTeam origin, @NotNull Function<FistinPlayer, FistinTeam.SplitData> fillFunction, int numberOf);
}
