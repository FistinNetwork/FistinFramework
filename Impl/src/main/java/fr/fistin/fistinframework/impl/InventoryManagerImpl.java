package fr.fistin.fistinframework.impl;

import fr.fistin.fistinframework.runnable.RunnableUtils;
import fr.fistin.fistinframework.smartinvs.InventoryListener;
import fr.fistin.fistinframework.smartinvs.InventoryManager;
import fr.fistin.fistinframework.smartinvs.SmartInventory;
import fr.fistin.fistinframework.smartinvs.content.InventoryContents;
import fr.fistin.fistinframework.smartinvs.content.InventoryContentsWrapper;
import fr.fistin.fistinframework.smartinvs.opener.ChestInventoryOpener;
import fr.fistin.fistinframework.smartinvs.opener.InventoryOpener;
import fr.fistin.fistinframework.smartinvs.opener.SpecialInventoryOpener;
import fr.fistin.fistinframework.utils.IBukkitPluginProvider;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.ApiStatus;

import java.util.*;
import java.util.concurrent.TimeUnit;

@ApiStatus.Internal
class InventoryManagerImpl implements InventoryManager
{
    private final IBukkitPluginProvider plugin;
    private final InventoryContentsWrapper contentsWrapper;
    private final Map<UUID, SmartInventory> inventories;
    private final Map<UUID, InventoryContents> contents;
    private final List<InventoryOpener> defaultOpeners;
    private final List<InventoryOpener> openers;

    public InventoryManagerImpl(IBukkitPluginProvider plugin, InventoryContentsWrapper contentsWrapper)
    {
        this.plugin = plugin;
        this.contentsWrapper = contentsWrapper;

        this.inventories = new HashMap<>();
        this.contents = new HashMap<>();

        this.defaultOpeners = Arrays.asList(new ChestInventoryOpener(), new SpecialInventoryOpener());

        this.openers = new ArrayList<>();
    }

    @Override
    public void init()
    {
        new BukkitRunnable() {
            @Override
            public void run()
            {
                new HashMap<>(InventoryManagerImpl.this.inventories).forEach((player, inv) -> inv.getProvider().update(Bukkit.getPlayer(player), InventoryManagerImpl.this.contents.get(player)));
            }
        }.runTaskTimer(this.plugin, 1, 1);
        RunnableUtils.runRepeatedBukkitRunnable(() -> new HashMap<>(InventoryManagerImpl.this.inventories).forEach((player, inv) -> inv.getProvider().update(Bukkit.getPlayer(player), InventoryManagerImpl.this.contents.get(player))), false, this.plugin, 1/20, TimeUnit.SECONDS, 1/20, TimeUnit.SECONDS);
    }

    @Override
    public Optional<InventoryOpener> findOpener(InventoryType type)
    {
        Optional<InventoryOpener> opInv = this.openers.stream().filter(opener -> opener.supports(type)).findAny();

        if (!opInv.isPresent())
            opInv = this.defaultOpeners.stream().filter(opener -> opener.supports(type)).findAny();

        return opInv;
    }

    @Override
    public void registerOpeners(InventoryOpener... openers)
    {
        this.openers.addAll(Arrays.asList(openers));
    }

    @Override
    public List<Player> getOpenedPlayers(SmartInventory inv)
    {
        final List<Player> list = new ArrayList<>();

        this.inventories.forEach((player, playerInv) -> {
            if (inv.equals(playerInv))
                list.add(Bukkit.getPlayer(player));
        });

        return list;
    }

    @Override
    public Optional<SmartInventory> getInventory(Player p)
    {
        return Optional.ofNullable(this.inventories.get(p.getUniqueId()));
    }

    @Override
    public void setInventory(Player p, SmartInventory inv)
    {
        if (inv == null) this.inventories.remove(p.getUniqueId());
        else this.inventories.put(p.getUniqueId(), inv);
    }

    @Override
    public Optional<InventoryContents> getContents(Player p)
    {
        return Optional.ofNullable(this.contents.get(p.getUniqueId()));
    }

    @Override
    public void setContents(Player p, InventoryContents contents)
    {
        if (contents == null) this.contents.remove(p.getUniqueId());
        else this.contents.put(p.getUniqueId(), contents);
    }

    @Override
    public InventoryContentsWrapper getContentsWrapper()
    {
        return this.contentsWrapper;
    }

    @Override
    public SmartInventory.Builder smartInventoryBuilder()
    {
        return new SmartInventoryImpl.Builder().manager(this);
    }

