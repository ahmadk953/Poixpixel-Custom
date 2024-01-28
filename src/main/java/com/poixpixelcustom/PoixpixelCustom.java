package com.poixpixelcustom;

import org.bstats.bukkit.Metrics;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class PoixpixelCustom extends JavaPlugin {

    private static final Logger log = Logger.getLogger("Minecraft");

    @Override
    public void onEnable() {
        Metrics metrics = new Metrics(this, 20841);

        log.info("Enabled Poixpixel Custom");
    }

    @Override
    public void onDisable() {
        log.info("Goodbye");
    }
}