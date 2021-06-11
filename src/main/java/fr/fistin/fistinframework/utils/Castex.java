package fr.fistin.fistinframework.utils;

import org.jetbrains.annotations.NotNull;

public interface Castex<T>
{
   @SuppressWarnings("unchecked")
   default <C extends T> @NotNull C cast()
   {
      return (C)this;
   }
}
