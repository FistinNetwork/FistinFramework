package fr.fistin.fistinframework.impl;

import fr.fistin.fistinframework.IFistinFramework;
import fr.fistin.fistinframework.addon.AddonInfo;
import fr.fistin.fistinframework.addon.FistinAddon;
import fr.fistin.fistinframework.command.FistinCommand;
import fr.fistin.fistinframework.eventbus.FistinEvent;
import fr.fistin.fistinframework.eventbus.FistinEventBus;
import fr.fistin.fistinframework.item.FistinItem;
import fr.fistin.fistinframework.utils.*;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.ApiStatus;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@ApiStatus.Internal
class AutomaticRegistererImpl implements AutomaticRegisterer
{
    @Override
    public void register(IBukkitPluginProvider plugin, String packageName, Type type)
    {
        final IFistinFramework framework = IFistinFramework.framework();

        framework.getLogger().info(String.format("Searching for %ss in package '%s' (%s).", type.name().toLowerCase(), packageName, plugin.getClass().getSimpleName()));

        final Reflections reflections = new Reflections(new ConfigurationBuilder().filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix(packageName))).setUrls(ClasspathHelper.forPackage(packageName, plugin.getClass().getClassLoader())));

        switch (type)
        {
            case EVENT:
                this.registerEvents(reflections.getSubTypesOf(FistinEvent.class).stream().filter(aClass -> aClass.getDeclaredAnnotation(IgnoreDetection.class) == null).collect(Collectors.toSet()), framework.fistinEventBus(), plugin);
                break;
            case COMMAND:
                this.registerCommands(reflections.getSubTypesOf(FistinCommand.class).stream().filter(aClass -> aClass.getDeclaredAnnotation(IgnoreDetection.class) == null).collect(Collectors.toSet()), plugin);
                break;
            case LISTENER:
                this.registerListeners(reflections.getSubTypesOf(Listener.class).stream().filter(aClass -> aClass.getDeclaredAnnotation(IgnoreDetection.class) == null).collect(Collectors.toSet()), plugin);
                break;
            case ADDON:
                if (plugin instanceof IAddonProvider) this.registerAddons(reflections.getSubTypesOf(FistinAddon.class).stream().filter(aClass -> aClass.getDeclaredAnnotation(IgnoreDetection.class) == null).filter(aClass -> aClass.getDeclaredAnnotation(AddonInfo.class) != null).collect(Collectors.toSet()), (IAddonProvider)plugin);
                else framework.getLogger().severe(String.format("Provided `plugin` (%s) isn't an instance of `IAddonProvider`!", plugin.getClass().getSimpleName()));
                break;
            case ITEM:
                this.registerItems(reflections.getSubTypesOf(FistinItem.class).stream().filter(aClass -> aClass.getDeclaredAnnotation(IgnoreDetection.class) == null).filter(aClass -> !Modifier.isAbstract(aClass.getModifiers())).collect(Collectors.toSet()), plugin);
                break;
        }
    }

    private void registerEvents(Set<Class<? extends FistinEvent>> classes, FistinEventBus<Supplier<? extends FistinEvent>> eventBus, IBukkitPluginProvider plugin)
    {
        classes.forEach((event) -> {
            IFistinFramework.framework().getLogger().info(String.format("Registering '%s' event (%s).", event.getName(), plugin.getClass().getSimpleName()));
            eventBus.registerEvent(event);
        });
    }

    private void registerCommands(Set<Class<? extends FistinCommand>> classes, IBukkitPluginProvider plugin)
    {
        for (Class<? extends FistinCommand> commandClass : classes)
        {
            try
            {
                final Constructor<? extends FistinCommand> constructor = commandClass.getDeclaredConstructor();
                constructor.setAccessible(true);
                final FistinCommand command = constructor.newInstance();

                IFistinFramework.framework().getLogger().info(String.format("Registering '%s' command (%s).", command.getFistinCommandInfo().name(), plugin.getClass().getSimpleName()));
                ((JavaPlugin)plugin).getCommand(command.getFistinCommandInfo().name()).setExecutor(command);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e)
            {
                throw new FistinFrameworkException(e);
            }
        }
    }

    private void registerListeners(Set<Class<? extends Listener>> classes, IBukkitPluginProvider plugin)
    {
        for (Class<? extends Listener> listenerClass : classes)
        {
            try
            {
                final Constructor<? extends Listener> constructor = listenerClass.getDeclaredConstructor();
                constructor.setAccessible(true);
                final Listener listener = constructor.newInstance();

                IFistinFramework.framework().getLogger().info(String.format("Registering '%s' listener (%s).", listener.getClass().getName(), plugin.getClass().getSimpleName()));
                plugin.getServer().getPluginManager().registerEvents(listener, plugin);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e)
            {
                throw new FistinFrameworkException(e);
            }
        }
    }

    @SuppressWarnings("rawtypes")
    private void registerAddons(Set<Class<? extends FistinAddon>> addons, IAddonProvider plugin)
    {
        plugin.addons(addons.stream().map(addonClass -> {
            try
            {
                final AddonInfo addonInfo = addonClass.getDeclaredAnnotation(AddonInfo.class);
                final Constructor<? extends FistinAddon> constructor = addonClass.getDeclaredConstructor();
                constructor.setAccessible(true);
                final FistinAddon<?> addon = constructor.newInstance();

                addon.name(addonInfo.name());
                addon.version(addonInfo.version());
                addon.logging(addonInfo.logging());
                addon.parent(plugin);

                IFistinFramework.framework().getLogger().info(String.format("Registering '%s' addon (%s).", addon.name(), plugin.getClass().getSimpleName()));

                return addon;
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e)
            {
                throw new FistinFrameworkException(e);
            }
        }).collect(Collectors.toSet()));
    }

    private void registerItems(Set<Class<? extends FistinItem>> classes, IBukkitPluginProvider plugin)
    {
        for (Class<? extends FistinItem> itemClass : classes)
        {
            try
            {
                final Constructor<? extends FistinItem> constructor = itemClass.getDeclaredConstructor();
                constructor.setAccessible(true);
                final FistinItem item = constructor.newInstance();

                final IFistinFramework framework = IFistinFramework.framework();
                framework.getLogger().info(String.format("Registering '%s' item (%s).", item.getClass().getName(), plugin.getClass().getSimpleName()));
                framework.fistinItems().registerItem(() -> item);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e)
            {
                throw new FistinFrameworkException(e);
            }
        }
    }
}
