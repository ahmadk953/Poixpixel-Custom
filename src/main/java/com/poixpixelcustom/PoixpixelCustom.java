package com.poixpixelcustom;

import com.poixpixelcustom.Commands.EnrichCommand;
import com.poixpixelcustom.util.Version;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import org.bukkit.Bukkit;

import com.poixpixelcustom.Commands.*;
import com.poixpixelcustom.util.*;

import java.util.ArrayList;
import java.util.List;

public final class PoixpixelCustom extends JavaPlugin {

    private static final Version OLDEST_MC_VER_SUPPORTED = Version.fromString("1.19");
    private static final Version CUR_BUKKIT_VER = Version.fromString(Bukkit.getBukkitVersion());
    private final String version = this.getDescription().getVersion();

    private static PoixpixelCustom plugin;

    private int pluginsFound = 0;

    public PoixpixelCustom() {
        plugin = this;
    }

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveConfig();

        this.getCommand("enrich").setExecutor(new EnrichCommand());
        getLogger().info("Added the Enrich command.");

        checkPlugins();

        if (pluginsFound > 0) {
            getLogger().info("Add-on plugins found!");
            getLogger().info(pluginsFound + " add-on plugins found");
        } else {
            getLogger().warning("No add-on plugins were found.");
            getLogger().warning("The plugin might run with limited functionality.");
        }
    }

    @Override
    public void onDisable() {
        //Plugin shutdown logic
        saveConfig();
        getLogger().info("Plugin was disabled successfully!");
    }

    private void checkPlugins() {

        plugin.getLogger().info("Searching for third-party plugins...");
        String ecowarn = "";
        List<String> addons = new ArrayList<>();
        Plugin test;

        /*
         * Check add-ons and third-party plugins we use.
         */

        test = getServer().getPluginManager().getPlugin("Vault");
        if (test != null) {
            addons.add(String.format("%s v%s", "Vault", test.getDescription().getVersion()));
            pluginsFound = pluginsFound + 1;
        }

        test = getServer().getPluginManager().getPlugin("Essentials");
        if (test != null) {
            addons.add(String.format("%s v%s", "Essentials", test.getDescription().getVersion()));
            pluginsFound = pluginsFound + 1;
        }

    }
}
