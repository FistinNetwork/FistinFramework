package fr.fistin.fistinframework.impl;

import fr.fistin.fistinframework.IFistinFramework;
import fr.fistin.fistinframework.command.FistinCommand;
import fr.fistin.fistinframework.eventbus.FistinEvent;
import fr.fistin.fistinframework.eventbus.FistinEventBus;
import fr.fistin.fistinframework.game.IGamePluginProvider;
import fr.fistin.fistinframework.utils.AutomaticRegisterer;
import fr.fistin.fistinframework.utils.IBukkitPluginProvider;
import fr.fistin.fistinframework.utils.IgnoreDetection;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.ApiStatus;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.stream.Collectors;

@ApiStatus.Internal
class AutomaticRegistererImpl implements AutomaticRegisterer
{
    @Override
    public void register(IBukkitPluginProvider plugin, String packageName, Type type)
    {
        plugin.getLogger().info(String.format("Registering %ss in package '%s' (%s).", type.name().toLowerCase(), packageName, plugin.getName()));
        final Reflections reflections = new Reflections(packageName);

        switch (type)
        {
            case EVENT:
                this.registerEvents(reflections.getSubTypesOf(FistinEvent.class).stream().filter(aClass -> aClass.getDeclaredAnnotation(IgnoreDetection.class) == null).collect(Collectors.toSet()), IFistinFramework.framework().fistinEventBus());
                break;
            case COMMAND:
                this.registerCommands(reflections.getSubTypesOf(FistinCommand.class).stream().filter(aClass -> aClass.getDeclaredAnnotation(IgnoreDetection.class) == null).collect(Collectors.toSet()), plugin);
                break;
            case LISTENER:
                this.registerListeners(reflections.getSubTypesOf(Listener.class).stream().filter(aClass -> aClass.getDeclaredAnnotation(IgnoreDetection.class) == null).collect(Collectors.toSet()), plugin);
                break;
        }
    }

    private void registerEvents(Set<Class<? extends FistinEvent>> classes, FistinEventBus<Supplier<? extends FistinEvent>> eventBus)
    {
        classes.forEach(eventBus::registerEvent);
    }

    private void registerCommands(Set<Class<? extends FistinCommand>> classes, IBukkitPluginProvider plugin)
    {
        for (Class<? extends FistinCommand> commandClass : classes)
        {
            try
            {
                final FistinCommand command;
                if(plugin instanceof IGamePluginProvider)
                {
                    final IGamePluginProvider temp = (IGamePluginProvider)plugin;
                    command = commandClass.getDeclaredConstructor(IGamePluginProvider.class).newInstance(temp);
                }
                else command = commandClass.getDeclaredConstructor().newInstance();
                ((JavaPlugin)plugin).getCommand(command.getFistinCommandInfo().name()).setExecutor(command);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e)
            {
                plugin.getLogger().log(Level.SEVERE, e.getMessage(), e);
            }
        }
    }

    private void registerListeners(Set<Class<? extends Listener>> classes, IBukkitPluginProvider plugin)
    {
        for (Class<? extends Listener> listenerClass : classes)
        {
            try
            {
                final Listener listener = listenerClass.getDeclaredConstructor().newInstance();
                plugin.getServer().getPluginManager().registerEvents(listener, plugin);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e)
            {
                plugin.getLogger().log(Level.SEVERE, e.getMessage(), e);
            }
        }
    }
}
