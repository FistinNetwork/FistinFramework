package fr.fistin.fistinframework.impl;

import fr.fistin.api.plugin.providers.PluginProviders;
import fr.fistin.api.utils.PluginLocation;
import fr.fistin.fistinframework.IFistinFramework;
import fr.fistin.fistinframework.command.FistinCommand;
import fr.fistin.fistinframework.command.FistinCommandInfo;
import fr.fistin.fistinframework.eventbus.DefaultEventBus;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@ApiStatus.Internal
@FistinCommandInfo(name = "fdebug", permission = "fistin.staff")
class FDebugCommand extends FistinCommand
{
    private final IFistinFramework framework = IFistinFramework.framework();

    @Override
    protected void execute(CommandSender sender, String[] args)
    {
        if(args.length < 1) return;

        switch (args[0].toLowerCase(Locale.ROOT))
        {
            case "providers":
                this.sendProviders(sender);
                break;
            case "items":
                this.sendItems(sender);
                break;
            case "fireworks":
                this.sendFireworks(sender);
                break;
            case "events":
                if (args.length > 1)
                    this.sendEvents(sender, args[1]);
                break;
        }
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
                .fistinItems()
                .getRegisteredItemsName()
                .forEach(name -> {
                    final String finalPath = this.framework.fistinItems().nameToLocation().get(name).getFinalPath();
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
        if(bus.equalsIgnoreCase(this.framework.fistinEventBus().implName()))
            DefaultEventBus.getEventExecutions().forEach((defaultEventBus, eventExecution) -> sender.sendMessage("* " + eventExecution.getName() + " -> "  + new SimpleDateFormat("hh:mm:ss").format(new Date(eventExecution.getTimestamp()))));
    }
}
