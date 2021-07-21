package fr.fistin.fistinframework.impl;

import fr.fistin.api.utils.PluginLocation;
import fr.fistin.fistinframework.IFistinFramework;
import fr.fistin.fistinframework.command.FistinCommand;
import fr.fistin.fistinframework.command.FistinCommandInfo;
import fr.fistin.fistinframework.configuration.Language;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
@FistinCommandInfo(name = "fgive", permission = "fistin.staff", usage = "/fgive [player] item")
class FGiveCommand extends FistinCommand
{
    @Override
    protected ResultType execute(CommandSender sender, String[] args)
    {
        if(args.length < 1 || args.length > 2) return ResultType.BAD_USAGE;

        if(args.length == 1)
        {
            final ResultType err = this.checkPlayerRequired(sender);

            if(err != null) return err;

            return this.giveItem(args[0], (Player)sender);
        }
        else
        {
            final Player player = Bukkit.getPlayer(args[0]);

            if(player == null) return ResultType.ERROR.apply(IFistinFramework.framework().messages().getPlayerSpecifiedNotFound(Language.globalLanguage()));

            return this.giveItem(args[1], player);
        }
    }

    private ResultType giveItem(String location, Player player)
    {
        final PluginLocation loc = PluginLocation.getLocation(location);
        final IFistinFramework framework = IFistinFramework.framework();

        if(loc != null)
        {
            player.getInventory().addItem(framework.fistinItems().getItem(loc).enclosingItem());
            return ResultType.SUCCESS;
        }
        else return ResultType.ERROR.apply(framework.messages().getInvalidObjectName(Language.globalLanguage()));
    }
}
