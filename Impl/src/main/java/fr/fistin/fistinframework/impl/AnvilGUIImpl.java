package fr.fistin.fistinframework.impl;

import fr.fistin.api.plugin.providers.IBukkitPluginProvider;
import fr.fistin.fistinframework.anvilgui.AnvilGUI;
import fr.fistin.fistinframework.anvilgui.VersionWrapper;
import fr.fistin.fistinframework.utils.FistinValidate;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.ApiStatus;

import java.util.function.BiFunction;
import java.util.function.Consumer;

/**
 * An anvil gui (impl), used for gathering a user's input
 *
 * @author Wesley Smith
 * @since 1.0
 */
@ApiStatus.Internal
class AnvilGUIImpl implements AnvilGUI
{
    /**
     * The local {@link VersionWrapper} object for the server's version
     */
    private static final VersionWrapper WRAPPER = new Wrapper18R3();

    /**
     * The {@link IBukkitPluginProvider} that this anvil GUI is associated with
     */
    private final IBukkitPluginProvider plugin;
    /**
     * The player who has the GUI open
     */
    private final Player player;
    /**
     * The title of the anvil inventory
     */
    private final String inventoryTitle;
    /**
     * The ItemStack that is in the {@link Slot#INPUT_LEFT} slot.
     */
    private ItemStack inputLeft;
    /**
     * The ItemStack that is in the {@link Slot#INPUT_RIGHT} slot.
     */
    private final ItemStack inputRight;
    /**
     * A state that decides where the anvil GUI is able to be closed by the user
     */
    private final boolean preventClose;
    /**
     * An {@link Consumer} that is called when the anvil GUI is closed
     */
    private final Consumer<Player> closeListener;
    /**
     * An {@link BiFunction} that is called when the {@link Slot#OUTPUT} slot has been clicked
     */
    private final BiFunction<Player, String, Response> completeFunction;

    /**
     * An {@link Consumer} that is called when the {@link Slot#INPUT_LEFT} slot has been clicked
     */
    private final Consumer<Player> inputLeftClickListener;
    /**
     * An {@link Consumer} that is called when the {@link Slot#INPUT_RIGHT} slot has been clicked
     */
    private final Consumer<Player> inputRightClickListener;

    /**
     * The container id of the inventory, used for NMS methods
     */
    private int containerId;
    /**
     * The inventory that is used on the Bukkit side of things
     */
    private Inventory inventory;
    /**
     * The listener holder class
     */
    private final ListenUp listener = new ListenUp();

    /**
     * Represents the state of the inventory being open
     */
    private boolean open;

    /**
     * Create an AnvilGUI and open it for the player.
     *
     * @param plugin           A {@link IBukkitPluginProvider} instance
     * @param player           The {@link Player} to open the inventory for
     * @param inventoryTitle   What to have the text already set to
     * @param itemText         The name of the item in the first slot of the anvilGui
     * @param inputLeft        The material of the item in the first slot of the anvilGUI
     * @param preventClose     Whether to prevent the inventory from closing
     * @param closeListener    A {@link Consumer} when the inventory closes
     * @param completeFunction A {@link BiFunction} that is called when the player clicks the {@link Slot#OUTPUT} slot
     */
    private AnvilGUIImpl(IBukkitPluginProvider plugin, Player player,
            String inventoryTitle, String itemText,
            ItemStack inputLeft, ItemStack inputRight,
            boolean preventClose, Consumer<Player> closeListener,
            Consumer<Player> inputLeftClickListener, Consumer<Player> inputRightClickListener,
            BiFunction<Player, String, Response> completeFunction)
    {
        this.plugin = plugin;
        this.player = player;
        this.inventoryTitle = inventoryTitle;
        this.inputLeft = inputLeft;
        this.inputRight = inputRight;
        this.preventClose = preventClose;
        this.closeListener = closeListener;
        this.inputLeftClickListener = inputLeftClickListener;
        this.inputRightClickListener = inputRightClickListener;
        this.completeFunction = completeFunction;

        if (itemText != null)
        {
            if (inputLeft == null)
                this.inputLeft = new ItemStack(Material.PAPER);

            final ItemMeta paperMeta = this.inputLeft.getItemMeta();
            paperMeta.setDisplayName(itemText);
            this.inputLeft.setItemMeta(paperMeta);
        }

        this.openInventory();
    }

