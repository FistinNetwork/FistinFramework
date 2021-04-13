package fr.fistin.fistinframework.smartinvs.content;

import fr.fistin.fistinframework.smartinvs.SmartInventory;

import java.util.UUID;

@FunctionalInterface
public interface InventoryContentsWrapper
{
    InventoryContents newImpl(SmartInventory inv, UUID player);
}
