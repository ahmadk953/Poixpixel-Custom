package com.poixpixelcustom.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.*;
import org.bukkit.entity.Display.Billboard;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Transformation;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DisplayEntityCommand implements CommandExecutor, TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command!");

            return true;
        }

        if (args.length != 1) {
            return false;
        }

        Player player = (Player) sender;

        try {
            // 1: Display items
            if (args[0].equalsIgnoreCase("item")) {
                ItemDisplay item = player.getWorld().spawn(player.getLocation(), ItemDisplay.class);
                item.setItemStack(new ItemStack(Material.DIAMOND));

                Transformation transformation = item.getTransformation();

                transformation.getScale().set(2D);

                //transformation.getLeftRotation().x = 1; // 1 to -1, forward/backward lay
                //transformation.getLeftRotation().y = 0.5F; // 1 to -1, horizontal rotation
                //transformation.getLeftRotation().z = -1F; // 1 to -1, right/left tilt

                item.setTransformation(transformation);

                //item.setViewRange(0.1F); /* 0.1 = 16 blocks */
                //item.setShadowRadius(0.3F); // 1 = 1 block
                //item.setShadowRadius(1F);
                //item.setShadowStrength(5F); // >= 5F = "black hole"

                //item.setDisplayWidth(50F);
                //item.setDisplayHeight(50F);

                //item.setBillboard(Billboard.CENTER); // auto-rotate

                //item.setGlowColorOverride(Color.RED); // only works for scoreboard

                //item.setBrightness(new Brightness(15, 15)); // 0-15, will override auto brightness
            }

            // 2: Blocks
            if (args[0].equalsIgnoreCase("block")) {
                BlockDisplay block = player.getWorld().spawn(player.getLocation(), BlockDisplay.class);
                block.setBlock(Bukkit.createBlockData(Material.DIAMOND_BLOCK));

                Transformation transformation = block.getTransformation();
                transformation.getScale().set(2D);

                block.setTransformation(transformation);
            }

            // 3: Text
            if (args[0].equalsIgnoreCase("text")) {
                TextDisplay text = player.getWorld().spawn(player.getLocation(), TextDisplay.class);
                text.text(Component.text("Hello World! \n This is a test!").color(NamedTextColor.GREEN).decorate(TextDecoration.BOLD));

                // text.setBackgroundColor(Color.RED);
                text.setLineWidth(50);
                text.setBillboard(Billboard.CENTER);
                text.setTextOpacity(Byte.MAX_VALUE); // transparent
            }

        } catch (Throwable t) {
            sender.sendMessage("Error: " + t.getMessage());

            t.printStackTrace();
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length == 1) {
            return List.of("item", "block", "text");
        }

        return new ArrayList<>();
    }
}
