package com.poixpixelcustom.listeners;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.RayTraceResult;

public final class LaserPointerListener implements Listener {

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

        if (hand.hasItemMeta() && hand.getItemMeta().getDisplayName().equals(ChatColor.WHITE + "Laser Pointer")) {
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
