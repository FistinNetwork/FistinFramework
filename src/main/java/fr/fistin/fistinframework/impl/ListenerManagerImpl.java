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
        if(this.enable(PreparedWorldProtectionListener.class))
            plugin.getServer().getPluginManager().registerEvents(new PreparedWorldProtectionListener(), plugin);
    }

    private boolean enable(Class<? extends Listener> listener)
    {
        if(this.registered.contains(listener))
            return false;
        else this.registered.add(listener);
        return true;
    }
}
