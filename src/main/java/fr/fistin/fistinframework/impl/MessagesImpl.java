package fr.fistin.fistinframework.impl;

import fr.fistin.api.plugin.providers.IBukkitPluginProvider;
import fr.fistin.fistinframework.IFistinFramework;
import fr.fistin.fistinframework.configuration.ConfigurationMappings;
import fr.fistin.fistinframework.configuration.Language;
import fr.fistin.fistinframework.configuration.LanguageManager;
import fr.fistin.fistinframework.configuration.Messages;
import fr.fistin.fistinframework.game.Game;
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
    public String getPlayerJoinMessage(IBukkitPluginProvider plugin, Locale locale, FistinPlayer fistinPlayer, Game game)
    {
        return this.getPlayerJoinMessage(this.getLanguageManager().getLanguage(plugin, locale), fistinPlayer, game);
    }

    @Override
    public String getStartingGameMessage(IBukkitPluginProvider plugin, Locale locale)
    {
        return this.getStartingGameMessage(this.getLanguageManager().getLanguage(plugin, locale));
    }

    @Override
    public String getStoppingGameMessage(IBukkitPluginProvider plugin, Locale locale)
    {
        return this.getStoppingGameMessage(this.getLanguageManager().getLanguage(plugin, locale));
    }

    @Override
    public String getWinnerMessage(IBukkitPluginProvider plugin, Locale locale, FistinPlayer fistinPlayer)
    {
        return this.getWinnerMessage(this.getLanguageManager().getLanguage(plugin, locale), fistinPlayer);
    }

    @Override
    public String getKickMessageAtEnd(IBukkitPluginProvider plugin, Locale locale)
    {
        return this.getKickMessageAtEnd(this.getLanguageManager().getLanguage(plugin, locale));
    }

    @Override
    public String getPlayerJoinMessage(Language language, FistinPlayer fistinPlayer, Game game)
    {
        return this.getMappings().getPlayerMappings().map(this.getMappings().getGameMappings().map(this.fixColor(language.getTranslatedMessage("player_join")), game), fistinPlayer);
    }

    @Override
    public String getStartingGameMessage(Language language)
    {
        return this.fixColor(language.getTranslatedMessage("starting_game"));
    }

    @Override
    public String getStoppingGameMessage(Language language)
    {
        return this.fixColor(language.getTranslatedMessage("stopping_game"));
    }

    @Override
    public String getWinnerMessage(Language language, FistinPlayer fistinPlayer)
    {
        return this.getMappings().getPlayerMappings().map(this.fixColor(language.getTranslatedMessage("winner")), fistinPlayer);
    }

    @Override
    public String getKickMessageAtEnd(Language language)
    {
        return this.fixColor(language.getTranslatedMessage("kick_message_at_end"));
    }

    private ConfigurationMappings getMappings()
    {
        return IFistinFramework.framework().mappings();
    }

    private LanguageManager getLanguageManager()
    {
        return IFistinFramework.framework().languageManager();
    }
}
