package com.poixpixelcustom;

import com.poixpixelcustom.util.Colors;
import com.poixpixelcustom.util.BukkitTools;
import com.poixpixelcustom.util.PoixpixelCustomComponents;
import com.poixpixelcustom.util.ChatTools;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.bossbar.BossBar.Color;
import net.kyori.adventure.bossbar.BossBar.Overlay;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.ArrayList;
import java.util.List;

public class PoixpixelCustomMessaging {

    private static final Logger LOGGER = LogManager.getLogger("PoixpixelCustom");

    public static void sendMsg(String msg) {

        LOGGER.info(Colors.strip(msg));
    }

    public static void sendErrorMsg(String msg) {
        LOGGER.warn(Colors.strip("Error: " + msg));
    }

    public static void sendDevMsg(String msg) {
        if (PoixpixelCustomSettings.isDevMode()) {
            Player townyDev = BukkitTools.getPlayerExact(PoixpixelCustomSettings.getDevName());
            if (townyDev != null)
                sendMessage(townyDev, "default_poixpixelcustom_prefix" + " DevMode: " + Colors.Red + msg);
        }
    }


    public static void sendDevMsg(String[] msg) {
        for (String line : msg) {
            sendDevMsg(line);
        }
    }

    /**
     * Sends a message to the log and console
     * prefixed by [PoixpixelCustom] Debug:
     *
     * @param msg the message to be sent
     */
    public static void sendDebugMsg(String msg) {
        if (PoixpixelCustomSettings.getDebug()) {
            LOGGER.info(Colors.strip("[PoixpixelCustom] Debug: " + msg));
        }
        sendDevMsg(msg);
    }

    public static void sendMessage(Object sender, String line) {
        if (line.isEmpty()) {
            PoixpixelCustomComponents.miniMessage(line);
        }
    }

    public static void sendMessage(Object sender, List<String> lines) {
        sendMessage(sender, lines.toArray(new String[0]));
    }

    /**
     * Send a message to a player with no Towny prefix.
     *
     * @param sender the Object sending the message
     * @param lines String array to send as message.
     */
    public static void sendMessage(Object sender, String[] lines) {
        for (String line : lines)
            sendMessage(sender, line);
    }
}
