package fr.fistin.fistinframework.anvilgui;

import fr.fistin.api.plugin.providers.IBukkitPluginProvider;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.function.BiFunction;
import java.util.function.Consumer;

/**
 * An anvil gui (API), used for gathering a user's input
 *
 * @author Wesley Smith
 * @since 1.0
 */
public interface AnvilGUI
{
    /**
     * Closes the inventory if it's open.
     */
    void closeInventory();

    /**
     * Returns the Bukkit inventory for this anvil gui
     *
     * @return the {@link Inventory} for this anvil gui
     */
    Inventory getInventory();

    /**
     * A builder class for an {@link AnvilGUI} object
     */
    interface Builder
    {
        /**
         * Prevents the closing of the anvil GUI by the user
         *
         * @return The {@link Builder} instance
         */
        Builder preventClose();

        /**
         * Listens for when the inventory is closed
         *
         * @param closeListener An {@link Consumer} that is called when the anvil GUI is closed
         * @return The {@link Builder} instance
         * @throws IllegalArgumentException when the closeListener is null
         */
        Builder onClose(Consumer<Player> closeListener);

        /**
         * Listens for when the first input slot is clicked
         *
         * @param inputLeftClickListener An {@link Consumer} that is called when the first input slot is clicked
         * @return The {@link Builder} instance
         */
        Builder onLeftInputClick(Consumer<Player> inputLeftClickListener);

        /**
         * Listens for when the second input slot is clicked
         *
         * @param inputRightClickListener An {@link Consumer} that is called when the second input slot is clicked
         * @return The {@link Builder} instance
         */
        Builder onRightInputClick(Consumer<Player> inputRightClickListener);

        /**
         * Handles the inventory output slot when it is clicked
         *
         * @param completeFunction An {@link BiFunction} that is called when the user clicks the output slot
         * @return The {@link Builder} instance
         * @throws IllegalArgumentException when the completeFunction is null
         */
        Builder onComplete(BiFunction<Player, String, Response> completeFunction);

        /**
         * Sets the plugin for the {@link AnvilGUI}
         *
         * @param plugin The {@link IBukkitPluginProvider} this anvil GUI is associated with
         * @return The {@link Builder} instance
         * @throws IllegalArgumentException if the plugin is null
         */
        Builder plugin(IBukkitPluginProvider plugin);

        /**
         * Sets the initial item-text that is displayed to the user
         *
         * @param text The initial name of the item in the anvil
         * @return The {@link Builder} instance
         * @throws IllegalArgumentException if the text is null
         */
        Builder text(String text);

        /**
         * Sets the AnvilGUI title that is to be displayed to the user
         *
         * @param title The title that is to be displayed to the user
         * @return The {@link Builder} instance
         * @throws IllegalArgumentException if the title is null
         */
        Builder title(String title);

        /**
         * Sets the {@link ItemStack} to be put in the first slot
         *
         * @param item The {@link ItemStack} to be put in the first slot
         * @return The {@link Builder} instance
         * @throws IllegalArgumentException if the {@link ItemStack} is null
         */
        Builder itemLeft(ItemStack item);

        /**
         * Sets the {@link ItemStack} to be put in the second slot
         *
         * @param item The {@link ItemStack} to be put in the second slot
         * @return The {@link Builder} instance
         */
        Builder itemRight(ItemStack item);

        /**
         * Creates the anvil GUI and opens it for the player
         *
         * @param player The {@link Player} the anvil GUI should open for
         * @return The {@link AnvilGUI} instance from this builder
         * @throws IllegalArgumentException when the onComplete function, plugin, or player is null
         */
        AnvilGUI open(Player player);
    }

    class Response
    {
        /**
         * The text that is to be displayed to the user
         */
        private final String text;
        private final Inventory openInventory;

        /**
         * Creates a response to the user's input
         *
         * @param text The text that is to be displayed to the user, which can be null to close the inventory
         */
        private Response(String text, Inventory openInventory)
        {
            this.text = text;
            this.openInventory = openInventory;
        }

        /**
         * Gets the text that is to be displayed to the user
         *
         * @return The text that is to be displayed to the user
         */
        public String getText()
        {
            return this.text;
        }

        /**
         * Gets the inventory that should be opened
         *
         * @return The inventory that should be opened
         */
        public Inventory getInventoryToOpen()
        {
            return this.openInventory;
        }

        /**
         * Returns an {@link Response} object for when the anvil GUI is to close
         *
         * @return An {@link Response} object for when the anvil GUI is to close
         */
        public static Response close()
        {
            return new Response(null, null);
        }

        /**
         * Returns an {@link Response} object for when the anvil GUI is to display text to the user
         *
         * @param text The text that is to be displayed to the user
         * @return An {@link Response} object for when the anvil GUI is to display text to the user
         */
        public static Response text(String text)
        {
            return new Response(text, null);
        }

        /**
         * Returns an {@link Response} object for when the GUI should open the provided inventory
         *
         * @param inventory The inventory to open
         * @return The {@link Response} to return
         */
        public static Response openInventory(Inventory inventory)
        {
            return new Response(null, inventory);
        }
    }
}
