package fr.fistin.fistinframework.smartinvs;

import fr.fistin.fistinframework.smartinvs.content.InventoryContents;
import fr.fistin.fistinframework.smartinvs.content.InventoryContentsWrapper;
import fr.fistin.fistinframework.smartinvs.opener.InventoryOpener;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

import java.util.List;
import java.util.Optional;

public interface InventoryManager
{
    void init();
    Optional<InventoryOpener> findOpener(InventoryType type);
    void registerOpeners(InventoryOpener... openers);
    List<Player> getOpenedPlayers(SmartInventory inv);
    Optional<SmartInventory> getInventory(Player p);
    void setInventory(Player p, SmartInventory inv);
    Optional<InventoryContents> getContents(Player p);
    void setContents(Player p, InventoryContents contents);
    InventoryContentsWrapper getContentsWrapper();
    SmartInventory.Builder smartInventoryBuilder();
}
