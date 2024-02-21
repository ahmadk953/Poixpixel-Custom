package com.poixpixelcustom.listeners;

import com.poixpixelcustom.utils.ConfigHandler;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

public class EntityListener implements Listener {

    @EventHandler
    public void onEntityRightClick(PlayerInteractEntityEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) return;

        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();

        if (entity.getType() == ConfigHandler.getInstance().getExplodingType() && entity.hasMetadata("PoixpixelCustom") && player.getInventory().getItemInMainHand().getType() == Material.BUCKET) {
            if (!player.hasPermission("poixpixelcustom.explodingentity.use")) {
                player.sendMessage("You don't have permission to milk this entity!");

                return;
            }

            entity.getWorld().createExplosion(entity.getLocation(), 2.5F);
        }
    }
}
