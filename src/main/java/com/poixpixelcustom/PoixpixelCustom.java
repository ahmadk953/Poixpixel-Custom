package com.poixpixelcustom;

import com.poixpixelcustom.commands.*;
import com.poixpixelcustom.listeners.EntityListener;
import com.poixpixelcustom.listeners.GuiListener;
import com.poixpixelcustom.listeners.LaserPointerListener;
import com.poixpixelcustom.tasks.Board;
import com.poixpixelcustom.tasks.ButterflyTask;
import com.poixpixelcustom.tasks.LaserPointerTask;
import com.poixpixelcustom.utils.ConfigHandler;
import com.poixpixelcustom.utils.CustomRecipes;

import org.bstats.bukkit.Metrics;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.logging.Logger;

public class PoixpixelCustom extends JavaPlugin {
    private Economy econ = null;
    private static Permission perms = null;
    private static Chat chat = null;

    private static final Logger log = Logger.getLogger("Minecraft");

    private BukkitTask butterflyTask;
    private BukkitTask scoreboardTask;
    private BukkitTask laserPointerTask;

    /**
     * Called when the plugin is enabled
     */
    @Override
    public void onEnable() {
        try {
            setup();
            log.info(String.format("[%s] Enabled Poixpixel Custom", this.getName()));
        } catch (Exception e) {
            handleException(e);
        }
    }

    /**
     * Called when the plugin is disabled
     */
    @Override
    public void onDisable() {
        try {
            disablePlugin();
            log.info(String.format("[%s] Plugin Disabled", this.getName()));
        } catch (Exception e) {
            handleException(e);
            log.severe(String.format("[%s] Plugin Disabled With Errors", this.getName()));
        }
    }

    /**
     * Setup method for initializing the Plugin on startup.
     */
    private void setup() {
        Metrics metrics = new Metrics(this, 20841);

        /*
         * Setup Vault
         */
        /*
        if (!setupEconomy()) {
            throw new RuntimeException("Disabled due to no Vault dependency found!");
        }
        setupPermissions();
        setupChat();
         */

        /*
         * Register Listeners
         */
        getServer().getPluginManager().registerEvents(new EntityListener(), this);
        getServer().getPluginManager().registerEvents(new GuiListener(), this);
        getServer().getPluginManager().registerEvents(new LaserPointerListener(), this);

        /*
         * Register Commands
         */
        getCommand("explodingentity").setExecutor(new ExplodingEntityCommand());
        getCommand("butterfly").setExecutor(new ButterflyCommand());
        getCommand("displayentity").setExecutor(new DisplayEntityCommand());
        getCommand("customitem").setExecutor(new CustomItemCommand());
        getCommand("gui").setExecutor(new GuiCommand());

        /*
         * Load Config
         */
        ConfigHandler.getInstance().load();
        log.info("CONFIG: " + String.valueOf(ConfigHandler.getInstance().getConfig()));
        log.info("Explosion Power: " + String.valueOf(ConfigHandler.getInstance().getExplosionPower()));

        /*
         * Start Tasks
         */
        butterflyTask = getServer().getScheduler().runTaskTimer(this, ButterflyTask.getInstance(), 0, 1);
        scoreboardTask = getServer().getScheduler().runTaskTimer(this, Board.getInstance(), 0, 20);
        laserPointerTask = getServer().getScheduler().runTaskTimer(this, LaserPointerTask.getInstance(), 0, 1);

        /*
        * Register Custom Recipes
         */
        CustomRecipes.register();
    }

    private void disablePlugin() {
        /*
         * Cancel Tasks if they are Running
         */
        if (butterflyTask != null && !butterflyTask.isCancelled()) {
            butterflyTask.cancel();
        }
        if (scoreboardTask != null && !scoreboardTask.isCancelled()) {
            scoreboardTask.cancel();
        }
        if (laserPointerTask != null && !laserPointerTask.isCancelled()) {
            laserPointerTask.cancel();
        }
    }

    /**
     * Handle an exception by logging the error and disabling the plugin.
     *
     * @param e the exception to be handled
     */
    private void handleException(Exception e) {
        log.severe(String.format("[%s] There was an Error in the Plugin: %s", this.getName(), e.getMessage()));
        getServer().getPluginManager().disablePlugin(this);
    }

    /**
     * Set up the economy for the plugin.
     *
     * @return true if the economy setup was successful, false otherwise
     */
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    /**
     * Set up the chat functionality.
     *
     * @return true if chat provider is successfully set up, false otherwise
     */
    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }

    /**
     * A private method to set up permissions.
     *
     * @return true if permissions are set up, false otherwise
     */
    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }


    /**
     * Retrieves an instance of PoixpixelCustom.
     *
     * @return an instance of PoixpixelCustom
     */
    public static PoixpixelCustom getInstance() {
        return getPlugin(PoixpixelCustom.class);
    }
}