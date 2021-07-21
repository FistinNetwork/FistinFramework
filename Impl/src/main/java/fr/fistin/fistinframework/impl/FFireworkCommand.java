package fr.fistin.fistinframework.impl;

import fr.fistin.api.utils.PluginLocation;
import fr.fistin.fistinframework.IFistinFramework;
import fr.fistin.fistinframework.command.FistinCommand;
import fr.fistin.fistinframework.command.FistinCommandInfo;
import fr.fistin.fistinframework.configuration.Language;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
@FistinCommandInfo(name = "ffirework", permission = "fistin.staff", usage = "/ffirework firework [x, y, z]")
class FFireworkCommand extends FistinCommand
{
    @Override
    protected ResultType execute(CommandSender sender, String[] args)
    {
        if(args.length != 1 && args.length != 4) return ResultType.BAD_USAGE;

        if(args.length == 1)
        {
            final ResultType err = this.checkPlayerRequired(sender);
            if(err != null) return err;

            return this.spawnFirework(args[0], ((Player)sender).getLocation());
        }
        else return this.spawnFirework(args[0], new Location(Bukkit.getWorlds().get(0), Double.parseDouble(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3])));
    }

    private ResultType spawnFirework(String firework, Location location)
    {
        final PluginLocation loc = PluginLocation.getLocation(firework);
        if(loc != null)
        {
            IFistinFramework.framework().fireworkFactory().spawnFirework(loc, location);
            return ResultType.SUCCESS;
        }
        else return ResultType.ERROR.apply(IFistinFramework.framework().messages().getInvalidObjectName(Language.globalLanguage()));
    }
}
