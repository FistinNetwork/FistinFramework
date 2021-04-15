package fr.fistin.fistinframework.grade;

import net.luckperms.api.model.user.User;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public interface LuckPermsToFistin
{
    @NotNull PlayerGrade getGradeForPlayer(Player player);
    @NotNull PlayerGrade getGradeForPlayer(UUID uuid);
    @NotNull PlayerGrade getGradeForPlayer(String playerName);
    @NotNull PlayerGrade getGradeForPlayer(User user);
}
