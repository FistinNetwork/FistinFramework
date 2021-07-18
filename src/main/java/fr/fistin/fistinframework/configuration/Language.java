package fr.fistin.fistinframework.configuration;

import fr.fistin.fistinframework.IFistinFramework;
import fr.fistin.fistinframework.utils.Cleanable;
import fr.fistin.fistinframework.utils.IBukkitPluginProvider;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;

public class Language implements Cleanable
{
    public static final Language DEFAULT = IFistinFramework.framework().languageManager().getLanguage(IFistinFramework.framework(), Locale.ENGLISH);

    private final Locale locale;
    private final IBukkitPluginProvider plugin;
    private final String name;
    private final Path file;
    private final Map<String, String> translatedMessages = new HashMap<>();

    public Language(Locale locale, IBukkitPluginProvider plugin)
    {
        this.locale = locale;
        this.plugin = plugin;
        this.name = this.locale.getLanguage();
        this.file = this.plugin.getDataFolder().toPath().resolve("languages").resolve(this.name + ".yml");

        try
        {
            if(Files.notExists(this.file))
            {
                Files.createDirectories(this.file.getParent());
                this.plugin.saveResource("languages/" + this.name + ".yml", false);
            }

            final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(Files.newBufferedReader(this.file));
            for (String translation : configuration.getKeys(false))
                this.translatedMessages.put(translation, configuration.getString(translation));
        } catch (IOException e)
        {
            this.plugin.getLogger().log(Level.SEVERE, e.getMessage(), e);
        }
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

    public Path getFile()
    {
        return this.file;
    }

    @Override
    public void clean()
    {
        this.translatedMessages.clear();
    }
}
