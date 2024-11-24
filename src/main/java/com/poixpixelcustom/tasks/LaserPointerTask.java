package com.poixpixelcustom.tasks;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.Objects;

public final class LaserPointerTask implements Runnable {

    private static final LaserPointerTask instance = new LaserPointerTask();

    private LaserPointerTask() {
    }

    /**
     * The run method that executes the laser pointer task for online players.
     */
    @Override
    public void run() {
        int length = 5;
        double particleDistance = 0.5;

        for (Player online : Bukkit.getOnlinePlayers()) {
            ItemStack hand = online.getInventory().getItemInMainHand();

            if (hand.hasItemMeta() && Objects.equals(hand.getItemMeta().displayName(), Component.text("Laser Pointer").color(NamedTextColor.WHITE))) {
                Location location = online.getLocation().add(0, 1, 0);

                for (double waypoint = 1; waypoint < length; waypoint += particleDistance) {
                    Vector vector = location.getDirection().multiply(waypoint);
                    location.add(vector);

                    if (location.getBlock().getType() != Material.AIR && location.getBlock().getType() != Material.WATER)
                        break;

                    location.getWorld().spawnParticle(Particle.DUST, location, 1, new Particle.DustOptions(Color.YELLOW, 0.75F));
                }
            }
        }
    }

    /**
     * Returns the singleton instance of the LaserPointerTask class.
     *
     * @return the singleton instance of the LaserPointerTask class
     */
    public static LaserPointerTask getInstance() {
        return instance;
    }
}
