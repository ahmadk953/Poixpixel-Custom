package com.poixpixelcustom.listeners;

import com.poixpixelcustom.PoixpixelCustom;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

public class GuiListener implements Listener {

    /**
     * Handles the click event in a GUI inventory.
     *
     * @param  event  the InventoryClickEvent that triggered the click
     */
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (player.hasMetadata("OpenedMenu")) {
            event.setCancelled(true);

            if (event.getSlot() == 11) {
                player.getInventory().addItem(new ItemStack(Material.DIAMOND));
                player.closeInventory();

            } else if (event.getSlot() == 13) {
                player.getInventory().clear();
                player.closeInventory();

            } else if (event.getSlot() == 15) {
                player.getWorld().setStorm(false);
                player.getWorld().setThundering(false);

                player.closeInventory();
            }
        }
    }

    /**
     * Handles the event when a player closes a GUI inventory. Removes the "OpenedMenu" metadata from the player
     * if it exists.
     *
     * @param event the InventoryCloseEvent that triggered the event
     */
    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();

        if (player.hasMetadata("OpenedMenu"))
            player.removeMetadata("OpenedMenu", PoixpixelCustom.getInstance());
    }
}
