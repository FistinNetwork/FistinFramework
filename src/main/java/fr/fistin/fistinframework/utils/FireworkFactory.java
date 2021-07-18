package fr.fistin.fistinframework.utils;

import fr.fistin.api.utils.PluginLocation;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;

import java.util.Set;
import java.util.function.Function;

public interface FireworkFactory extends Cleanable
{
	void registerFirework(PluginLocation location, Function<FireworkEffect.Builder, FireworkEffect> effect);
	void spawnFirework(PluginLocation pluginLocation, Location location, double offsetY);
	void spawnFirework(PluginLocation pluginLocation, Location location);
	Set<PluginLocation> effectsLocation();
}
