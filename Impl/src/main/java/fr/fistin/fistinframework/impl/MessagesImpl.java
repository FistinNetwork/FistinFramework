package fr.fistin.fistinframework.impl;

import fr.fistin.fistinframework.IFistinFramework;
import fr.fistin.fistinframework.configuration.ConfigurationMappings;
import fr.fistin.fistinframework.configuration.Language;
import fr.fistin.fistinframework.configuration.LanguageManager;
import fr.fistin.fistinframework.configuration.Messages;
import fr.fistin.fistinframework.game.Game;
import fr.fistin.fistinframework.player.FistinPlayer;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
class MessagesImpl implements Messages
{
    @Override
    public String fixColor(String toFix)
    {
        return ChatColor.translateAlternateColorCodes('&', toFix);
    }

    @Override
    public String getPlayerJoinMessage(Language language, FistinPlayer fistinPlayer, Game game)
    {
        return this.getMappings().getPlayerMappings().map(this.getMappings().getGameMappings().map(this.fixColor(this.getLanguageManager().translate(language, "player_join")), game), fistinPlayer);
    }

    @Override
    public String getStartingGameMessage(Language language)
    {
        return this.fixColor(this.getLanguageManager().translate(language, "starting_game"));
    }

    @Override
    public String getStoppingGameMessage(Language language)
    {
        return this.fixColor(this.getLanguageManager().translate(language, "stopping_game"));
    }

    @Override
    public String getWinnerMessage(Language language, FistinPlayer fistinPlayer)
    {
        return this.getMappings().getPlayerMappings().map(this.fixColor(this.getLanguageManager().translate(language, "winner")), fistinPlayer);
    }

    @Override
    public String getKickMessageAtEnd(Language language)
    {
        return this.fixColor(this.getLanguageManager().translate(language, "kick_message_at_end"));
    }

    @Override
    public String getPlayerRequiredMessage(Language language)
    {
        return this.fixColor(this.getLanguageManager().translate(language, "player_required"));
    }

    @Override
    public String getMissingPermissionMessage(Language language)
    {
        return this.fixColor(this.getLanguageManager().translate(language, "missing_permission"));
    }

    private ConfigurationMappings getMappings()
    {
        return IFistinFramework.framework().configurationMappings();
    }

    private LanguageManager getLanguageManager()
    {
        return IFistinFramework.framework().languageManager();
    }
}
