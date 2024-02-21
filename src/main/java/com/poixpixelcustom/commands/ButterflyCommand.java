package com.poixpixelcustom.commands;

import com.poixpixelcustom.tasks.ButterflyTask;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ButterflyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command!");

            return true;
        }

        Player player = (Player) sender;
        ButterflyTask instance = ButterflyTask.getInstance();

        if (instance.hasPlayer(player.getUniqueId())) {
            instance.removePlayer(player.getUniqueId());
            player.sendMessage("You no longer have butterfly wings!");

        } else {
            instance.addPlayer(player.getUniqueId());
            player.sendMessage("You now have butterfly wings!");
        }

        return true;
    }
}