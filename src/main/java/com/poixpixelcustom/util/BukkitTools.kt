package com.poixpixelcustom.util

import com.poixpixelcustom.PoixpixelCustom
import org.bukkit.Server

object BukkitTools {
    private var plugin: PoixpixelCustom? = null
    private var server: Server? = null
    fun initialize(plugin: PoixpixelCustom) {
        BukkitTools.plugin = plugin
        server = plugin.server
    }

}