package fr.fistin.fistinframework.game;

import fr.fistin.api.ILevelingProvider;
import fr.fistin.api.plugin.providers.PluginProviders;
import fr.fistin.fistinframework.IFistinFramework;
import fr.fistin.fistinframework.event.GameManagerInitEvent;
import fr.fistin.fistinframework.grade.LuckPermsToFistin;
import fr.fistin.fistinframework.grade.PlayerGrade;
import fr.fistin.fistinframework.player.FistinPlayer;
import fr.fistin.fistinframework.player.PlayerState;
import fr.fistin.fistinframework.utils.FistinFrameworkException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public abstract class DefaultGameManager implements GameManager
{
    @Nullable
    protected ILevelingProvider cacheLeveling;
    @Nullable
    protected LuckPermsToFistin cacheLuckPermsToFistin;

    protected boolean init = false;
    protected Game game;

    private final Map<String, GameState> gameStatesByName = new HashMap<>();
    private final Map<Integer, GameState> gameStatesByID = new HashMap<>();
    private final Map<String, PlayerState> playerStatesByName = new HashMap<>();
    private final Map<Integer, PlayerState> playerStatesByID = new HashMap<>();
    private int gameStateIDIncrement = 0;
    private int playerStateIDIncrement = 0;

    @Override
    public void init()
    {
        this.init = true;
        this.registerNewPlayerState("IN_LOBBY");
        IFistinFramework.framework().fistinEventBus().handleEvent(() -> new GameManagerInitEvent(this));
    }

    @Override
    public boolean isInitialized()
    {
        return this.init;
    }

    @Override
    public void throwNotInitializedError()
    {
        throw new FistinFrameworkException("GameManager must be initialized before any action");
    }

    @Override
    public @NotNull Game game()
    {
        if(!this.init)
            this.throwNotInitializedError();
        return this.game;
    }

    @Override
    public void setGame(@NotNull Game game)
    {
        if(!this.init)
            this.throwNotInitializedError();
        this.game = game;
    }

    @Override
    public GameState getGameState(int id)
    {
        if(!this.init)
            this.throwNotInitializedError();
        return this.gameStatesByID.get(id);
    }

    @Override
    public GameState getGameState(String name)
    {
        if(!this.init)
            this.throwNotInitializedError();
        return this.gameStatesByName.get(name);
    }

    @Override
    public PlayerState getPlayerState(int id)
    {
        if(!this.init)
            this.throwNotInitializedError();
        return this.playerStatesByID.get(id);
    }

    @Override
    public PlayerState getPlayerState(String name)
    {
        if(!this.init)
            this.throwNotInitializedError();
        return this.playerStatesByName.get(name);
    }

    // Don't need any initialization

    @Override
    public GameState registerNewGameState(String name)
    {
        final GameState state = new GameState(name, this.gameStateIDIncrement++);
        this.gameStatesByID.put(state.getID(), state);
        this.gameStatesByName.put(state.getName(), state);
        return state;
    }

    @Override
    public PlayerState registerNewPlayerState(String name)
    {
        final PlayerState state = new PlayerState(name, this.playerStateIDIncrement++);
        this.playerStatesByID.put(state.getID(), state);
        this.playerStatesByName.put(state.getName(), state);
        return state;
    }

    @Override
    public void winGame(@NotNull FistinPlayer player, int expBonus, int coinsBonus)
    {
        final ILevelingProvider leveling = this.getLeveling();
        final LuckPermsToFistin luckPermsToFistin = this.getLuckPermsToFistin();

        leveling.addExp(player.getUniqueId(), new Random().nextInt(15) + 15 + expBonus, this.gradeMultiplier(luckPermsToFistin.getGradeForPlayer(player)));
        leveling.addCoins(player.getUniqueId(), new Random().nextInt(65) + 30 + coinsBonus, this.gradeMultiplier(luckPermsToFistin.getGradeForPlayer(player)));
    }

    @Override
    public void looseGame(@NotNull FistinPlayer player, int expBonus, int coinsBonus)
    {
        final ILevelingProvider leveling = this.getLeveling();
        final LuckPermsToFistin luckPermsToFistin = this.getLuckPermsToFistin();

        leveling.addExp(player.getUniqueId(), new Random().nextInt(6) + new Random().nextInt(8), this.gradeMultiplier(luckPermsToFistin.getGradeForPlayer(player)));
        leveling.addCoins(player.getUniqueId(), coinsBonus, this.gradeMultiplier(luckPermsToFistin.getGradeForPlayer(player)));
    }

    @Override
    public float gradeMultiplier(@NotNull PlayerGrade grade)
    {
        switch (grade)
        {
            case NORMAL:
                return 1f;
            case STAFF:
                return 1.4f;
            case VIP:
                return 1.5f;
            case ADMIN:
                return 1.6f;
        }
        return 1f;
    }

    public abstract IGamePluginProvider getPlugin();

    @NotNull
    protected ILevelingProvider getLeveling()
    {
        return this.cacheLeveling != null ? this.cacheLeveling : (this.cacheLeveling = PluginProviders.getProvider(ILevelingProvider.class));
    }

    @NotNull
    protected LuckPermsToFistin getLuckPermsToFistin()
    {
        return this.cacheLuckPermsToFistin != null ? this.cacheLuckPermsToFistin : (this.cacheLuckPermsToFistin = IFistinFramework.framework().luckPermsToFistin());
    }
}
