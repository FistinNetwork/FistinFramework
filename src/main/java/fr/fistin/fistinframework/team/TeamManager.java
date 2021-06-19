package fr.fistin.fistinframework.team;

import fr.fistin.fistinframework.player.FistinPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public interface TeamManager
{
    /**
     * Add a {@link FistinTeam}.
     * @param team the team to add.
     */
    void addTeam(@NotNull FistinTeam team);

    /**
     * Clear all {@link FistinTeam}s in this {@link TeamManager}.
     */
    void removeAll();

    /**
     * Get a {@link FistinTeam} by its name.
     * @param teamName the {@link FistinTeam}'s name.
     * @return the {@link FistinTeam}. Can be null if the name is invalid.
     */
    @Nullable FistinTeam getTeam(@NotNull String teamName);

    /**
     * Transfer all players from a team to a new team.
     * @param origin the original team.
     * @param destination the destination team.
     * @param overrideName if the destination team takes the name of the original team.
     */
    void transfer(@NotNull FistinTeam origin, @NotNull FistinTeam destination, boolean overrideName);

    /**
     * Merge all the teams in params to a new team.
     * @param toMerge teams to merge.
     * @return the merged team.
     */
    FistinTeam merge(@NotNull FistinTeam... toMerge);

    /**
     * Merge all the teams in params to a new team &amp; specify a new name.
     * @param name the name to set.
     * @param toMerge teams to merge.
     * @return the merged team.
     */
    FistinTeam merge(@NotNull String name, @NotNull FistinTeam... toMerge);

    /**
     * Split a team to a specified number of teams.
     * @param origin the original team that will be split.
     * @param fillFunction the function who transfer a {@link FistinPlayer} to a specific team.
     * @param numberOf number of teams to create.
     * @return all created teams.
     */
    FistinTeam[] split(@NotNull FistinTeam origin, @NotNull Function<FistinPlayer, FistinTeam.SplitData> fillFunction, int numberOf);
}
