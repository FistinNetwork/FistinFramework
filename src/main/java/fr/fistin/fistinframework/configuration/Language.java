package fr.fistin.fistinframework.configuration;

import fr.fistin.fistinframework.IFistinFramework;
import fr.fistin.fistinframework.utils.Cleanable;
import fr.fistin.fistinframework.utils.IBukkitPluginProvider;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class Language implements Cleanable
{
    private final IBukkitPluginProvider plugin;
    private final String name;
    private final Path file;
    private final Map<String, String> translatedMessages = new HashMap<>();

    public Language(String name, IBukkitPluginProvider plugin)
    {
        this.plugin = plugin;
        this.name = name;
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
        return this.translatedMessages.get(key);
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

    public static Language defaultLanguage()
    {
        return IFistinFramework.framework().languageManager().getLanguage(IFistinFramework.framework(), "en");
    }

    public static Language globalLanguage()
    {
        final IFistinFramework framework = IFistinFramework.framework();
        return framework.languageManager().getLanguage(framework, framework.getConfig().getString("global_language"));
    }
}
