package fr.fistin.fistinframework.configuration;

import fr.fistin.fistinframework.utils.Cleanable;
import fr.fistin.fistinframework.utils.IBukkitPluginProvider;

import java.util.Locale;

public interface LanguageManager extends Cleanable
{
    Language getLanguage(IBukkitPluginProvider plugin, Locale locale);
    void load(IBukkitPluginProvider plugin, Locale... locales);
}
