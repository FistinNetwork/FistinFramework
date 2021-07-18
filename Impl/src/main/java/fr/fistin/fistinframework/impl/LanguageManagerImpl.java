package fr.fistin.fistinframework.impl;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import fr.fistin.fistinframework.IFistinFramework;
import fr.fistin.fistinframework.configuration.Language;
import fr.fistin.fistinframework.configuration.LanguageManager;
import fr.fistin.fistinframework.utils.IBukkitPluginProvider;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
class LanguageManagerImpl implements LanguageManager
{
    private final Table<String, IBukkitPluginProvider, Language> table = HashBasedTable.create();

    @Override
    public void load(IBukkitPluginProvider plugin, String... locales)
    {
        final Language defaultLanguage = new Language("en", plugin);
        this.table.put("en", plugin, defaultLanguage);

        for (String locale : locales)
            this.table.put(locale, plugin, new Language(locale, plugin));
    }

    @Override
    public Language getLanguage(IBukkitPluginProvider plugin, String locale)
    {
        return this.table.get(locale, plugin);
    }

    @Override
    public String translate(Language language, String key)
    {
        final String first = language.getTranslatedMessage(key);

        if(first != null && !first.isEmpty())
            return first;

        final String second = this.getLanguage(language.getPlugin(), "en").getTranslatedMessage(key);

        if(second != null && !second.isEmpty())
            return second;

        final String third = this.getLanguage(IFistinFramework.framework(), language.getName()).getTranslatedMessage(key);

        if(third != null && !third.isEmpty())
            return third;

        final String fourth = Language.globalLanguage().getTranslatedMessage(key);

        if(fourth != null && !fourth.isEmpty())
            return fourth;

        final String fifth = Language.defaultLanguage().getTranslatedMessage(key);

        if(fifth != null && !fifth.isEmpty())
            return fifth;

        return "";
    }

    @Override
    public void clean()
    {
        this.table.values().forEach(Language::clean);
        this.table.clear();
    }
}
