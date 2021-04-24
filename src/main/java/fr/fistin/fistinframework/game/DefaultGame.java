package fr.fistin.fistinframework.game;

import fr.fistin.fistinframework.IFistinFramework;
import fr.fistin.fistinframework.event.GameStateChangedEvent;
import fr.fistin.fistinframework.player.FistinPlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public abstract class DefaultGame implements Game
{
    protected final Map<Player, FistinPlayer> inLobbyPlayers = new HashMap<>();
    protected final Map<Player, FistinPlayer> playingPlayers = new HashMap<>();
    protected final Map<Player, FistinPlayer> spectatingPlayers = new HashMap<>();

    protected GameState gameState;
    protected int timer = -1;

    @Override
    public @NotNull GameState gameState()
    {
        return this.gameState;
    }

    @Override
    public void setGameState(@NotNull GameState gameState)
    {
        IFistinFramework.framework().eventBus().handleEvent(() -> new GameStateChangedEvent(this.gameState, this.gameState = gameState, this));
    }

    @Override
    public @NotNull Map<Player, FistinPlayer> inLobbyPlayers()
    {
        return this.inLobbyPlayers;
    }

    @Override
    public @NotNull Map<Player, FistinPlayer> playingPlayers()
    {
        return this.playingPlayers;
    }

    @Override
    public @NotNull Map<Player, FistinPlayer> spectatingPlayers()
    {
        return this.spectatingPlayers;
    }

    @Override
    public int players()
    {
        return this.playingPlayers.size() + this.inLobbyPlayers.size();
    }

    @Override
    public boolean checkPlayersCount()
    {
        return this.players() <= this.maxPlayers() && this.players() >= this.minPlayers();
    }

    @Override
    public int timerBeforeGameStart()
    {
        return this.timer;
    }
}
