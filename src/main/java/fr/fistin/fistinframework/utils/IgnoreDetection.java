package fr.fistin.fistinframework.utils;

import java.lang.annotation.*;

/**
 * Indicate to {@link AutomaticRegisterer} that the class MUST NOT be processed and registered.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface IgnoreDetection {}
