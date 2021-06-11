package fr.fistin.fistinframework.grade;

import fr.fistin.fistinframework.player.FistinPlayer;
import net.luckperms.api.model.user.User;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public interface LuckPermsToFistin
{
    @NotNull PlayerGrade getGradeForPlayer(FistinPlayer player);
    @NotNull PlayerGrade getGradeForPlayer(UUID uuid);
    @NotNull PlayerGrade getGradeForPlayer(String playerName);
    @NotNull PlayerGrade getGradeForPlayer(User user);
}
