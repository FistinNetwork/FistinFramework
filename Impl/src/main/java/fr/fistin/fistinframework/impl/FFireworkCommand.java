package fr.fistin.fistinframework.impl;

import fr.fistin.api.utils.PluginLocation;
import fr.fistin.fistinframework.IFistinFramework;
import fr.fistin.fistinframework.command.FistinCommand;
import fr.fistin.fistinframework.command.FistinCommandInfo;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
@FistinCommandInfo(name = "ffirework", permission = "fistin.staff", usage = "/ffirework [firework]", requiresPlayer = true)
class FFireworkCommand extends FistinCommand
{
    @Override
    protected ResultType execute(Player sender, String[] args)
    {
        if(args.length != 1) return ResultType.BAD_USAGE;

        final PluginLocation loc = PluginLocation.getLocation(args[0]);
        if(loc != null)
        {
            IFistinFramework.framework().fireworkFactory().spawnFirework(loc, sender.getLocation());
            return ResultType.SUCCESS;
        }
        else return ResultType.ERROR.apply("\u00A7cFirework name not valid!");
    }
}
