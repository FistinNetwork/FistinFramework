package fr.fistin.fistinframework.impl;

import fr.fistin.api.plugin.providers.PluginProviders;
import fr.fistin.api.utils.PluginLocation;
import fr.fistin.fistinframework.IFistinFramework;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

final class DebugCommand implements CommandExecutor
{
    private final IFistinFramework framework;

    DebugCommand(IFistinFramework framework)
    {
        this.framework = framework;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if(command.getName().equalsIgnoreCase("fistindebug"))
        {
            if(args.length >= 1)
            {
                switch (args[0].toLowerCase(Locale.ROOT))
                {
                    case "providers":
                        this.sendProviders(sender);
                        return true;
                    case "items":
                        this.sendItems(sender);
                        return true;
                    case "fireworks":
                        this.sendFireworks(sender);
                        return true;
                    case "events":
                        if(args.length > 1)
                        {
                            this.sendEvents(sender, args[1]);
                            return true;
                        }
                        else return false;
                    default:
                        return false;
                }
            }
        }
        return false;
    }

    private void sendProviders(CommandSender sender)
    {
        sender.sendMessage("-- PluginProviders --");
        PluginProviders.getProvidersName().forEach(name -> sender.sendMessage("* " + name));
    }

    private void sendItems(CommandSender sender)
    {
        sender.sendMessage("-- Items --");
        this.framework.items().getRegisteredItemsName().forEach(name -> {
            final String finalPath = this.framework.items().nameToLocation().get(name).getFinalPath();
            if(sender instanceof Player)
            {
                final TextComponent clickable = new TextComponent(name);
                clickable.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/fgive " + finalPath));

                final TextComponent component = new TextComponent("* ");
                component.addExtra(clickable);
                component.addExtra("\u00A7r (" + finalPath + ")");

                ((Player)sender).spigot().sendMessage(component);
            }
            else sender.sendMessage("* " + name + "\u00A7r (" + finalPath + ")");
        });
    }

    private void sendFireworks(CommandSender sender)
    {
        sender.sendMessage("-- Fireworks --");
        this.framework.fireworkFactory()
                .effectsLocation()
                .stream()
                .map(PluginLocation::getFinalPath)
                .forEach(name -> sender.sendMessage("* " + name));
    }

    private void sendEvents(CommandSender sender, String bus)
    {
        sender.sendMessage("-- Events --");
        if(bus.equalsIgnoreCase(this.framework.eventBus().implName()))
            DefaultEventBus.getEventExecutions().forEach(eventExecution -> sender.sendMessage("* " + eventExecution.getName() + " -> "  + new SimpleDateFormat("hh:mm:ss").format(new Date(eventExecution.getTimestamp()))));
    }
}
