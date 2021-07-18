package fr.fistin.fistinframework.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface FistinCommandInfo
{
    String name();
    String permission() default "";
    boolean requiresPlayer() default false;
}
