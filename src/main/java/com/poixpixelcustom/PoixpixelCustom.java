package com.poixpixelcustom;

import com.poixpixelcustom.commands.*;
import com.poixpixelcustom.listeners.ChatListener;
import com.poixpixelcustom.listeners.EntityListener;
import com.poixpixelcustom.listeners.GuiListener;
import com.poixpixelcustom.listeners.LaserPointerListener;
import com.poixpixelcustom.tasks.Board;
import com.poixpixelcustom.tasks.ButterflyTask;
import com.poixpixelcustom.tasks.LaserPointerTask;
import com.poixpixelcustom.utils.ConfigHandler;
import com.poixpixelcustom.utils.CustomRecipes;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.logging.Logger;

import static com.poixpixelcustom.utils.ExceptionHandeler.handleException;

public class PoixpixelCustom extends JavaPlugin {
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
            handleException(e, true);
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
            handleException(e, false);
            log.severe(String.format("[%s] Plugin Disabled With Errors", this.getName()));
        }
    }

    /**
     * Setup method for initializing the Plugin on startup.
     */
    private void setup() {
        Metrics metrics = new Metrics(this, 20841);

        /*
         * Register Listeners
         */
        getServer().getPluginManager().registerEvents(new EntityListener(), this);
        getServer().getPluginManager().registerEvents(new GuiListener(), this);
        getServer().getPluginManager().registerEvents(new LaserPointerListener(), this);
        getServer().getPluginManager().registerEvents(new ChatListener(), this);

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

    /**
     * Cancels the running tasks if they are not already cancelled.
     *
     * This method checks if each task is not null and not already cancelled,
     * and if so, cancels the task.
     */
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
     * Retrieves an instance of PoixpixelCustom.
     *
     * @return an instance of PoixpixelCustom
     */
    public static PoixpixelCustom getInstance() {
        return getPlugin(PoixpixelCustom.class);
    }
}