package fr.fistin.fistinframework.utils;

import fr.fistin.fistinframework.IFistinFramework;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Level;

public class FistinGridParser
{
    public static final Map<File, FistinGridParser> PARSERS = new HashMap<>();

    private final File toParse;

    private FistinGridParser(File toParse)
    {
        this.toParse = toParse;
    }

    public static FistinGridParser of(File toParse)
    {
        if(!toParse.exists())
        {
            try
            {
                toParse.getParentFile().mkdirs();
                toParse.createNewFile();
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        if(PARSERS.containsKey(toParse))
            return PARSERS.get(toParse);
        else
        {
            final FistinGridParser parser = new FistinGridParser(toParse);
            PARSERS.put(toParse, parser);
            return parser;
        }
    }

    public List<Location> parse(String worldName)
    {
        final List<Location> locations = new ArrayList<>();
        try
        {
            final World world = Bukkit.getWorld(worldName);
            Files.readAllLines(this.toParse.toPath(), StandardCharsets.UTF_8)
                    .stream()
                    .filter(s -> !s.isEmpty())
                    .map(s -> s.split(","))
                    .filter(split -> split.length == 3)
                    .forEach(split -> {
                        final Function<Integer, Double> parser = index -> Double.parseDouble(split[index]);
                        locations.add(new Location(world, parser.apply(0), parser.apply(1), parser.apply(2)));
                    });
            return locations;
        }
        catch (Exception e)
        {
            IFistinFramework.framework().getLogger().log(Level.SEVERE, e.getMessage(), e);
        }

        return locations;
    }

    public void addPos(Location location)
    {
        try
        {
            final List<String> sb = Files.readAllLines(this.toParse.toPath(), StandardCharsets.UTF_8);
            sb.add(location.getX() + "," + location.getY() + "," + location.getZ());
            this.toParse.delete();
            this.toParse.createNewFile();
            Files.write(this.toParse.toPath(), sb, StandardCharsets.UTF_8);
        } catch (Exception e)
        {
            IFistinFramework.framework().getLogger().log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
