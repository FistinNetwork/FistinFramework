package fr.fistin.fistinframework.configuration;

import fr.fistin.fistinframework.game.Game;
import fr.fistin.fistinframework.player.FistinPlayer;
import org.jetbrains.annotations.NotNull;

public interface ConfigurationMappings
{
    @NotNull ConfigurationMapping<Game, Integer> getGameMappings();
    @NotNull ConfigurationMapping<FistinPlayer, String> getPlayerMappings();
    @NotNull ConfigurationMapping<Integer, String> getTimeMappings();
}
