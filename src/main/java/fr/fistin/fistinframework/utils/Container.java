package fr.fistin.fistinframework.utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Container<T, P>
{
    @Nullable T find(@NotNull P p);
    void add(@NotNull T t);
    void remove(@NotNull T t);
}
