package fr.fistin.fistinframework.configuration;

import org.bukkit.command.CommandSender;

public interface LanguageContainer
{
    Language getSelectedLanguage();
    CommandSender getPlayer();
}
