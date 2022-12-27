package com.poixpixelcustom.Event;

import com.poixpixelcustom.PoixpixelCustom;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.logging.Logger;

public class MovementListener implements Listener {

    private final Logger logger;

    public MovementListener(PoixpixelCustom plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.logger = plugin.getLogger();
    }

    @EventHandler
    public void movement(PlayerMoveEvent event) {
        Location loc = event.getPlayer().getLocation();
        loc.setY(loc.getY() - 1);
        Block b = loc.getBlock();
        Material material = b.getType();
        switch (material) {
            case WATER:
                b.setType(Material.WATER);
                break;
            case GRASS:
                b.setType(Material.GRASS);
                break;
            case SAND:
                b.setType(Material.SAND);
                break;
        }
    }
}
