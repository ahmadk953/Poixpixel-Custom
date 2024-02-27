package com.poixpixelcustom.commands;

import com.poixpixelcustom.PoixpixelCustom;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;

import org.jetbrains.annotations.NotNull;

public class GuiCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command!");

            return true;
        }

        Player player = (Player) sender;

        Inventory inventory = Bukkit.createInventory(player, InventoryType.CHEST, Component.text("Utility Menu").color(NamedTextColor.DARK_PURPLE));

        ItemStack getDiamondButton = new ItemStack(Material.DIAMOND);
        ItemMeta diamondMeta = getDiamondButton.getItemMeta();
        diamondMeta.displayName(Component.text("Get Diamond").color(NamedTextColor.AQUA));
        getDiamondButton.setItemMeta(diamondMeta);

        ItemStack clearInventoryButton = new ItemStack(Material.LAVA_BUCKET);
        ItemMeta clearInventoryMeta = clearInventoryButton.getItemMeta();
        clearInventoryMeta.displayName(Component.text("Clear Inventory").color(NamedTextColor.RED));
        clearInventoryButton.setItemMeta(clearInventoryMeta);

        ItemStack clearWeatherButton = new ItemStack(Material.SUNFLOWER);
        ItemMeta clearWeatherMeta = clearWeatherButton.getItemMeta();
        clearWeatherMeta.displayName(Component.text("Clear Weather").color(NamedTextColor.YELLOW));
        clearWeatherButton.setItemMeta(clearWeatherMeta);

        inventory.setItem(11, getDiamondButton);
        inventory.setItem(13, clearInventoryButton);
        inventory.setItem(15, clearWeatherButton);

        player.openInventory(inventory);
        player.setMetadata("OpenedMenu", new FixedMetadataValue(PoixpixelCustom.getInstance(), "Utility Menu"));

        return true;
    }
}
