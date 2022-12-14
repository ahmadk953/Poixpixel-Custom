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
}
