package fr.fistin.fistinframework.impl;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Multimap;
import com.google.common.collect.Table;
import fr.fistin.fistinframework.configuration.Language;
import fr.fistin.fistinframework.configuration.LanguageManager;
import fr.fistin.fistinframework.utils.IBukkitPluginProvider;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@ApiStatus.Internal
class LanguageManagerImpl implements LanguageManager
{
    private final Table<Locale, IBukkitPluginProvider, Language> table = HashBasedTable.create();
    private final Multimap<IBukkitPluginProvider, Locale> remainingLanguagesByPlugin = ArrayListMultimap.create();

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
        this.remainingLanguagesByPlugin.putAll(plugin, this.remainingLanguages);

        final Language defaultLanguage = new Language(Locale.ENGLISH, plugin);
        this.table.put(Locale.ENGLISH, plugin, defaultLanguage);

        for (Locale locale : locales)
        {
            if (this.remainingLanguagesByPlugin.containsEntry(plugin, locale))
            {
                this.table.put(locale, plugin, new Language(locale, plugin));
            }
        }

        for (Locale locale : this.table.column(plugin).keySet())
            this.remainingLanguagesByPlugin.remove(plugin, locale);
    }

    @Override
    public Language getLanguage(IBukkitPluginProvider plugin, Locale locale)
    {
        return this.table.get(locale, plugin);
    }

    @Override
    public void clean()
    {
        this.table.values().forEach(Language::clean);
        this.table.clear();
        this.remainingLanguagesByPlugin.clear();
        this.remainingLanguages.clear();
    }
}
