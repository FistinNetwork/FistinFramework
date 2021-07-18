package fr.fistin.fistinframework.player;

public class Preferences
{
    private final String locale;

    public Preferences(String locale)
    {
        this.locale = locale;
    }

    public String getLocale()
    {
        return locale;
    }
}