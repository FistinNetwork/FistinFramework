package fr.fistin.fistinframework.impl;

import fr.fistin.api.plugin.providers.IBukkitPluginProvider;
import fr.fistin.fistinframework.impl.listener.PreparedWorldProtectionListener;
import fr.fistin.fistinframework.listener.ListenerManager;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

public class ListenerManagerImpl implements ListenerManager
{
    private final List<Class<? extends Listener>> registered = new ArrayList<>();

    @Override
    public void enableWorldProtectionListener(IBukkitPluginProvider plugin)
    {
        if(!this.registered.contains(PreparedWorldProtectionListener.class))
        {
            plugin.getServer().getPluginManager().registerEvents(new PreparedWorldProtectionListener(), plugin);
            this.registered.add(PreparedWorldProtectionListener.class);
        }
    }
}
