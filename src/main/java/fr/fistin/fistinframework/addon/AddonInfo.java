package fr.fistin.fistinframework.addon;

import java.lang.annotation.*;

/**
 * All annotated classes with this annotation and that extends {@link FistinAddon} are considered as addons.
 * This annotation is mandatory for the registration.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface AddonInfo
{
    /**
     * The name of the addon.
     * @return the name of the addon.
     */
    String name();

    /**
     * The version of the addon.
     * @return the version of the addon.
     */
    String version();

    /**
     * Define if parent plugin should log some things about the addon.
     * @return true if it should, false if it shouldn't.
     */
    boolean logging() default true;
}
