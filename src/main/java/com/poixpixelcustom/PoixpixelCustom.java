package com.poixpixelcustom;

import org.bstats.bukkit.Metrics;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class PoixpixelCustom extends JavaPlugin {

    private static final Logger log = Logger.getLogger("Minecraft");

    private Economy econ = null;
    private static Permission perms = null;
    private static Chat chat = null;

    /*
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

    /*
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
        if (!setupEconomy()) {
            throw new RuntimeException("Disabled due to no Vault dependency found!");
        }
        setupPermissions();
        setupChat();
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
     * Setup the economy for the plugin.
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
     * Setup the chat functionality.
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
     * Command Handling
     *
     * @param sender       the command sender
     * @param command      the command being executed
     * @param commandLabel the label of the command
     * @param args         the arguments for the command
     * @return true if the command was handled
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) {
            getLogger().info("Only players are supported for this Example Plugin, but you should not do this!!!");
            return true;
        }

        Player player = (Player) sender;

        if (command.getLabel().equals("test-economy")) {
            // Lets give the player 1.05 currency (note that SOME economic plugins require
            // rounding!)
            sender.sendMessage(String.format("You have %s", econ.format(econ.getBalance(player.getName()))));
            EconomyResponse r = econ.depositPlayer(player, 1.05);
            if (r.transactionSuccess()) {
                sender.sendMessage(String.format("You were given %s and now have %s", econ.format(r.amount),
                        econ.format(r.balance)));
            } else {
                sender.sendMessage(String.format("An error occured: %s", r.errorMessage));
            }
            return true;
        } else if (command.getLabel().equals("test-permission")) {
            if (perms.has(player, "poixpixelcustom.awesome")) {
                sender.sendMessage("You are awesome!");
            } else {
                sender.sendMessage("You suck!");
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Get the economy instance.
     * 
     * @return the economy instance
     */
    public Economy getEconomy() {
        return econ;
    }

    /**
     * Get the permission instance.
     * 
     * @return the permission instance
     */
    public static Permission getPermissions() {
        return perms;
    }

    /**
     * Get the chat instance.
     * 
     * @return the chat instance
     */
    public static Chat getChat() {
        return chat;
    }
}