package com.poixpixelcustom;

import com.poixpixelcustom.commands.ExplodingEntityCommand;
import com.poixpixelcustom.listeners.EntityListener;

import com.poixpixelcustom.utils.ConfigHandler;
import org.bstats.bukkit.Metrics;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class PoixpixelCustom extends JavaPlugin {
    public Economy econ = null;
    private static Permission perms = null;
    private static Chat chat = null;

    private static final Logger log = Logger.getLogger("Minecraft");

    /**
     * Called when the plugin is enabled
     */
    @Override
    public void onEnable() {
        try {
            setup();
            log.info(String.format("[%s] - Enabled Poixpixel Custom", this.getName()));
        } catch (Exception e) {
            handleException(e);
        }
    }

    /**
     * Called when the plugin is disabled
     */
    @Override
    public void onDisable() {
        log.info(String.format("[%s] - Goodbye", this.getName()));
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

        /*
         * Register Commands
         */
        getCommand("explodingentity").setExecutor(new ExplodingEntityCommand());

        /*
         * Load Config
         */
        ConfigHandler.getInstance().load();
    }

    /**
     * Handle an exception by logging the error and disabling the plugin.
     *
     * @param e the exception to be handled
     * @return void
     */
    private void handleException(Exception e) {
        log.severe(String.format("[%s] - There was an Error in the Plugin: %s", this.getName(), e.getMessage()));
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


    public static PoixpixelCustom getInstance() {
        return getPlugin(PoixpixelCustom.class);
    }
}