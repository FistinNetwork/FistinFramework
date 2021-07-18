package fr.fistin.fistinframework.command;

import fr.fistin.fistinframework.IFistinFramework;
import fr.fistin.fistinframework.configuration.Language;
import fr.fistin.fistinframework.game.IGamePluginProvider;
import fr.fistin.fistinframework.utils.FistinValidate;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class FistinCommand implements CommandExecutor
{
    private final @NotNull FistinCommandInfo fistinCommandInfo;
    protected @Nullable IGamePluginProvider gamePluginProvider;

    protected FistinCommand()
    {
        this.fistinCommandInfo = this.getClass().getDeclaredAnnotation(FistinCommandInfo.class);
        FistinValidate.notNull(this.fistinCommandInfo, "`fistinCommandInfo` cannot be null.");
    }

    protected FistinCommand(@NotNull IGamePluginProvider gamePluginProvider)
    {
        this();
        this.gamePluginProvider = gamePluginProvider;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if(!this.fistinCommandInfo.permission().isEmpty())
        {
            if(!sender.hasPermission(this.fistinCommandInfo.permission()))
            {
                sender.sendMessage(IFistinFramework.framework().messages().getMissingPermissionMessage(Language.DEFAULT));
                return true;
            }
        }

        if(this.fistinCommandInfo.requiresPlayer())
        {
            if(!(sender instanceof Player))
            {
                sender.sendMessage(IFistinFramework.framework().messages().getPlayerRequiredMessage(Language.DEFAULT));
                return true;
            }

            this.execute((Player)sender, args);
            return true;
        }

        this.execute(sender, args);

        return true;
    }

    protected void execute(CommandSender sender, String[] args) {}
    protected void execute(Player sender, String[] args) {}

    public @NotNull FistinCommandInfo getFistinCommandInfo()
    {
        return fistinCommandInfo;
    }
}
