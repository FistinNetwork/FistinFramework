package fr.fistin.fistinframework.configuration;

import fr.fistin.fistinframework.IFistinFramework;

import java.io.File;
import java.util.Locale;

public class Language
{
    private final Locale locale;
    private final String name;
    private final File file;

    public Language(Locale locale)
    {
        this.locale = locale;
        this.name = this.locale.getLanguage();
        this.file = new File(IFistinFramework.framework().getDataFolder(), "languages/" + this.name + ".yml");
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
