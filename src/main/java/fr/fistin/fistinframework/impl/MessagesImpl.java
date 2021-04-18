package fr.fistin.fistinframework.impl;

import fr.fistin.api.plugin.providers.IBukkitPluginProvider;
import fr.fistin.fistinframework.IFistinFramework;
import fr.fistin.fistinframework.configuration.LanguageContainer;
import fr.fistin.fistinframework.configuration.LanguageManager;
import fr.fistin.fistinframework.configuration.Messages;
import org.bukkit.ChatColor;

import java.util.List;
import java.util.Locale;

public class MessagesImpl implements Messages
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
    public String getWinnerMessage(IBukkitPluginProvider plugin, Locale locale)
    {
        return this.fixColor(this.getLanguageManager().getLanguage(plugin, locale).getTranslatedMessage("winner"));
    }

    @Override
    public void broadcastToPlayers(List<LanguageContainer> containers, String key)
    {
        containers.forEach(container -> container.getPlayer().sendMessage(container.getSelectedLanguage().getTranslatedMessage(key)));
    }

    private LanguageManager getLanguageManager()
    {
        return IFistinFramework.framework().languageManager();
    }
}
