package com.poixpixelcustom.commands;

import com.poixpixelcustom.constants.Keys;
import com.poixpixelcustom.utils.ConfigHandler;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ExplodingEntityCommand implements CommandExecutor, TabExecutor {

    /**
     * Executes the exploding entity command.
     *
     * @param sender the command sender
     * @param command the command being executed
     * @param label the command label
     * @param args the command arguments
     * @return true if the command was executed successfully, false otherwise
     */
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
        entity.customName(Component.text("Click Me").color(NamedTextColor.RED));
        entity.setCustomNameVisible(true);

        return true;
    }

    /**
     * Generates a list of tab completion suggestions for the ExplodingEntityCommand.
     *
     * @param  sender  the command sender
     * @param  command the command being executed
     * @param  label   the command label
     * @param  args    the command arguments
     * @return          a list of tab completion suggestions or an empty list if none
     */
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