    /**
     * Opens the anvil GUI
     */
    private void openInventory()
    {
        WRAPPER.handleInventoryCloseEvent(this.player);
        WRAPPER.setActiveContainerDefault(this.player);

        Bukkit.getPluginManager().registerEvents(this.listener, this.plugin);

        final Object container = WRAPPER.newContainerAnvil(this.player, this.inventoryTitle);

        this.inventory = WRAPPER.toBukkitInventory(container);
        this.inventory.setItem(Slot.INPUT_LEFT, this.inputLeft);

        if (this.inputRight != null)
            inventory.setItem(Slot.INPUT_RIGHT, this.inputRight);

        containerId = WRAPPER.getNextContainerId(this.player, container);
        WRAPPER.sendPacketOpenWindow(this.player, this.containerId, this.inventoryTitle);
        WRAPPER.setActiveContainer(this.player, container);
        WRAPPER.setActiveContainerId(container, containerId);
        WRAPPER.addActiveContainerSlotListener(container, this.player);
        this.open = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void closeInventory()
    {
        this.closeInventory(true);
    }

    /**
     * Closes the inventory if it's open, only sending the close inventory packets if the arg is true
     * @param sendClosePacket Whether to send the close inventory event, packet, etc
     */
    private void closeInventory(boolean sendClosePacket)
    {
        if (!this.open) return;

        this.open = false;

        if (sendClosePacket)
        {
            WRAPPER.handleInventoryCloseEvent(this.player);
            WRAPPER.setActiveContainerDefault(this.player);
            WRAPPER.sendPacketCloseWindow(this.player, this.containerId);
        }

        HandlerList.unregisterAll(this.listener);

        if (this.closeListener != null)
            this.closeListener.accept(this.player);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Inventory getInventory()
    {
        return this.inventory;
    }

    /**
     * Simply holds the listeners for the GUI
     */
    private class ListenUp implements Listener
    {
        @EventHandler
        public void onInventoryClick(InventoryClickEvent event) {
            if (event.getInventory().equals(AnvilGUIImpl.this.inventory) && (event.getRawSlot() < 3
                    || event.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY)))
            {
                event.setCancelled(true);
                final Player clicker = (Player) event.getWhoClicked();
                if (event.getRawSlot() == Slot.OUTPUT) {
                    final ItemStack clicked = AnvilGUIImpl.this.inventory.getItem(Slot.OUTPUT);
                    if (clicked == null || clicked.getType() == Material.AIR) return;

                    final Response response = completeFunction.apply(
                            clicker,
                            clicked.hasItemMeta() ? clicked.getItemMeta().getDisplayName() : ""
                    );
                    if (response.getText() != null)
                    {
                        final ItemMeta meta = clicked.getItemMeta();
                        meta.setDisplayName(response.getText());
                        clicked.setItemMeta(meta);
                        AnvilGUIImpl.this.inventory.setItem(Slot.INPUT_LEFT, clicked);
                    }
                    else if (response.getInventoryToOpen() != null) clicker.openInventory(response.getInventoryToOpen());
                    else AnvilGUIImpl.this.closeInventory();
                }
                else if (event.getRawSlot() == Slot.INPUT_LEFT)
                    if (AnvilGUIImpl.this.inputLeftClickListener != null)
                        AnvilGUIImpl.this.inputLeftClickListener.accept(AnvilGUIImpl.this.player);
                else if (event.getRawSlot() == Slot.INPUT_RIGHT)
                    if (AnvilGUIImpl.this.inputRightClickListener != null)
                        AnvilGUIImpl.this.inputRightClickListener.accept(AnvilGUIImpl.this.player);
            }
        }

        @EventHandler
        public void onInventoryDrag(InventoryDragEvent event)
        {
            if (event.getInventory().equals(AnvilGUIImpl.this.inventory))
            {
                for (int slot : Slot.values())
                {
                    if (event.getRawSlots().contains(slot))
                    {
                        event.setCancelled(true);
                        break;
                    }
                }
            }
        }

        @EventHandler
        public void onInventoryClose(InventoryCloseEvent event)
        {
            if (AnvilGUIImpl.this.open && event.getInventory().equals(AnvilGUIImpl.this.inventory))
            {
                AnvilGUIImpl.this.closeInventory(false);
                if (AnvilGUIImpl.this.preventClose) Bukkit.getScheduler().runTask(AnvilGUIImpl.this.plugin, AnvilGUIImpl.this::openInventory);
            }
        }
    }

    /**
     * A builder class for an {@link AnvilGUI} object
     */
    public static class BuilderImpl implements Builder
    {
        /**
         * An {@link Consumer} that is called when the anvil GUI is closed
         */
        private Consumer<Player> closeListener;
        /**
         * A state that decides where the anvil GUI is able to be closed by the user
         */
        private boolean preventClose = false;
        /**
         * An {@link Consumer} that is called when the {@link Slot#INPUT_LEFT} slot has been clicked
         */
        private Consumer<Player> inputLeftClickListener;
        /**
         * An {@link Consumer} that is called when the {@link Slot#INPUT_RIGHT} slot has been clicked
         */
        private Consumer<Player> inputRightClickListener;
        /**
         * An {@link BiFunction} that is called when the anvil output slot has been clicked
         */
        private BiFunction<Player, String, Response> completeFunction;
        /**
         * The {@link IBukkitPluginProvider} that this anvil GUI is associated with
         */
        private IBukkitPluginProvider plugin;
        /**
         * The text that will be displayed to the user
         */
        private String title = "Repair & Name";
        /**
         * The starting text on the item
         */
        private String itemText;
        /**
         * An {@link ItemStack} to be put in the left input slot
         */
        private ItemStack itemLeft;
        /**
         * An {@link ItemStack} to be put in the right input slot
         */
        private ItemStack itemRight;

        /**
         * {@inheritDoc}
         */
        @Override
        public Builder preventClose()
        {
            this.preventClose = true;
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Builder onClose(Consumer<Player> closeListener)
        {
            FistinValidate.notNull(closeListener, "closeListener cannot be null");
            this.closeListener = closeListener;
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Builder onLeftInputClick(Consumer<Player> inputLeftClickListener)
        {
            this.inputLeftClickListener = inputLeftClickListener;
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Builder onRightInputClick(Consumer<Player> inputRightClickListener)
        {
            this.inputRightClickListener = inputRightClickListener;
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Builder onComplete(BiFunction<Player, String, Response> completeFunction)
        {
            FistinValidate.notNull(completeFunction, "Complete function cannot be null");
            this.completeFunction = completeFunction;
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Builder plugin(IBukkitPluginProvider plugin)
        {
            FistinValidate.notNull(plugin, "Plugin cannot be null");
            this.plugin = plugin;
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Builder text(String text)
        {
            FistinValidate.notNull(text, "Text cannot be null");
            this.itemText = text;
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Builder title(String title)
        {
            FistinValidate.notNull(title, "title cannot be null");
            this.title = title;
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Builder itemLeft(ItemStack item)
        {
            FistinValidate.notNull(item, "item cannot be null");
            this.itemLeft = item;
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Builder itemRight(ItemStack item)
        {
            this.itemRight = item;
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public AnvilGUI open(Player player)
        {
            FistinValidate.notNull(this.plugin, "IBukkitPluginProvider cannot be null");
            FistinValidate.notNull(this.completeFunction, "Complete function cannot be null");
            FistinValidate.notNull(player, "Player cannot be null");
            return new AnvilGUIImpl(this.plugin,
                                    player, this.title,
                                    this.itemText, this.itemLeft,
                                    this.itemRight, this.preventClose,
                                    this.closeListener, this.inputLeftClickListener,
                                    this.inputRightClickListener, this.completeFunction);
        }
    }

    /**
     * Class wrapping the magic constants of slot numbers in an anvil GUI
     */
    public static class Slot
    {
        private static final int[] VALUES = new int[]{Slot.INPUT_LEFT, Slot.INPUT_RIGHT, Slot.OUTPUT};

        /**
         * The slot on the far left, where the first input is inserted. An {@link ItemStack} is always inserted
         * here to be renamed
         */
        public static final int INPUT_LEFT = 0;
        /**
         * Not used, but in a real anvil you are able to put the second item you want to combine here
         */
        public static final int INPUT_RIGHT = 1;
        /**
         * The output slot, where an item is put when two items are combined from {@link #INPUT_LEFT} and
         * {@link #INPUT_RIGHT} or {@link #INPUT_LEFT} is renamed
         */
        public static final int OUTPUT = 2;

        /**
         * Get all anvil slot values
         *
         * @return The array containing all possible anvil slots
         */
        public static int[] values()
        {
            return VALUES;
        }
    }
}
