package fr.fistin.fistinframework.game;

import fr.fistin.fistinframework.grade.PlayerGrade;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface GameManager
{
    void init();
    boolean isInitialized();
    void throwNotInitializedError();

    @NotNull Game game();
    void setGame(@NotNull Game game);

    void winGame(@NotNull Player player, int expBonus, int coinsBonus);
    void looseGame(@NotNull Player player, int expBonus, int coinsBonus);
    float gradeMultiplier(PlayerGrade grade);
}
