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

    /**
     * Executes the GUI command for the player. Opens an inventory with a utility menu and sets metadata to
     * track the opened menu. The inventory contains three items: a diamond button, a lava bucket button,
     * and a sunflower button. The diamond button displays the text "Get Diamond" in aqua color. The lava
     * bucket button displays the text "Clear Inventory" in red color. The sunflower button displays the
     * text "Clear Weather" in yellow color.
     *
     * @param  sender  the command sender
     * @param  command the command that was executed
     * @param  s       the command name
     * @param  strings the command arguments
     * @return         true if the command was executed successfully
     */
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
