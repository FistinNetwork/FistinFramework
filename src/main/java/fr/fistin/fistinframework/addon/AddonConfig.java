package fr.fistin.fistinframework.addon;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.plugin.Plugin;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.logging.Level;

public class AddonConfig<C>
{
    private final Gson gson;
    private final Path file;
    private final Class<C> type;
    private final C defaultConfig;
    private final Plugin plugin;
    private C config;

    public AddonConfig(String name, Class<C> type, C def, Plugin plugin)
    {
        this.file = Paths.get(plugin.getDataFolder().getAbsolutePath(), name + ".json");
        this.gson = new GsonBuilder()
                .disableHtmlEscaping()
                .serializeNulls()
                .setPrettyPrinting()
                .create();
        this.type = type;
        this.defaultConfig = this.config = def;
        this.plugin = plugin;
    }

    public void setConfig(C config)
    {
        this.config = config;
        this.save();
    }

    public C getConfig()
    {
        return this.config;
    }

    public void load()
    {
        try
        {
            if(Files.notExists(this.file))
            {
                this.save();
                this.load();
            }
            else
            {
                final StringBuilder sb = new StringBuilder();
                Files.readAllLines(this.file, StandardCharsets.UTF_8).forEach(s -> sb.append(s).append('\n'));
                final String json = sb.toString();
                final C conf = this.gson.fromJson(json, this.type);
                if(conf == null)
                {
                    Files.delete(this.file);
                    this.load();
                }
                else this.config = conf;
            }
        }
        catch (Exception e)
        {
            this.plugin.getLogger().log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public void save()
    {
        try
        {
            if(Files.notExists(this.file))
            {
                Files.createDirectories(this.file.getParent());
                Files.createFile(this.file);
            }
            Files.write(this.file, Collections.singletonList(this.gson.toJson(this.config)));
        }
        catch (Exception e)
        {
            this.plugin.getLogger().log(Level.SEVERE, e.getMessage(), e);
        }
    }
    
    public void reset()
    {
        this.setConfig(this.defaultConfig);
    }
}
