package fr.fistin.fistinframework.impl;

import fr.fistin.api.plugin.providers.IBukkitPluginProvider;
import fr.fistin.fistinframework.IFistinFramework;
import fr.fistin.fistinframework.configuration.LanguageManager;
import fr.fistin.fistinframework.configuration.Messages;
import fr.fistin.fistinframework.player.FistinPlayer;
import org.bukkit.ChatColor;

import java.util.Locale;

class MessagesImpl implements Messages
{
    @Override
    public String fixColor(String toFix)
    {
        return ChatColor.translateAlternateColorCodes('&', toFix);
    }

    @Override
    public String getStartingGameMessage(IBukkitPluginProvider plugin, Locale locale)
    {
        return this.fixColor(this.getLanguageManager().getLanguage(plugin, locale).getTranslatedMessage("starting_game"));
    }

    @Override
    public String getStoppingGameMessage(IBukkitPluginProvider plugin, Locale locale)
    {
        return this.fixColor(this.getLanguageManager().getLanguage(plugin, locale).getTranslatedMessage("stopping_game"));
    }

    @Override
    public String getWinnerMessage(IBukkitPluginProvider plugin, Locale locale, FistinPlayer fistinPlayer)
    {
        return IFistinFramework.framework().mappings().getPlayerMappings().map(this.fixColor(this.getLanguageManager().getLanguage(plugin, locale).getTranslatedMessage("winner")), fistinPlayer);
    }

    private LanguageManager getLanguageManager()
    {
        return IFistinFramework.framework().languageManager();
    }
}
