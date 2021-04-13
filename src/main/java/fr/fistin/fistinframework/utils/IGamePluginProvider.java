package fr.fistin.fistinframework.utils;

import fr.fistin.api.plugin.PluginType;
import fr.fistin.api.plugin.providers.IPluginProvider;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface IGamePluginProvider extends IPluginProvider
{
    void winGame(Player player);
    void looseGame(Player player);
    float gradeMultiplier(PlayerGrade grade);

    @Override
    default @NotNull PluginType pluginType()
    {
        return PluginType.GAME;
    }
}
