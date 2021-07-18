package fr.fistin.fistinframework.configuration;

import fr.fistin.fistinframework.utils.Cleanable;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

public interface ConfigurationMapping<T, R> extends Cleanable
{
    @NotNull
    Map<String, Function<T, R>> mappings();

    default String map(String toMap, T param)
    {
        final AtomicReference<String> str = new AtomicReference<>(toMap);
        this.mappings().forEach((s, trFunction) -> str.set(str.get().replace(s, "" + trFunction.apply(param))));
        return str.get();
    }

    @Override
    default void clean()
    {
        this.mappings().clear();
    }
}
