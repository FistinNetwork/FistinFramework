package fr.fistin.fistinframework.game;

import fr.fistin.api.ILevelingProvider;
import fr.fistin.api.plugin.providers.IBukkitPluginProvider;
import fr.fistin.api.plugin.providers.PluginProviders;
import fr.fistin.fistinframework.IFistinFramework;
import fr.fistin.fistinframework.event.GameManagerInitEvent;
import fr.fistin.fistinframework.utils.FistinFrameworkException;
import fr.fistin.fistinframework.grade.LuckPermsToFistin;
import fr.fistin.fistinframework.grade.PlayerGrade;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public abstract class DefaultGameManager implements GameManager
{
    protected boolean init = false;

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

    // Don't need any initialization

    @Override
    public void winGame(@NotNull Player player, int expBonus, int coinsBonus)
    {
        final ILevelingProvider leveling = PluginProviders.getProvider(ILevelingProvider.class);
        final LuckPermsToFistin luckPermsToFistin = IFistinFramework.framework().luckPermsToFistin();

        leveling.addExp(player, new Random().nextInt(15) + 15 + expBonus, this.gradeMultiplier(luckPermsToFistin.getGradeForPlayer(player)));
        leveling.addCoins(player, new Random().nextInt(65) + 30 + coinsBonus, this.gradeMultiplier(luckPermsToFistin.getGradeForPlayer(player)));
    }

    @Override
    public void looseGame(@NotNull Player player, int expBonus, int coinsBonus)
    {
        final ILevelingProvider leveling = PluginProviders.getProvider(ILevelingProvider.class);
        final LuckPermsToFistin luckPermsToFistin = IFistinFramework.framework().luckPermsToFistin();

        leveling.addExp(player, new Random().nextInt(6) + new Random().nextInt(8), this.gradeMultiplier(luckPermsToFistin.getGradeForPlayer(player)));
        leveling.addCoins(player, coinsBonus, this.gradeMultiplier(luckPermsToFistin.getGradeForPlayer(player)));
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
}
