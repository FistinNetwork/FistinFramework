package fr.fistin.fistinframework.command;

import fr.fistin.fistinframework.IFistinFramework;
import fr.fistin.fistinframework.configuration.Language;
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
    private String currentError;

    protected FistinCommand()
    {
        this.fistinCommandInfo = this.getClass().getDeclaredAnnotation(FistinCommandInfo.class);
        this.defineCurrentError();
        FistinValidate.notNull(this.fistinCommandInfo, "`fistinCommandInfo` cannot be null. (%s)", this.getClass().getName());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if(!this.fistinCommandInfo.permission().isEmpty())
        {
            if(!sender.hasPermission(this.fistinCommandInfo.permission()))
            {
                sender.sendMessage(IFistinFramework.framework().messages().getCommandMissingPermission(Language.globalLanguage()));
                return true;
            }
        }

        if(this.fistinCommandInfo.requiresPlayer())
        {
            if(this.checkPlayerRequired(sender) != null) return true;

            this.processResult(sender, this.execute((Player)sender, args));
            return true;
        }

        this.processResult(sender, this.execute(sender, args));

        return true;
    }

    protected void processResult(CommandSender sender, ResultType resultType)
    {
        switch (resultType)
        {
            case SUCCESS:
                break;
            case BAD_USAGE:
                sender.sendMessage(IFistinFramework.framework().messages().getCommandBadUsage(Language.globalLanguage(), this));
                break;
            case ERROR:
                this.defineCurrentError(resultType.msg);
                sender.sendMessage(IFistinFramework.framework().messages().getCommandError(Language.globalLanguage(), this));
                this.defineCurrentError();
                break;
        }
    }

    protected ResultType execute(CommandSender sender, String[] args)
    {
        return ResultType.SUCCESS;
    }

    protected ResultType execute(Player sender, String[] args)
    {
        return ResultType.SUCCESS;
    }

    public @NotNull FistinCommandInfo getFistinCommandInfo()
    {
        return this.fistinCommandInfo;
    }

    public @NotNull String currentError()
    {
        return this.currentError;
    }

    private void defineCurrentError(String currentError)
    {
        this.currentError = currentError;
    }

    private void defineCurrentError()
    {
        this.currentError = "No information.";
    }

    protected @Nullable ResultType checkPlayerRequired(CommandSender sender)
    {
        if(!(sender instanceof Player)) return ResultType.ERROR.apply(IFistinFramework.framework().messages().getCommandPlayerRequired(Language.globalLanguage()));
        else return null;
    }

    public enum ResultType {
        BAD_USAGE,
        ERROR,
        SUCCESS;

        @Nullable
        private String msg;

        public ResultType apply(String msg)
        {
            this.msg = msg;
            return this;
        }
    }
}
