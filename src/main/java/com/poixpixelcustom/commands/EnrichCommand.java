package com.poixpixelcustom.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class EnrichCommand implements CommandExecutor {
    //TODO: Make sure this works
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (sender instanceof Player player && player.hasPermission("poixpixelcustom.enrich")) {
            PlayerInventory inventory = player.getInventory();
            ItemStack itemstack = new ItemStack(Material.DIAMOND, 64);
            inventory.addItem(itemstack);
            player.sendMessage("You got 64 diamonds!!");
        } else {
            sender.sendMessage("You do not have the right permissions to do this! Error: Player doesn't have the permission poixpixelcustom.enrich");
            return false;
        }
        return false;
    }
}