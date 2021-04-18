package fr.fistin.fistinframework.impl;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import fr.fistin.api.plugin.providers.IBukkitPluginProvider;
import fr.fistin.fistinframework.configuration.Language;
import fr.fistin.fistinframework.configuration.LanguageManager;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

class LanguageManagerImpl implements LanguageManager
{
    private final Table<Locale, IBukkitPluginProvider, Language> table = HashBasedTable.create();

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
        this.table.put(Locale.ENGLISH, plugin, defaultLanguage);

        for (Locale locale : locales)
        {
            if (this.remainingLanguages.contains(locale))
            {
                this.table.put(locale, plugin, new Language(locale, plugin));
            }
        }

        for (Locale locale : this.table.column(plugin).keySet())
            this.remainingLanguages.remove(locale);
    }

    @Override
    public Language getLanguage(IBukkitPluginProvider plugin, Locale locale)
    {
        return this.table.get(locale, plugin);
    }
}
