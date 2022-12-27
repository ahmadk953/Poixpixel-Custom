package com.poixpixelcustom.Commands

import net.milkbowl.vault.economy.Economy
import net.milkbowl.vault.economy.EconomyResponse
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.logging.Logger

class TestEconomyCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, commandLabel: String, args: Array<String>): Boolean {
        if (sender !is Player) {
            log.info("Only players are supported for this Example Plugin, but you should not do this!!!")
            return true
        }
        if (command.label == "test-economy") {
            // Let's give the player 1.05 currency (note that SOME economic plugins require rounding!)
            sender.sendMessage(String.format("You have %s", econ!!.format(econ.getBalance(sender.getName()))))
            val r: EconomyResponse = econ.depositPlayer(sender, 1.05)
            if (r.transactionSuccess()) {
                sender.sendMessage(String.format("You were given %s and now have %s", econ.format(r.amount), econ.format(r.balance)))
            } else {
                sender.sendMessage(String.format("An error occurred: %s", r.errorMessage))
            }
            return true
        }
        return false
    }

    companion object {
        private val log = Logger.getLogger("Minecraft")
        private val econ: Economy? = null
    }
}