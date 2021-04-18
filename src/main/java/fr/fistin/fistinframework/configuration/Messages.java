package fr.fistin.fistinframework.configuration;

import fr.fistin.api.plugin.providers.IBukkitPluginProvider;

import java.util.List;
import java.util.Locale;

public interface Messages
{
    String fixColor(String toFix);
    String getStartingGameMessage(IBukkitPluginProvider plugin, Locale locale);
    String getStoppingGameMessage(IBukkitPluginProvider plugin, Locale locale);
    String getWinnerMessage(IBukkitPluginProvider plugin, Locale locale);

    void broadcastToPlayers(List<LanguageContainer> containers, String key);
}