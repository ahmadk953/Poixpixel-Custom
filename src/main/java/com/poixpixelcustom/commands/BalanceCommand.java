package com.poixpixelcustom.commands;

import com.poixpixelcustom.PoixpixelCustom;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public class BalanceCommand implements CommandExecutor {

    private static final Logger log = Logger.getLogger("Minecraft");

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            Economy economy = PoixpixelCustom.getEconomy();
            if (args.length == 0) {
                //Get a balance of a player
                player.sendMessage(ChatColor.GREEN + "Current Balance: " + ChatColor.GOLD + economy.format(economy.getBalance(player)));
            } else if (args.length == 2 && args[0].equalsIgnoreCase("withdraw")) {
                double withdraw_amount = Double.parseDouble(args[1]);
                //if the method returns an economyresponse, set the method equal to a reference for one
                //so that you can use it for information on the transaction
                EconomyResponse response = economy.withdrawPlayer(player, withdraw_amount);
                if (response.transactionSuccess()) {
                    player.sendMessage("You have successfully removed: " + economy.format(response.amount));
                    player.sendMessage("Your new balance is: " + economy.format(response.balance));
                } else {
                    player.sendMessage("Failed to withdraw money from your balance.");
                    player.sendMessage(response.errorMessage);
                }
            }
        }
        else {
            log.warning("You must be a player in order to use this command!");
        }
        return true;
    }
}

