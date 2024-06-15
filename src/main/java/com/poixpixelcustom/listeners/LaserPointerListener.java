package com.poixpixelcustom.listeners;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.RayTraceResult;

public final class LaserPointerListener implements Listener {

    /**
     * Handles the event when a player interacts with an item in their hand. If the player right clicks in the air,
     * checks if they have the permission to use the laser and if the item in their hand is a laser pointer. If so,
     * traces a line in the air and creates an explosion at the location of the hit block if it is a solid block. If the
     * hit block is too far or not a solid block, sends a message to the player.
     *
     * @param  event  the PlayerInteractEvent representing the interaction event
     */
    @EventHandler
    public void onClick(final PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.HAND || event.getAction() != Action.RIGHT_CLICK_AIR)
            return;


        Player player = event.getPlayer();
        ItemStack hand = player.getItemInHand();
        int distance = 100;

        if (!player.hasPermission("poixpixelcustom.laser.use")) {
            player.sendMessage(Component
                    .text("[Laser] ")
                    .color(NamedTextColor.LIGHT_PURPLE)
                    .append(Component.text("You do not have permission to use the laser!").color(NamedTextColor.WHITE))
            );
            return;
        }

        if (hand.hasItemMeta() && hand.getItemMeta().displayName().equals(NamedTextColor.WHITE + "Laser Pointer")) {
            RayTraceResult result = player.rayTraceBlocks(distance);

            if (result != null && result.getHitBlock() != null && result.getHitBlock().isSolid())
                player.getWorld().createExplosion(result.getHitBlock().getLocation(), 5F, true);
            else
                player.sendMessage(Component
                        .text("[Laser] ")
                        .color(NamedTextColor.LIGHT_PURPLE)
                        .append(Component.text("Target is too far or not a solid block!").color(NamedTextColor.WHITE))
                );
        }
    }
}
