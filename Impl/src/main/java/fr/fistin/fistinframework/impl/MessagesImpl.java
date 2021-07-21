package fr.fistin.fistinframework.impl;

import fr.fistin.fistinframework.IFistinFramework;
import fr.fistin.fistinframework.command.FistinCommand;
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
    public String getPlayerJoin(Language language, FistinPlayer fistinPlayer, Game game)
    {
        return this.getMappings().getPlayerMappings().map(this.getMappings().getGameMappings().map(this.fixColor(this.getLanguageManager().translate(language, "player_join")), game), fistinPlayer);
    }

    @Override
    public String getStartingGame(Language language)
    {
        return this.fixColor(this.getLanguageManager().translate(language, "starting_game"));
    }

    @Override
    public String getStoppingGame(Language language)
    {
        return this.fixColor(this.getLanguageManager().translate(language, "stopping_game"));
    }

    @Override
    public String getWinner(Language language, FistinPlayer fistinPlayer)
    {
        return this.getMappings().getPlayerMappings().map(this.fixColor(this.getLanguageManager().translate(language, "winner")), fistinPlayer);
    }

    @Override
    public String getKickMessageAtEnd(Language language)
    {
        return this.fixColor(this.getLanguageManager().translate(language, "kick_message_at_end"));
    }

    @Override
    public String getPlayerSpecifiedNotFound(Language language)
    {
        return this.fixColor(this.getLanguageManager().translate(language, "player_specified_not_found"));
    }

    @Override
    public String getInvalidObjectName(Language language)
    {
        return this.fixColor(this.getLanguageManager().translate(language, "invalid_object_name"));
    }

    @Override
    public String getCommandPlayerRequired(Language language)
    {
        return this.fixColor(this.getLanguageManager().translate(language, "command_player_required"));
    }

    @Override
    public String getCommandMissingPermission(Language language)
    {
        return this.fixColor(this.getLanguageManager().translate(language, "command_missing_permission"));
    }

    @Override
    public String getCommandBadUsage(Language language, FistinCommand command)
    {
        return this.getMappings().getCommandMappings().map(this.fixColor(this.getLanguageManager().translate(language, "command_bad_usage")), command);
    }

    @Override
    public String getCommandError(Language language, FistinCommand command)
    {
        return this.getMappings().getCommandMappings().map(this.fixColor(this.getLanguageManager().translate(language, "command_error")), command);
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
