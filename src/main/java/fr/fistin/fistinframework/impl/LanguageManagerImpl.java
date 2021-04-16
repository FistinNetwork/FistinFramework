package fr.fistin.fistinframework.impl;

import fr.fistin.api.plugin.providers.IBukkitPluginProvider;
import fr.fistin.fistinframework.configuration.Language;
import fr.fistin.fistinframework.configuration.LanguageManager;
import org.jetbrains.annotations.ApiStatus;

import java.util.*;

class LanguageManagerImpl implements LanguageManager
{
    private final Map<Locale, Language> languages = new HashMap<>();

    @ApiStatus.Internal
    private final List<Locale> remainingLanguages = new ArrayList<>();

    public LanguageManagerImpl()
    {
        this.remainingLanguages.add(Locale.FRENCH);
        this.remainingLanguages.add(Locale.GERMAN);
        this.remainingLanguages.add(Locale.ITALIAN);
        this.remainingLanguages.add(Locale.JAPANESE);
        this.remainingLanguages.add(Locale.KOREAN);
        this.remainingLanguages.add(Locale.CHINESE);
    }

    @Override
    public void load(IBukkitPluginProvider plugin, Locale... locales)
    {
        final Language defaultLanguage = new Language(Locale.ENGLISH, plugin);
        this.languages.put(Locale.ENGLISH, defaultLanguage);

        for (Locale locale : locales)
        {
            if (this.remainingLanguages.contains(locale))
                this.languages.put(locale, new Language(locale, plugin));
        }

        for (Locale locale : this.languages.keySet())
            this.remainingLanguages.remove(locale);
    }

    @Override
    public Language getLanguage(Locale locale)
    {
        return this.languages.getOrDefault(locale, this.languages.get(Locale.ENGLISH));
    }
}
