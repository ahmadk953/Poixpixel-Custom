package com.poixpixelcustom.utils;

import com.poixpixelcustom.PoixpixelCustom;
import org.bukkit.plugin.Plugin;

import java.util.logging.Logger;

public class ExceptionHandeler {

    private static final Logger log = Logger.getLogger("Minecraft");
    private static final Plugin plugin = PoixpixelCustom.getInstance();

    /**
     * Handles an exception, logging an error message and potentially disabling a plugin.
     *
     * @param  e              the exception to be handled
     * @param  disablePlugin  whether the plugin should be disabled
     */
    public static void handleException( Exception e, Boolean disablePlugin) {
        log.severe(String.format("[%s] There was an Error in the Plugin: %s", plugin.getName(), e.getMessage()));

        if (disablePlugin) {
            plugin.getServer().getPluginManager().disablePlugin(plugin);
        }
    }
}
