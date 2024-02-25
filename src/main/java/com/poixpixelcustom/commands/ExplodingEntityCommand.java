package com.poixpixelcustom.commands;

import com.poixpixelcustom.constants.Keys;
import com.poixpixelcustom.utils.ConfigHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.*;
import org.bukkit.persistence.PersistentDataType;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ExplodingEntityCommand implements CommandExecutor, TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players are allowed to run this command!");
            return true;
        }

        if (args.length > 1) {

            if (args[0].equalsIgnoreCase("set")) {
                EntityType type;

                try {
                    type = EntityType.valueOf(args[1].toUpperCase());
                } catch (IllegalArgumentException e) {
                    sender.sendMessage("Invalid entity type: " + args[1]);
                    return true;
                }

                if (!type.isSpawnable() || !type.isAlive()) {
                    sender.sendMessage("You can only use living and spawnable entities!");
                    return true;
                }

                ConfigHandler.getInstance().setExplodingType(type);
                sender.sendMessage("Exploding type set to " + type);
                return true;
            }
            return false;
        }

        Player player = (Player) sender;
        LivingEntity entity = (LivingEntity) player.getWorld().spawnEntity(player.getLocation(), ConfigHandler.getInstance().getExplodingType());

        if (args.length == 1 && args[0].equalsIgnoreCase("baby")) {
            if (entity instanceof Ageable)
                ((Ageable) entity).setBaby();
            else {
                sender.sendMessage("This entity cannot be a baby!");

                return true;
            }
        }

        entity.getPersistentDataContainer().set(Keys.CUSTOM_ENTITY, PersistentDataType.BOOLEAN, true);
        entity.setCustomName(ChatColor.RED + "Click Me");
        entity.setCustomNameVisible(true);

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1)
            return Arrays.asList("baby", "set");

        if (args.length == 2) {
            String name = args[1].toUpperCase();

            return Arrays.stream(EntityType.values())
                    .filter(type -> type.isSpawnable() && type.isAlive() && type.name().startsWith(name))
                    .map(Enum::name)
                    .collect(Collectors.toList());
        }

        return new ArrayList<>(); // null = all player names
    }
}
