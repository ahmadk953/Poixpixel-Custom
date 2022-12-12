package poixpixelcustom.poixpixelcustom;

import com.google.common.io.ByteStreams;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import poixpixelcustom.poixpixelcustom.Commands.*;
import poixpixelcustom.poixpixelcustom.Event.MovementListener;
import poixpixelcustom.poixpixelcustom.util.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.bukkit.Bukkit.getBukkitVersion;
import static org.bukkit.Bukkit.getConsoleSender;

public final class PoixpixelCustom extends JavaPlugin {

    private static final Version OLDEST_MC_VER_SUPPORTED = Version.fromString("1.19");
    private static final Version CUR_BUKKIT_VER = Version.fromString(Bukkit.getBukkitVersion());
    private final String version = this.getDescription().getVersion();

    private static PoixpixelCustom plugin;

    private int pluginsFound = 0;

    public PoixpixelCustom() {
        plugin = this;
    }

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveConfig();

        this.getCommand("enrich").setExecutor(new EnrichCommand());
        getLogger().info("Added the Enrich command.");

        checkPlugins();

        if (pluginsFound > 0) {
            getLogger().info("Add-on plugins found!");
            getLogger().info(pluginsFound + " add-on plugins found");
        } else {
            getLogger().warning("No add-on plugins were found.");
            getLogger().warning("The plugin might run with limited functionality.");
        }
    }

    @Override
    public void onDisable() {
        //Plugin shutdown logic
        saveConfig();
        getLogger().info("Plugin was disabled successfully!");
    }

    private void checkPlugins() {

        plugin.getLogger().info("Searching for third-party plugins...");
        String ecowarn = "";
        List<String> addons = new ArrayList<>();
        Plugin test;

        /*
         * Check add-ons and third-party plugins we use.
         */

        test = getServer().getPluginManager().getPlugin("Vault");
        if (test != null) {
            addons.add(String.format("%s v%s", "Vault", test.getDescription().getVersion()));
            pluginsFound = pluginsFound + 1;
        }

        test = getServer().getPluginManager().getPlugin("Essentials");
        if (test != null) {
            addons.add(String.format("%s v%s", "Essentials", test.getDescription().getVersion()));
            pluginsFound = pluginsFound + 1;
        }

    }
}
