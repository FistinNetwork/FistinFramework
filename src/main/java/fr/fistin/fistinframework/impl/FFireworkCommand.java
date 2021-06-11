package fr.fistin.fistinframework.impl;

import fr.fistin.api.utils.PluginLocation;
import fr.fistin.fistinframework.IFistinFramework;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FFireworkCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if(args.length == 1)
        {
            if(sender instanceof Player)
            {
                final PluginLocation loc = PluginLocation.getLocation(args[0]);
                if(loc != null)
                    IFistinFramework.framework().fireworkFactory().spawnFirework(loc, ((Player)sender).getLocation());
                else sender.sendMessage("\u00A7cFirework name not valid!\u00A7r");
                return true;
            }
        }
        return false;
    }
}
