package fr.fistin.fistinframework.impl;

import fr.fistin.fistinframework.configuration.ConfigurationMapping;
import fr.fistin.fistinframework.configuration.ConfigurationMappings;
import fr.fistin.fistinframework.game.Game;
import fr.fistin.fistinframework.player.FistinPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

class ConfigurationMappingsImpl implements ConfigurationMappings
{
    private final ConfigurationMapping<FistinPlayer, String> playerMappings = new ConfigurationMapping<FistinPlayer, String>() {
        private final Map<String, Function<FistinPlayer, String>> mappings = new HashMap<>();

        @Override
        public @NotNull Map<String, Function<FistinPlayer, String>> mappings()
        {
            return this.mappings;
        }
    };

    private final ConfigurationMapping<Game, Integer> gameMappings = new ConfigurationMapping<Game, Integer>() {
        private final Map<String, Function<Game, Integer>> mappings = new HashMap<>();

        @Override
        public @NotNull Map<String, Function<Game, Integer>> mappings()
        {
            return this.mappings;
        }
    };

    private final ConfigurationMapping<Integer, String> timeMappings = new ConfigurationMapping<Integer, String>() {
        private final Map<String, Function<Integer, String>> mappings = new HashMap<>();

        @Override
        public @NotNull Map<String, Function<Integer, String>> mappings()
        {
            return this.mappings;
        }
    };

    ConfigurationMappingsImpl()
    {
        this.playerMappings.mappings().put("%PLAYER_NAME%", FistinPlayer::getName);
        this.gameMappings.mappings().put("%MAX_PLAYERS%", Game::maxPlayers);
        this.gameMappings.mappings().put("%MIN_PLAYERS%", Game::minPlayers);
        this.gameMappings.mappings().put("%PLAYERS%", Game::players);
        this.gameMappings.mappings().put("%ALIVE%", game -> game.playingPlayers().size());
        this.gameMappings.mappings().put("%START_TIMER%", Game::timerBeforeGameStart);
        this.timeMappings.mappings().put("%SECONDS%", time -> Integer.toString(time));
        this.timeMappings.mappings().put("%MINUTES%", time -> Integer.toString(time / 60));
    }

    @Override
    public @NotNull ConfigurationMapping<FistinPlayer, String> getPlayerMappings()
    {
        return this.playerMappings;
    }

    @Override
    public @NotNull ConfigurationMapping<Game, Integer> getGameMappings()
    {
        return this.gameMappings;
    }

    public @NotNull ConfigurationMapping<Integer, String> getTimeMappings()
    {
        return this.timeMappings;
    }
}
