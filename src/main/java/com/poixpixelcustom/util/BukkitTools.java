package com.poixpixelcustom.util;

import com.poixpixelcustom.PoixpixelCustom;
import org.bukkit.Server;

public class BukkitTools {
    private static PoixpixelCustom plugin = null;
    private static Server server = null;

    public static void initialize(PoixpixelCustom plugin) {
        BukkitTools.plugin = plugin;
        BukkitTools.server = plugin.getServer();
    }

}
