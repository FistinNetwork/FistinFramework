package fr.fistin.fistinframework.configuration;

import fr.fistin.api.plugin.providers.IBukkitPluginProvider;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Language
{
    private final Locale locale;
    private final String name;
    private final File file;
    private final Map<String, String> translatedMessages = new HashMap<>();

    public Language(Locale locale, IBukkitPluginProvider plugin)
    {
        this.locale = locale;
        this.name = this.locale.getLanguage();
        final String emplacement = "languages/" + this.name + ".yml";
        this.file = new File(plugin.getDataFolder(), emplacement);

        if(!this.file.exists())
        {
            this.file.getParentFile().mkdirs();
            plugin.saveResource(emplacement, false);
        }

        final FileConfiguration configuration = YamlConfiguration.loadConfiguration(this.file);
        for (String translation : configuration.getKeys(false))
            this.translatedMessages.put(translation, configuration.getString(translation));
    }

    public String getTranslatedMessage(String key)
    {
        return this.translatedMessages.get(key);
    }

    public Locale getLocale()
    {
        return this.locale;
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
