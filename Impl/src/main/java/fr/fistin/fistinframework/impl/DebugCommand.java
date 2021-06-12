package fr.fistin.fistinframework.impl;

import fr.fistin.api.plugin.providers.PluginProviders;
import fr.fistin.api.utils.PluginLocation;
import fr.fistin.fistinframework.IFistinFramework;
import fr.fistin.fistinframework.eventbus.DefaultEventBus;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.*;

class DebugCommand implements CommandExecutor, TabCompleter
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
        this.framework
                .items()
                .getRegisteredItemsName()
                .forEach(name -> {
                    final String finalPath = this.framework.items().nameToLocation().get(name).getFinalPath();
                    if (sender instanceof Player)
                    {
                        final TextComponent clickable = new TextComponent(name);
                        clickable.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[]{new TextComponent("\u00A7aClick to get this item.\u00A7r")}));
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
        this.framework
                .fireworkFactory()
                .effectsLocation()
                .stream()
                .map(PluginLocation::getFinalPath)
                .forEach(name -> {
                    if(sender instanceof Player)
                    {
                        final TextComponent clickable = new TextComponent(name);
                        final TextComponent hover = new TextComponent("\u00A7aClick to summon this firework.\u00A7r");
                        clickable.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new BaseComponent[]{hover}));
                        clickable.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/ffirework " + name));

                        final TextComponent component = new TextComponent("* ");
                        component.addExtra(clickable);
                        component.addExtra("\u00A7r (" + name + ")");

                        ((Player)sender).spigot().sendMessage(component);
                    }
                    else sender.sendMessage("* " + name);
                });
    }

    private void sendEvents(CommandSender sender, String bus)
    {
        sender.sendMessage(String.format("-- Events on '%s' eventbus --", bus));
        if(bus.equalsIgnoreCase(this.framework.eventBus().implName()))
            DefaultEventBus.getEventExecutions().forEach(eventExecution -> sender.sendMessage("* " + eventExecution.getName() + " -> "  + new SimpleDateFormat("hh:mm:ss").format(new Date(eventExecution.getTimestamp()))));
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args)
    {
        List<String> result = new ArrayList<>();
        if (args.length == 0 || args[0].equalsIgnoreCase(" "))
            result = Arrays.asList("providers", "events", "items", "fireworks");
        else if(args.length == 1)
        {
            if(args[0].equalsIgnoreCase("events"))
                result = Collections.singletonList("default");
        }
        return result;
    }

}
