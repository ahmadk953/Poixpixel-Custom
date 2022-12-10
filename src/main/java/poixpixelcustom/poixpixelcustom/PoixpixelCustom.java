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

import poixpixelcustom.poixpixelcustom.Commands.*;
import poixpixelcustom.poixpixelcustom.Event.MovementListener;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.bukkit.Bukkit.getBukkitVersion;
import static org.bukkit.Bukkit.getConsoleSender;

public final class PoixpixelCustom extends JavaPlugin {

    private String pluginPrefix = "";
    private String updateMessage = "";
    private String configBackup = null;
    private boolean updateAvailable = false;
    private String bukkitVersion = "0.0";
    private ArrayList<UUID> creatingCrate = new ArrayList<>();

    @Override
    public void onEnable() {
        Server server = getServer();
        Pattern pattern = Pattern.compile("(^[^\\-]*)");
        Matcher matcher = pattern.matcher(server.getBukkitVersion());
        if (!matcher.find()) {
            getLogger().severe("Could not find Bukkit version... Disabling plugin");
            setEnabled(false);
            return;
        } else {
            getLogger().info("Bukkit version is " + getBukkitVersion());
        }
        bukkitVersion = matcher.group(1);

        if (getConfig().isSet("Bukkit Version")) {
            bukkitVersion = getConfig().getString("Bukkit Version");
        }

        final ConsoleCommandSender console = server.getConsoleSender();
        getConfig().options().copyDefaults(true);
        saveConfig();

        this.getCommand("enrich").setExecutor(new EnrichCommand());
        getLogger().info("Added the Enrich command.");

    }

    @Override
    public void onDisable() {
        //Plugin shutdown logic
        saveConfig();
        getLogger().info("Plugin was disabled successfully!");
    }
}
