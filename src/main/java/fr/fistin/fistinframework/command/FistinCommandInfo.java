package fr.fistin.fistinframework.command;

import java.lang.annotation.*;

/**
 * All annotated classes with this annotation and that extends {@link FistinCommand} are considered as commands.
 * This annotation is mandatory for the registration.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface FistinCommandInfo
{
    /**
     * The name of the command.
     * @return the name of the addon.
     */
    String name();

    /**
     * The permission needed to execute this command.
     * @return the permission needed to execute this command.
     */
    String permission() default "";

    /**
     * man [command name]
     * @return the good usage.
     */
    String usage();

    /**
     * If this command requires a player to be executed.
     * @return if this command requires a player to be executed.
     */
    boolean requiresPlayer() default false;
}
