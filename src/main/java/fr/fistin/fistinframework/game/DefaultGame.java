package fr.fistin.fistinframework.game;

import fr.fistin.fistinframework.IFistinFramework;
import fr.fistin.fistinframework.event.GameStateChangedEvent;
import fr.fistin.fistinframework.player.FistinPlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public abstract class DefaultGame implements Game
{
    protected final Map<Player, FistinPlayer> inLobbyPlayers = new HashMap<>();
    protected final Map<Player, FistinPlayer> playingPlayers = new HashMap<>();
    protected final Map<Player, FistinPlayer> spectatingPlayers = new HashMap<>();

    protected GameState gameState;
    protected GameManager gameManager;
    protected int timer = -1;

    protected DefaultGame(GameManager gameManager)
    {
        this.gameManager = gameManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull GameState gameState()
    {
        return this.gameState;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setGameState(@NotNull GameState gameState)
    {
        IFistinFramework.framework().fistinEventBus().handleEvent(() -> new GameStateChangedEvent(this.gameState, this.gameState = gameState, this));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changeGameState(@NotNull String newState)
    {
        this.setGameState(this.gameManager().getGameState(newState));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changeGameState(int newState)
    {
        this.setGameState(this.gameManager().getGameState(newState));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Map<Player, FistinPlayer> inLobbyPlayers()
    {
        return this.inLobbyPlayers;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Map<Player, FistinPlayer> playingPlayers()
    {
        return this.playingPlayers;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Map<Player, FistinPlayer> spectatingPlayers()
    {
        return this.spectatingPlayers;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int players()
    {
        return this.playingPlayers.size() + this.inLobbyPlayers.size();
    }

    @Override
    public @Nullable FistinPlayer find(@NotNull Player player)
    {
        return this.playingPlayers.getOrDefault(player, this.inLobbyPlayers.getOrDefault(player, this.spectatingPlayers.get(player)));
    }

    @Override
    public void add(@NotNull FistinPlayer player)
    {
        player.changePlayerState("IN_LOBBY");
        this.inLobbyPlayers.put(player.getPlayer(), player);
        final IFistinFramework framework = IFistinFramework.framework();
        this.sendToAllPlayers((player1, fistinPlayer) -> player1.sendMessage(framework.messages().getPlayerJoin(fistinPlayer.getSelectedLanguage(), fistinPlayer, this)));
    }

    @Override
    public void remove(@NotNull FistinPlayer player)
    {
        this.playingPlayers.remove(player.getPlayer());
        this.inLobbyPlayers.remove(player.getPlayer());
        this.spectatingPlayers.remove(player.getPlayer());
    }

    protected void sendToAllPlayers(BiConsumer<Player, FistinPlayer> consumer)
    {
        this.inLobbyPlayers.forEach(consumer);
        this.playingPlayers.forEach(consumer);
        this.spectatingPlayers.forEach(consumer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkPlayersCount()
    {
        return this.players() <= this.maxPlayers() && this.players() >= this.minPlayers();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start()
    {
        if(this.playingPlayers.size() != 0)
        {
            this.playingPlayers.forEach((player, fistinPlayer) -> player.kickPlayer(IFistinFramework.framework().messages().getKickMessageAtEnd(fistinPlayer.getSelectedLanguage())));
            this.playingPlayers.clear();
        }

        if(this.checkPlayersCount())
        {
            this.changeGameState("STARTING");
            this.sendToAllPlayers((player, fistinPlayer) -> player.sendMessage(IFistinFramework.framework().messages().getStartingGame(fistinPlayer.getSelectedLanguage())));
            this.time();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int timerBeforeGameStart()
    {
        return this.timer;
    }

   /**
    * {@inheritDoc}
    */
   @Override
   public GameManager gameManager()
   {
       return this.gameManager;
   }

    /*
     * {@inheritDoc}
     */
    @Override
    public void clean()
    {
        this.inLobbyPlayers.clear();
        this.playingPlayers.clear();
        this.spectatingPlayers.clear();
    }
}