    @SuppressWarnings({"unchecked", "unused"})
    private class InvListener implements Listener
    {
        @EventHandler(priority = EventPriority.LOW)
        public void onInventoryClick(InventoryClickEvent e)
        {
            final Player p = (Player)e.getWhoClicked();

            if (!InventoryManagerImpl.this.inventories.containsKey(p.getUniqueId())) return;

            if (e.getAction() == InventoryAction.COLLECT_TO_CURSOR || e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY)
            {
                e.setCancelled(true);
                return;
            }

            if (e.getAction() == InventoryAction.NOTHING && e.getClick() != ClickType.MIDDLE)
            {
                e.setCancelled(true);
                return;
            }

            if (e.getClickedInventory() == p.getOpenInventory().getTopInventory())
            {
                e.setCancelled(true);

                final int row = e.getSlot() / 9;
                final int column = e.getSlot() % 9;

                if (row < 0 || column < 0) return;

                final SmartInventory inv = InventoryManagerImpl.this.inventories.get(p.getUniqueId());

                if (row >= inv.getRows() || column >= inv.getColumns()) return;

                inv.getListeners().stream().filter(listener -> listener.getType() == InventoryClickEvent.class).forEach(listener -> ((InventoryListener<InventoryClickEvent>)listener).accept(e));
                InventoryManagerImpl.this.contents.get(p.getUniqueId()).get(row, column).ifPresent(item -> item.run(e));
                p.updateInventory();
            }
        }

        @EventHandler(priority = EventPriority.LOW)
        public void onInventoryDrag(InventoryDragEvent e)
        {
            final Player p = (Player)e.getWhoClicked();

            if (!InventoryManagerImpl.this.inventories.containsKey(p.getUniqueId())) return;

            final SmartInventory inv = InventoryManagerImpl.this.inventories.get(p.getUniqueId());

            for (int slot : e.getRawSlots())
            {
                if (slot >= p.getOpenInventory().getTopInventory().getSize()) continue;
                e.setCancelled(true); break;
            }

            inv.getListeners().stream().filter(listener -> listener.getType() == InventoryDragEvent.class).forEach(listener -> ((InventoryListener<InventoryDragEvent>)listener).accept(e));
        }

        @EventHandler(priority = EventPriority.LOW)
        public void onInventoryOpen(InventoryOpenEvent e)
        {
            final Player p = (Player)e.getPlayer();

            if (!InventoryManagerImpl.this.inventories.containsKey(p.getUniqueId())) return;

            final SmartInventory inv = InventoryManagerImpl.this.inventories.get(p.getUniqueId());
            inv.getListeners().stream().filter(listener -> listener.getType() == InventoryOpenEvent.class).forEach(listener -> ((InventoryListener<InventoryOpenEvent>)listener).accept(e));
        }

        @EventHandler(priority = EventPriority.LOW)
        public void onInventoryClose(InventoryCloseEvent e)
        {
            final Player p = (Player)e.getPlayer();

            if (!InventoryManagerImpl.this.inventories.containsKey(p.getUniqueId())) return;

            final SmartInventory inv = InventoryManagerImpl.this.inventories.get(p.getUniqueId());

            inv.getListeners().stream().filter(listener -> listener.getType() == InventoryCloseEvent.class).forEach(listener -> ((InventoryListener<InventoryCloseEvent>)listener).accept(e));

            if (inv.isCloseable())
            {
                e.getInventory().clear();
                InventoryManagerImpl.this.inventories.remove(p.getUniqueId());
                InventoryManagerImpl.this.contents.remove(p.getUniqueId());
            }
            else Bukkit.getScheduler().runTask(plugin, () -> p.openInventory(e.getInventory()));
        }

        @EventHandler(priority = EventPriority.LOW)
        public void onPlayerQuit(PlayerQuitEvent e)
        {
            final Player p = e.getPlayer();

            if (!InventoryManagerImpl.this.inventories.containsKey(p.getUniqueId())) return;

            final SmartInventory inv = InventoryManagerImpl.this.inventories.get(p.getUniqueId());

            inv.getListeners().stream().filter(listener -> listener.getType() == PlayerQuitEvent.class).forEach(listener -> ((InventoryListener<PlayerQuitEvent>)listener).accept(e));
            InventoryManagerImpl.this.inventories.remove(p.getUniqueId());
            InventoryManagerImpl.this.contents.remove(p.getUniqueId());
        }

        @EventHandler(priority = EventPriority.LOW)
        public void onPluginDisable(PluginDisableEvent e)
        {
            new HashMap<>(InventoryManagerImpl.this.inventories).forEach((player, inv) -> {
                inv.getListeners().stream().filter(listener -> listener.getType() == PluginDisableEvent.class).forEach(listener -> ((InventoryListener<PluginDisableEvent>)listener).accept(e));
                inv.close(Bukkit.getPlayer(player));
            });

            InventoryManagerImpl.this.inventories.clear();
            InventoryManagerImpl.this.contents.clear();
        }
    }

    @Override
    public void clean()
    {
        this.inventories.clear();
        this.contents.clear();
        this.openers.clear();
    }
}
