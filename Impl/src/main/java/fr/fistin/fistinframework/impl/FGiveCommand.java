package fr.fistin.fistinframework.impl;

import fr.fistin.api.utils.PluginLocation;
import fr.fistin.fistinframework.IFistinFramework;
import fr.fistin.fistinframework.command.FistinCommand;
import fr.fistin.fistinframework.command.FistinCommandInfo;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
@FistinCommandInfo(name = "fgive", permission = "fistin.staff", usage = "/fgive [item]", requiresPlayer = true)
class FGiveCommand extends FistinCommand
{
    @Override
    protected ResultType execute(Player sender, String[] args)
    {
        if(args.length != 1) return ResultType.BAD_USAGE;

        final PluginLocation loc = PluginLocation.getLocation(args[0]);
        if(loc != null)
        {
            sender.getInventory().addItem(IFistinFramework.framework().fistinItems().getItem(loc).enclosingItem());
            return ResultType.SUCCESS;
        }
        else return ResultType.ERROR.apply("\u00A7cItem name not valid!");
    }
}
