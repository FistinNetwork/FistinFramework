package fr.fistin.fistinframework.impl;

import fr.fistin.fistinframework.configuration.Language;
import fr.fistin.fistinframework.configuration.LanguageManager;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

class LanguageManagerImpl implements LanguageManager
{
    private final Map<Locale, Language> languages = new HashMap<>();

    @Override
    public Language getLanguage(Locale locale)
    {
        return this.languages.getOrDefault(locale, this.languages.get(Locale.ENGLISH));
    }
}
