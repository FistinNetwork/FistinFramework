package fr.fistin.fistinframework.utils;

import org.jetbrains.annotations.NotNull;

/**
 * @see #cast()
 * @param <T> the object parent's that will be cast.
 */
public interface Castex<T>
{
   /**
    * Hacky trick to access to an implementation of the parent class without any reflection.
    * @param <C> the implementation to get.
    * @return the implementation.
    */
   @SuppressWarnings("unchecked")
   default <C extends T> @NotNull C cast()
   {
      return (C)this;
   }
}
