package fr.fistin.fistinframework.configuration;

import fr.fistin.api.plugin.providers.IBukkitPluginProvider;
import fr.fistin.fistinframework.game.Game;
import fr.fistin.fistinframework.player.FistinPlayer;

import java.util.Locale;

public interface Messages
{
    String fixColor(String toFix);
    String getPlayerJoinMessage(IBukkitPluginProvider plugin, Locale locale, FistinPlayer fistinPlayer, Game game);
    String getStartingGameMessage(IBukkitPluginProvider plugin, Locale locale);
    String getStoppingGameMessage(IBukkitPluginProvider plugin, Locale locale);
    String getWinnerMessage(IBukkitPluginProvider plugin, Locale locale, FistinPlayer fistinPlayer);
    String getKickMessageAtEnd(IBukkitPluginProvider plugin, Locale locale);

    String getPlayerJoinMessage(Language language, FistinPlayer fistinPlayer, Game game);
    String getStartingGameMessage(Language language);
    String getStoppingGameMessage(Language language);
    String getWinnerMessage(Language language, FistinPlayer fistinPlayer);
    String getKickMessageAtEnd(Language language);
}
