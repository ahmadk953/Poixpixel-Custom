package com.poixpixelcustom.commands;

import net.milkbowl.vault.permission.Permission;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.logging.Logger;

public class TestPermissionCommand implements CommandExecutor {

    private static final Logger log = Logger.getLogger("Minecraft");
    private static final Permission perms = null;

    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        if (!(sender instanceof Player player)) {
            log.info("Only players are supported for this Example Plugin, but you should not do this!!!");
            return true;
        }

        if (command.getLabel().equals("test-permission")) {
            // Let's test if user has the node "poixpixelcustom.awesome" to determine if they are awesome or just suck
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
}
