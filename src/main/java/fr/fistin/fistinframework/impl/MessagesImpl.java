package fr.fistin.fistinframework.impl;

import fr.fistin.fistinframework.configuration.Messages;
import org.bukkit.ChatColor;

public class MessagesImpl implements Messages
{
    @Override
    public String fixColor(String toFix)
    {
        return ChatColor.translateAlternateColorCodes('&', toFix);
    }
}
