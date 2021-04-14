package fr.fistin.fistinframework.grade;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedPermissionData;
import net.luckperms.api.model.user.User;
import org.bukkit.entity.Player;

import java.util.UUID;

public class LuckPermsToFistin
{
    private static final LuckPerms LUCK_PERMS = LuckPermsProvider.get();

    public PlayerGrade getGradeForPlayer(Player player)
    {
        return this.getGradeForPlayer(player.getName());
    }

    public PlayerGrade getGradeForPlayer(UUID uuid)
    {
        return this.getGradeForPlayer(LUCK_PERMS.getUserManager().getUser(uuid));
    }

    public PlayerGrade getGradeForPlayer(String playerName)
    {
        return this.getGradeForPlayer(LUCK_PERMS.getUserManager().getUser(playerName));
    }

    public PlayerGrade getGradeForPlayer(User user)
    {
        if(user != null)
        {
            final CachedPermissionData data = user.getCachedData().getPermissionData();
            if(data.checkPermission(PlayerGrade.ADMIN.getName()).asBoolean())
                return PlayerGrade.ADMIN;
            else if(data.checkPermission(PlayerGrade.STAFF.getName()).asBoolean())
                return PlayerGrade.STAFF;
            else if(data.checkPermission(PlayerGrade.VIP.getName()).asBoolean())
                return PlayerGrade.VIP;
            else if(data.checkPermission(PlayerGrade.NORMAL.getName()).asBoolean())
                return PlayerGrade.NORMAL;
        }

        return PlayerGrade.NORMAL;
    }
}
