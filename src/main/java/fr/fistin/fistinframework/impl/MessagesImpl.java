package fr.fistin.fistinframework.impl;

import fr.fistin.fistinframework.IFistinFramework;
import fr.fistin.fistinframework.configuration.LanguageManager;
import fr.fistin.fistinframework.configuration.Messages;
import org.bukkit.ChatColor;

import java.util.Locale;

public class MessagesImpl implements Messages
{
    @Override
    public String fixColor(String toFix)
    {
        return ChatColor.translateAlternateColorCodes('&', toFix);
    }

    @Override
    public String getStartingGameMessage(Locale locale)
    {
        return this.fixColor(this.getLanguageManager().getLanguage(locale).getTranslatedMessage("starting_game"));
    }

    @Override
    public String getStoppingGameMessage(Locale locale)
    {
        return this.fixColor(this.getLanguageManager().getLanguage(locale).getTranslatedMessage("stopping_game"));
    }

    @Override
    public String getWinnerMessage(Locale locale)
    {
        return this.fixColor(this.getLanguageManager().getLanguage(locale).getTranslatedMessage("winner"));
    }

    private LanguageManager getLanguageManager()
    {
        return IFistinFramework.framework().languageManager();
    }
}
