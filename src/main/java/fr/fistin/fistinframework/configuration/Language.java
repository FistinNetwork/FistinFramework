package fr.fistin.fistinframework.configuration;

import fr.fistin.api.plugin.providers.IBukkitPluginProvider;
import fr.fistin.fistinframework.IFistinFramework;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Language
{
    private final Locale locale;
    private final IBukkitPluginProvider plugin;
    private final String name;
    private final File file;
    private final Map<String, String> translatedMessages = new HashMap<>();

    public Language(Locale locale, IBukkitPluginProvider plugin)
    {
        this.locale = locale;
        this.plugin = plugin;
        this.name = this.locale.getLanguage();
        final String emplacement = "languages/" + this.name + ".yml";
        this.file = new File(this.plugin.getDataFolder(), emplacement);

        if(!this.file.exists())
        {
            this.file.getParentFile().mkdirs();
            this.plugin.saveResource(emplacement, false);
        }

        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(this.file);
        for (String translation : configuration.getKeys(false))
            this.translatedMessages.put(translation, configuration.getString(translation));
    }

    public String getTranslatedMessage(String key)
    {
        final IFistinFramework framework = IFistinFramework.framework();
        final LanguageManager languageManager = framework.languageManager();
        final String unsafe = this.translatedMessages.getOrDefault(key, languageManager.getLanguage(this.plugin, Locale.ENGLISH).getTranslatedMessage(key));
        if(unsafe != null || this.translatedMessages.containsKey(key))
            return unsafe;
        else
        {
            final String fallback = languageManager.getLanguage(framework, this.locale).getTranslatedMessage(key);
            if(fallback != null)
                return fallback;
            else return languageManager.getLanguage(framework, Locale.ENGLISH).getTranslatedMessage(key);
        }
    }

    public Locale getLocale()
    {
        return this.locale;
    }

    public IBukkitPluginProvider getPlugin()
    {
        return this.plugin;
    }

    public String getName()
    {
        return this.name;
    }

    public File getFile()
    {
        return this.file;
    }
}
