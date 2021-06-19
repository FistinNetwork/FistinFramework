package fr.fistin.fistinframework.impl;

import fr.fistin.api.plugin.providers.IBukkitPluginProvider;
import fr.fistin.fistinframework.addon.AddonInfo;
import fr.fistin.fistinframework.addon.AddonProcessor;
import fr.fistin.fistinframework.addon.FistinAddon;
import org.jetbrains.annotations.ApiStatus;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@ApiStatus.Internal
class AddonProcessorImpl implements AddonProcessor
{
    @Override
    public final List<AddonInfo> detectAddons(String searchingPackage)
    {
        final Reflections reflections = new Reflections(
                new ConfigurationBuilder()
                        .setUrls(ClasspathHelper.forPackage(searchingPackage))
                        .setScanners(new SubTypesScanner(), new TypeAnnotationsScanner()));
        return reflections.getTypesAnnotatedWith(AddonInfo.class).stream().map(aClass -> aClass.getAnnotationsByType(AddonInfo.class)[0]).collect(Collectors.toList());
    }

    @Override
    public final List<FistinAddon<?>> getAddons(Collection<AddonInfo> infos, IBukkitPluginProvider plugin)
    {
        return infos.stream().map(addonInfo -> {
            try
            {
                final FistinAddon<?> addon = addonInfo.addon().newInstance();
                addon.name(addonInfo.name());
                addon.version(addonInfo.version());
                addon.logging(addonInfo.logging());
                addon.parent(plugin);
                return addon;
            } catch (InstantiationException | IllegalAccessException e)
            {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }
}
