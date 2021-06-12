package fr.fistin.fistinframework.smartinvs;

import fr.fistin.fistinframework.smartinvs.content.InventoryProvider;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import java.util.List;
import java.util.Optional;

public interface SmartInventory
{
    Inventory open(Player player);
    Inventory open(Player player, int page);
    void close(Player player);
    String getId();
    String getTitle();
    InventoryType getType();
    int getRows();
    int getColumns();
    boolean isCloseable();
    void setCloseable(boolean closeable);
    InventoryProvider getProvider();
    Optional<SmartInventory> getParent();
    InventoryManager getManager();
    List<InventoryListener<? extends Event>> getListeners();

    interface Builder
    {
        Builder id(String id);
        Builder title(String title);
        Builder type(InventoryType type);
        Builder size(int rows, int columns);
        Builder closeable(boolean closeable);
        Builder provider(InventoryProvider provider);
        Builder parent(SmartInventory parent);
        Builder listener(InventoryListener<? extends Event> listener);
        Builder manager(InventoryManager manager);
        SmartInventory build();
    }
}