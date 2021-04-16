package fr.fistin.fistinframework.configuration;

import fr.fistin.api.plugin.providers.IBukkitPluginProvider;

import java.util.Locale;

public interface LanguageManager
{
    Language getLanguage(Locale locale);
    void load(IBukkitPluginProvider plugin, Locale... locales);
}
