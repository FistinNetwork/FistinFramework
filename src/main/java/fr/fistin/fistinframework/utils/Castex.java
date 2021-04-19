package fr.fistin.fistinframework.utils;

import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface Castex<T>
{
   @NotNull <C extends T> C cast();
}
