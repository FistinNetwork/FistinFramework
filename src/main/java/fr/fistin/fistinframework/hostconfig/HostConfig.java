package fr.fistin.fistinframework.hostconfig;

import fr.fistin.fistinframework.utils.Cleanable;
import fr.fistin.fistinframework.utils.IBukkitPluginProvider;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HostConfig implements Cleanable
{
    private final @NotNull IBukkitPluginProvider plugin;
    private final @NotNull List<Category> categories;

    private Category defaultCategory;

    public HostConfig(@NotNull IBukkitPluginProvider plugin, @NotNull List<Category> categories, Category defaultCategory)
    {
        this(plugin, categories);
        this.defaultCategory = defaultCategory;
    }

    public HostConfig(@NotNull IBukkitPluginProvider plugin, @NotNull List<Category> categories)
    {
        this.plugin = plugin;
        this.categories = categories;

        this.categories.forEach(category -> category.setPlugin(this.plugin));
    }

    public HostConfig(@NotNull IBukkitPluginProvider plugin)
    {
        this(plugin, new ArrayList<>());
    }

    public @NotNull IBukkitPluginProvider getPlugin()
    {
        return this.plugin;
    }

    public @NotNull List<Category> getCategories()
    {
        return Collections.unmodifiableList(this.categories);
    }

    public void addCategory(Category category, boolean defaultCategory)
    {
        category.setPlugin(this.plugin);
        this.categories.add(category);
        if(defaultCategory) this.setDefaultCategory(category);
    }

    public void setDefaultCategory(Category defaultCategory)
    {
        this.defaultCategory = defaultCategory;
    }

    public Category getDefaultCategory()
    {
        return this.defaultCategory;
    }

    @Override
    public void clean()
    {
        this.categories.forEach(Category::clean);
        this.categories.clear();
    }
}
