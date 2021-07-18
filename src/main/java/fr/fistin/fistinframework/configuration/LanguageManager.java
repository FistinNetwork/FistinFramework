package fr.fistin.fistinframework.configuration;

import fr.fistin.fistinframework.utils.Cleanable;
import fr.fistin.fistinframework.utils.IBukkitPluginProvider;

public interface LanguageManager extends Cleanable
{
    Language getLanguage(IBukkitPluginProvider plugin, String locale);
    void load(IBukkitPluginProvider plugin, String... locales);
    String translate(Language language, String key);
}
