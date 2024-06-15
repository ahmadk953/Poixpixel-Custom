package com.poixpixelcustom.commands;

import com.poixpixelcustom.constants.Keys;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class CustomItemCommand implements CommandExecutor {

    /**
     * Executes the command for the player. If the sender is not a player, sends a message and returns true.
     * Otherwise, creates a custom bucket item with a display name and lore, adds enchantments and metadata,
     * and adds the item to the player's inventory.
     *
     * @param  sender      the command sender
     * @param  command     the command that was executed
     * @param  s           the command name
     * @param  strings     the command arguments
     * @return             true if the command was executed successfully
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command!");

            return true;
        }

        Player player = (Player) sender;
        ItemStack customBucket = new ItemStack(Material.BUCKET);
        ItemMeta meta = customBucket.getItemMeta();

        meta.displayName(Component.text("Secret Bucket").color(NamedTextColor.GOLD).decorate(TextDecoration.BOLD));
        meta.lore(Arrays.asList(
                Component.text(""),
                Component.text("Right click me on a entity!").color(NamedTextColor.GRAY),
                Component.text(""),
                Component.text("Just don't left click me on an entity...").color(NamedTextColor.RED).decorate(TextDecoration.ITALIC)
        ));

        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addEnchant(Enchantment.KNOCKBACK, 255, true);
        meta.getPersistentDataContainer().set(Keys.CUSTOM_BUCKET, PersistentDataType.BOOLEAN, true);

        customBucket.setItemMeta(meta);

        player.getInventory().addItem(customBucket);

        return true;
    }
}
