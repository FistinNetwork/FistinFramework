package fr.fistin.fistinframework.addon;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.plugin.Plugin;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.logging.Level;

public class AddonConfig<C>
{
    private final Gson gson;
    private final File file;
    private final Class<C> type;
    private final C defaultConfig;
    private final Plugin plugin;
    private C config;

    public AddonConfig(String name, Class<C> type, C def, Plugin plugin)
    {
        this.file = new File(plugin.getDataFolder(), name + ".json");
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
            if(!this.file.exists())
            {
                this.save();
                this.load();
            }
            else
            {
                final StringBuilder sb = new StringBuilder();
                Files.readAllLines(this.file.toPath(), StandardCharsets.UTF_8).forEach(s -> sb.append(s).append('\n'));
                final String json = sb.toString();
                final C conf = this.gson.fromJson(json, this.type);
                if(conf == null)
                {
                    this.file.delete();
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
            if(!this.file.exists())
                this.file.getParentFile().mkdirs();
            this.file.createNewFile();
            final BufferedWriter writer = new BufferedWriter(new FileWriter(this.file));
            writer.write(this.gson.toJson(this.config));
            writer.close();
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
