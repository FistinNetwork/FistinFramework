package fr.fistin.fistinframework.impl;

import fr.fistin.fistinframework.grade.LuckPermsToFistin;
import fr.fistin.fistinframework.grade.PlayerGrade;
import fr.fistin.fistinframework.player.FistinPlayer;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedPermissionData;
import net.luckperms.api.model.user.User;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

class LuckPermsToFistinImpl implements LuckPermsToFistin
{
    private static final LuckPerms LUCK_PERMS = LuckPermsProvider.get();

    @Override
    public @NotNull PlayerGrade getGradeForPlayer(FistinPlayer player)
    {
        return this.getGradeForPlayer(player.getName());
    }

    @Override
    public @NotNull PlayerGrade getGradeForPlayer(UUID uuid)
    {
        return this.getGradeForPlayer(LUCK_PERMS.getUserManager().getUser(uuid));
    }

    @Override
    public @NotNull PlayerGrade getGradeForPlayer(String playerName)
    {
        return this.getGradeForPlayer(LUCK_PERMS.getUserManager().getUser(playerName));
    }

    @Override
    public @NotNull PlayerGrade getGradeForPlayer(User user)
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
