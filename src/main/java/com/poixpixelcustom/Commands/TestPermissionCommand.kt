package com.poixpixelcustom.Commands

import net.milkbowl.vault.permission.Permission
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.logging.Logger

class TestPermissionCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, commandLabel: String, args: Array<String>): Boolean {
        if (sender !is Player) {
            log.info("Only players are supported for this Example Plugin, but you should not do this!!!")
            return true
        }
        return if (command.label == "test-permission") {
            // Let's test if user has the node "poixpixelcustom.awesome" to determine if they are awesome or just suck
            if (perms!!.has(sender, "poixpixelcustom.awesome")) {
                sender.sendMessage("You are awesome!")
            } else {
                sender.sendMessage("You suck!")
            }
            true
        } else {
            false
        }
    }

    companion object {
        private val log = Logger.getLogger("Minecraft")
        private val perms: Permission? = null
    }
}