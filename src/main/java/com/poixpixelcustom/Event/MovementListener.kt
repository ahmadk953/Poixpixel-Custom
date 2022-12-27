package com.poixpixelcustom.Event

import com.poixpixelcustom.PoixpixelCustom
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent
import java.util.logging.Logger

class MovementListener(plugin: PoixpixelCustom) : Listener {
    private val logger: Logger

    init {
        plugin.server.pluginManager.registerEvents(this, plugin)
        logger = plugin.logger
    }

    @EventHandler
    fun movement(event: PlayerMoveEvent) {
        val loc = event.player.location
        loc.y = loc.y - 1
        val b = loc.block
        val material = b.type
        when (material) {
            Material.WATER -> b.type = Material.WATER
            Material.GRASS -> b.type = Material.GRASS
            Material.SAND -> b.type = Material.SAND
            else -> {return}
        }
    }
}
