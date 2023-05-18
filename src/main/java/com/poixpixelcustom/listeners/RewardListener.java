package com.poixpixelcustom.listeners;

import com.poixpixelcustom.PoixpixelCustom;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class RewardListener implements Listener {

    private final PoixpixelCustom plugin;
    private final int rewardThreshold = 1500;

    public RewardListener(PoixpixelCustom plugin) {
        this.plugin = plugin;
        File dataFolder = plugin.getDataFolder();
    }
/*
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        double balance = PoixpixelCustom.getEconomy().getBalance(player);

        // Check if the player has reached the reward threshold
        if (balance >= rewardThreshold) {
            // Reward the player with a diamond
            player.getInventory().addItem(new ItemStack(Material.DIAMOND));
            player.sendMessage("Congratulations! You have earned a diamond for reaching $" + rewardThreshold + ".");
            try {
                // Save the player's UUID and reward status to a file
                plugin.getDataManager().addRewardStatus(uuid, true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                // Check if the player has already received the reward
                boolean rewarded = plugin.getDataManager().getRewardStatus(uuid);
                if (rewarded) {
                    player.sendMessage("Welcome back! You have already received the reward for reaching $" + rewardThreshold + ".");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Periodically check the balance of all players and reward them if they have reached the reward threshold
    public void checkAllPlayers() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            UUID uuid = player.getUniqueId();
            double balance = plugin.getEconomy().getBalance(player);
            try {
                boolean rewarded = plugin.getDataManager().getRewardStatus(uuid);
                if (balance >= rewardThreshold && !rewarded) {
                    player.getInventory().addItem(new ItemStack(Material.DIAMOND));
                    player.sendMessage("Congratulations! You have earned a diamond for reaching $" + rewardThreshold + ".");
                    plugin.getDataManager().addRewardStatus(uuid, true);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    */
}