package com.poixpixelcustom.listeners;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class VillagerListener implements Listener {
    @EventHandler
    public void onInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();

        if (entity instanceof Villager villager && player.isSneaking() && player.getInventory().getItemInMainHand().equals(ItemStack.of(Material.STICK))) {
            Inventory inv = Bukkit.createInventory(null, 9, Component.text("Villager Inventory"));

            // Add the villager's items to the inventory
            for (ItemStack item : villager.getInventory().getContents()) {
                if (item != null) {
                    inv.addItem(item);
                }
            }

            // Show the inventory to the player
            event.setCancelled(true);
            player.openInventory(inv);
        }
    }
}
