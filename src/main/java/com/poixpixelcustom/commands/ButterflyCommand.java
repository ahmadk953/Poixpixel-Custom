package com.poixpixelcustom.commands;

import com.poixpixelcustom.tasks.ButterflyTask;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ButterflyCommand implements CommandExecutor {

    /**
     * Executes the command when a player runs the command. If the sender is not a player,
     * sends a message to the sender and returns true. Otherwise, checks if the player has
     * butterfly wings. If they do, removes the wings and sends a message to the player. If
     * they don't, adds the wings and sends a message to the player. Returns true.
     *
     * @param  sender  the command sender
     * @param  command the command being executed
     * @param  s       the label of the command
     * @param  strings the arguments passed to the command
     * @return         true if the command was executed successfully, false otherwise
     */
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
            player.sendMessage(Component.text("You no longer have butterfly wings!"));

        } else {
            instance.addPlayer(player.getUniqueId());
            player.sendMessage(Component.text("You now have butterfly wings!"));
        }

        return true;
    }
}