package fr.fistin.fistinframework.game;

import fr.fistin.api.ILevelingProvider;
import fr.fistin.api.plugin.providers.IBukkitPluginProvider;
import fr.fistin.api.plugin.providers.PluginProviders;
import fr.fistin.fistinframework.IFistinFramework;
import fr.fistin.fistinframework.event.GameManagerInitEvent;
import fr.fistin.fistinframework.grade.LuckPermsToFistin;
import fr.fistin.fistinframework.grade.PlayerGrade;
import fr.fistin.fistinframework.player.FistinPlayer;
import fr.fistin.fistinframework.utils.FistinFrameworkException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public abstract class DefaultGameManager implements GameManager
{
    @Nullable
    protected ILevelingProvider cacheLeveling;
    @Nullable
    protected LuckPermsToFistin cacheLuckPermsToFistin;

    protected boolean init = false;
    protected Game game;

    @Override
    public void init()
    {
        this.init = true;
        IFistinFramework.framework().eventBus().handleEvent(() -> new GameManagerInitEvent(this));
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

    // Don't need any initialization

    @Override
    public void winGame(@NotNull FistinPlayer player, int expBonus, int coinsBonus)
    {
        final ILevelingProvider leveling = this.getLeveling();
        final LuckPermsToFistin luckPermsToFistin = this.getLuckPermsToFistin();

        leveling.addExp(player.getPlayer(), new Random().nextInt(15) + 15 + expBonus, this.gradeMultiplier(luckPermsToFistin.getGradeForPlayer(player)));
        leveling.addCoins(player.getPlayer(), new Random().nextInt(65) + 30 + coinsBonus, this.gradeMultiplier(luckPermsToFistin.getGradeForPlayer(player)));
    }

    @Override
    public void looseGame(@NotNull FistinPlayer player, int expBonus, int coinsBonus)
    {
        final ILevelingProvider leveling = this.getLeveling();
        final LuckPermsToFistin luckPermsToFistin = this.getLuckPermsToFistin();

        leveling.addExp(player.getPlayer(), new Random().nextInt(6) + new Random().nextInt(8), this.gradeMultiplier(luckPermsToFistin.getGradeForPlayer(player)));
        leveling.addCoins(player.getPlayer(), coinsBonus, this.gradeMultiplier(luckPermsToFistin.getGradeForPlayer(player)));
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

    public abstract IBukkitPluginProvider getPlugin();

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
