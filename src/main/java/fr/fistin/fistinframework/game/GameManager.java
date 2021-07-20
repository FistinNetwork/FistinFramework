package fr.fistin.fistinframework.game;

import fr.fistin.fistinframework.grade.PlayerGrade;
import fr.fistin.fistinframework.player.FistinPlayer;
import fr.fistin.fistinframework.player.PlayerState;
import org.jetbrains.annotations.NotNull;

public interface GameManager
{
    void init();
    boolean isInitialized();
    void throwNotInitializedError();

    @NotNull Game game();
    void setGame(@NotNull Game game);

    GameState registerNewGameState(String name);
    GameState getGameState(String name);
    GameState getGameState(int id);

    PlayerState registerNewPlayerState(String name);
    PlayerState getPlayerState(String name);
    PlayerState getPlayerState(int id);

    void winGame(@NotNull FistinPlayer player, int expBonus, int coinsBonus);
    void looseGame(@NotNull FistinPlayer player, int expBonus, int coinsBonus);
    float gradeMultiplier(@NotNull PlayerGrade grade);
}
