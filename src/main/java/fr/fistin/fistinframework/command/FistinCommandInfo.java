package fr.fistin.fistinframework.command;

public @interface FistinCommandInfo
{
    String name();
    String permission() default "";
    boolean requiresPlayer() default false;
}
