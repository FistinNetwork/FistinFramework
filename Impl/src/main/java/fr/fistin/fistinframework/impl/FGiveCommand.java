package fr.fistin.fistinframework.impl;

import fr.fistin.api.utils.PluginLocation;
import fr.fistin.fistinframework.IFistinFramework;
import fr.fistin.fistinframework.command.FistinCommand;
import fr.fistin.fistinframework.command.FistinCommandInfo;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
@FistinCommandInfo(name = "fgive", permission = "fistin.staff", requiresPlayer = true)
class FGiveCommand extends FistinCommand
{
    @Override
    protected void execute(Player sender, String[] args)
    {
        if(args.length != 1) return;

        final PluginLocation loc = PluginLocation.getLocation(args[0]);
        if(loc != null)
            sender.getInventory().addItem(IFistinFramework.framework().fistinItems().getItem(loc).enclosingItem());
        else sender.sendMessage("\u00A7cItem name not valid!\u00A7r");
    }
}
