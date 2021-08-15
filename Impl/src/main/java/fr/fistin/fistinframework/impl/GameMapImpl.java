package fr.fistin.fistinframework.impl;

import fr.fistin.fistinframework.map.GameMap;
import fr.fistin.fistinframework.utils.FistinFrameworkException;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class GameMapImpl implements GameMap
{
    private final Path sourceWorldFolder;
    private Path currentWorldFolder;
    private World world;

    public GameMapImpl(Path worldFolder, String worldName, boolean loadOnInit)
    {
        try
        {
            if(Files.notExists(worldFolder))
                Files.createDirectories(worldFolder);

            this.sourceWorldFolder = worldFolder.resolve(worldName);

            if(loadOnInit)
                this.load();
        } catch (Exception e)
        {
            throw new FistinFrameworkException("Failed to initialize that GameMap!", e);
        }
    }

    @Override
    public boolean load()
    {
        if(this.isLoaded()) return true;

        try
        {
            this.currentWorldFolder = Bukkit.getWorldContainer().getParentFile().toPath().resolve(this.sourceWorldFolder.getFileName().toString() + "_loaded_on_" + System.currentTimeMillis());

            Files.copy(this.sourceWorldFolder, this.currentWorldFolder);

            this.world = Bukkit.createWorld(new WorldCreator(this.currentWorldFolder.getFileName().toString()));

            if(this.world != null)
                this.world.setAutoSave(false);

            return this.isLoaded();
        } catch (Exception e)
        {
            throw new FistinFrameworkException("Failed to load that GameMap!", e);
        }
    }

    @Override
    public boolean unload()
    {
        try {
            if(this.world != null)
                Bukkit.unloadWorld(this.world, false);
            if(this.currentWorldFolder != null)
                this.delete(this.currentWorldFolder);

            this.world = null;
            this.currentWorldFolder = null;
        }
        catch (Exception e)
        {
            throw new FistinFrameworkException("Failed to unload that GameMap!", e);
        }

        return false;
    }

    private void delete(Path path) throws Exception
    {
        try(Stream<Path> children = Files.list(path))
        {
            children.forEach(path1 -> {
                try
                {
                    if(Files.isDirectory(path1))
                        this.delete(path1);
                    else Files.deleteIfExists(path1);
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            });
        }
    }

    @Override
    public boolean restoreFromSource()
    {
        this.unload();
        return this.load();
    }

    @Override
    public boolean isLoaded()
    {
        return this.world != null;
    }

    @Override
    public World getWorld()
    {
        return this.world;
    }
}
