package fr.fistin.fistinframework.game;

import fr.fistin.fistinframework.grade.PlayerGrade;
import fr.fistin.fistinframework.player.FistinPlayer;
import org.jetbrains.annotations.NotNull;

public interface GameManager
{
    void init();
    boolean isInitialized();
    void throwNotInitializedError();

    @NotNull Game game();
    void setGame(@NotNull Game game);

    void winGame(@NotNull FistinPlayer player, int expBonus, int coinsBonus);
    void looseGame(@NotNull FistinPlayer player, int expBonus, int coinsBonus);
    float gradeMultiplier(@NotNull PlayerGrade grade);
}
