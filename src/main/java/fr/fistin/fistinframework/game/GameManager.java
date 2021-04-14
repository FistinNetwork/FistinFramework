package fr.fistin.fistinframework.game;

import fr.fistin.fistinframework.grade.PlayerGrade;
import org.bukkit.entity.Player;

public interface GameManager
{
    void init();
    boolean isInitialized();
    void throwNotInitializedError();

    void winGame(Player player, int expBonus, int coinsBonus);
    void looseGame(Player player, int expBonus, int coinsBonus);
    float gradeMultiplier(PlayerGrade grade);
}
