package fr.fistin.fistinframework.impl;

import fr.fistin.fistinframework.anvilgui.VersionWrapper;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.ApiStatus;

/**
 * {@link VersionWrapper} implemented for NMS version 1_8_R3
 *
 * @author Wesley Smith
 * @since 1.0
 */
@ApiStatus.Internal
class Wrapper18R3 implements VersionWrapper
{
    /**
     * {@inheritDoc}
     */
    @Override
    public int getNextContainerId(Player player, Object container)
    {
        return this.toNMS(player).nextContainerCounter();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleInventoryCloseEvent(Player player)
    {
        CraftEventFactory.handleInventoryCloseEvent(this.toNMS(player));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendPacketOpenWindow(Player player, int containerId, String guiTitle)
    {
        this.toNMS(player).playerConnection.sendPacket(new PacketPlayOutOpenWindow(containerId, "minecraft:anvil", new ChatMessage(Blocks.ANVIL.a() + ".name")));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendPacketCloseWindow(Player player, int containerId)
    {
        this.toNMS(player).playerConnection.sendPacket(new PacketPlayOutCloseWindow(containerId));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setActiveContainerDefault(Player player)
    {
        this.toNMS(player).activeContainer = this.toNMS(player).defaultContainer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setActiveContainer(Player player, Object container)
    {
        this.toNMS(player).activeContainer = (Container)container;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setActiveContainerId(Object container, int containerId)
    {
        ((Container)container).windowId = containerId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addActiveContainerSlotListener(Object container, Player player)
    {
        ((Container)container).addSlotListener(this.toNMS(player));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Inventory toBukkitInventory(Object container)
    {
        return ((Container)container).getBukkitView().getTopInventory();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object newContainerAnvil(Player player, String guiTitle)
    {
        return new AnvilContainer(this.toNMS(player));
    }

    /**
     * Turns a {@link Player} into an NMS one
     *
     * @param player The player to be converted
     * @return the NMS EntityPlayer
     */
    private EntityPlayer toNMS(Player player)
    {
        return ((CraftPlayer)player).getHandle();
    }

    /**
     * Modifications to ContainerAnvil that makes it so you don't have to have xp to use this anvil
     */
    private static class AnvilContainer extends ContainerAnvil
    {
        public AnvilContainer(EntityHuman entityhuman)
        {
            super(entityhuman.inventory, entityhuman.world, new BlockPosition(0, 0, 0), entityhuman);
        }

        @Override
        public boolean a(EntityHuman human)
        {
            return true;
        }

        @Override
        public void b(EntityHuman entityhuman) {}
    }
}
