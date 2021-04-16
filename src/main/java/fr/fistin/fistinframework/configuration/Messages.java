package fr.fistin.fistinframework.configuration;

import java.util.Locale;

public interface Messages
{
    String fixColor(String toFix);
    String getStartingGameMessage(Locale locale);
    String getStoppingGameMessage(Locale locale);
    String getWinnerMessage(Locale locale);
}
