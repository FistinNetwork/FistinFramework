package fr.fistin.fistinframework.command;

import fr.fistin.fistinframework.IFistinFramework;
import fr.fistin.fistinframework.configuration.Language;
import fr.fistin.fistinframework.utils.FistinFrameworkException;
import fr.fistin.fistinframework.utils.FistinValidate;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * This object is the parent class of all commands. All commands should extend this abstract class to be recognized correctly.
 */
public abstract class FistinCommand implements CommandExecutor
{
    private final @NotNull FistinCommandInfo fistinCommandInfo;
    private String currentError;

    /**
     * The main constructor that should be used by each sub-commands.
     */
    protected FistinCommand()
    {
        this.fistinCommandInfo = this.getClass().getDeclaredAnnotation(FistinCommandInfo.class);
        this.defineCurrentError();
        FistinValidate.notNull(this.fistinCommandInfo, "`fistinCommandInfo` cannot be null. (%s)", this.getClass().getName());
    }

    /**
     * Default implementation of the Bukkit {@link CommandExecutor} API.
     * @param sender the sender of the command.
     * @param command the command object.
     * @param label the used alias.
     * @param args arguments passed to this command.
     * @return always true.
     */
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

    /**
     * This method send the message corresponding to the given {@link ResultType}.
     * @param sender the sender of the command.
     * @param resultType the {@link ResultType} object.
     */
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

    /**
     * This method is executed when no player is required.
     * @param sender the command sender. It can be a {@link Player} or the console.
     * @param args arguments passed to this command.
     * @return a {@link ResultType}.
     */
    protected ResultType execute(CommandSender sender, String[] args)
    {
        return ResultType.SUCCESS;
    }

    /**
     * This method is executed a player is required.
     * @param sender the player who send this command.
     * @param args arguments passed to this command.
     * @return a {@link ResultType}.
     */
    protected ResultType execute(Player sender, String[] args)
    {
        return ResultType.SUCCESS;
    }

    /**
     * The {@link FistinCommandInfo} annotation.
     * @return the {@link FistinCommandInfo} annotation.
     */
    public @NotNull FistinCommandInfo getFistinCommandInfo()
    {
        return this.fistinCommandInfo;
    }

    /**
     * The current error details.
     * @return the current error details.
     */
    public @NotNull String currentError()
    {
        return this.currentError;
    }

    /**
     * Define the current error details.
     * @param currentError the current error details.
     */
    private void defineCurrentError(String currentError)
    {
        this.currentError = currentError;
    }

    /**
     * Define the default current error details.
     */
    private void defineCurrentError()
    {
        this.currentError = "No information.";
    }

    /**
     * This method checks if the sender is a player and reply to the sender an error message if it isn't.
     * @param sender the command sender to verify.
     * @return a {@link ResultType}.
     */
    protected @Nullable ResultType checkPlayerRequired(CommandSender sender)
    {
        if(!(sender instanceof Player)) return ResultType.ERROR.apply(IFistinFramework.framework().messages().getCommandPlayerRequired(Language.globalLanguage()));
        else return null;
    }

    /**
     * This enum represents all type of results that a command can throw on the process step.
     */
    public enum ResultType {
        BAD_USAGE,
        ERROR,
        SUCCESS;

        @Nullable
        private String msg;

        /**
         * Apply an error message. This method must be called only on the {@link #ERROR} result type. Otherwise, {@link FistinFrameworkException} will be thrown.
         * @param msg the error message.
         * @return this.
         */
        public ResultType apply(String msg)
        {
            if(this != ERROR) throw new FistinFrameworkException("apply is called on bad result type: " + this.name());
            this.msg = msg;
            return this;
        }
    }
}
