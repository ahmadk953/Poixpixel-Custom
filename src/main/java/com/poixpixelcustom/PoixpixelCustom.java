package com.poixpixelcustom;

import com.earth2me.essentials.Essentials;
import com.poixpixelcustom.Exceptions.Initialization.PoixpixelCustomInitException;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;

import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
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

import com.poixpixelcustom.Commands.*;
import com.poixpixelcustom.util.*;
import com.poixpixelcustom.PoixpixelCustomSettings;
import com.poixpixelcustom.Config.*;
import com.poixpixelcustom.Confirmations.*;
import com.poixpixelcustom.Event.*;
import com.poixpixelcustom.Exceptions.*;
import com.poixpixelcustom.Object.*;
import com.poixpixelcustom.PoixpixelCustomMessaging;

public final class PoixpixelCustom extends JavaPlugin {

    private static final Version OLDEST_MC_VER_SUPPORTED = Version.fromString("1.19");
    private static final Version CUR_BUKKIT_VER = Version.fromString(Bukkit.getBukkitVersion());
    private static PoixpixelCustom plugin;
    private static BukkitAudiences adventure;
    private final String version = this.getDescription().getVersion();
    private final List<PoixpixelCustomInitException.PoixpixelCustomError> errors = new ArrayList<>();
    private final Essentials essentials = null;
    private boolean citizens2 = false;
    private int pluginsFound = 0;

    public PoixpixelCustom() {
        plugin = this;
    }

    /**
     * @return the PoixpixelCustom instance
     */
    @NotNull
    public static PoixpixelCustom getPlugin() {
        if (plugin == null)
            throw new IllegalStateException("Attempted to use getPlugin() while the plugin is null, are you shading PoixpixelCustom?");

        return plugin;
    }

    @Override
    public void onEnable() {

        BukkitTools.initialize(this);

        try {
            // Load the foundation of PoixpixelCustom, containing config, locales, database.
            loadFoundation(false);

            // Setup bukkit command interfaces
            registerSpecialCommands();
            registerCommands();

            // Add custom metrics charts.
            addMetricsCharts();
        } catch (PoixpixelCustomInitException pcie) {
            addError(pcie.getError());
            getLogger().log(Level.SEVERE, pcie.getMessage(), pcie);

        }

        adventure = BukkitAudiences.create(this);

        checkPlugins();


    }

    @Override
    public void onDisable() {

        if (!isError()){
            getLogger().info("Plugin was disabled successfully!");
        } else {
            getLogger().warning("Plugin was disabled with 1 or more errors." + getErrors());
        }

    }

    private void registerSpecialCommands() {
        List<Command> commands = new ArrayList<>(4);
        try {
            final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");

            bukkitCommandMap.setAccessible(true);
            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());

            commandMap.registerAll("poixpixelcustom", commands);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new PoixpixelCustomInitException("An issue has occured while registering custom commands.", PoixpixelCustomInitException.PoixpixelCustomError.OTHER, e);
        }
    }

    private void registerCommands() {
        this.getCommand("enrich").setExecutor(new EnrichCommand());
        getLogger().info("Added the Enrich command.");

    }

    private void addMetricsCharts() {
        /*
         * Register bStats Metrics
         */
        Metrics metrics = new Metrics(this, 2244);

        metrics.addCustomChart(new SimplePie("server_type", () -> {
            if (Bukkit.getServer().getName().equalsIgnoreCase("paper"))
                return "Paper";
            else if (Bukkit.getServer().getName().equalsIgnoreCase("craftbukkit")) {
                if (isSpigotOrDerivative())
                    return "Spigot";
                else
                    return "CraftBukkit";
            }
            return "Unknown";
        }));

    }

    public void loadFoundation(boolean reload) {

        loadConfig(reload);
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

        if (pluginsFound > 0) {
            getLogger().info("Add-on plugins found!");
            getLogger().info(pluginsFound + " add-on plugins found");
        } else {
            getLogger().warning("No add-on plugins were found.");
            getLogger().warning("The plugin might run with limited functionality.");
        }

    }

    private void loadConfig(boolean reload) {
        PoixpixelCustomSettings.loadConfig(getDataFolder().toPath().resolve("settings").resolve("config.yml"), getVersion());
        if (reload) {
            PoixpixelCustomMessaging.sendMsg("msg_reloaded_config");
        }
    }

    public boolean isEssentials() {

        return (PoixpixelCustomSettings.isUsingEssentials() && (this.essentials != null));
    }

    public Essentials getEssentials() throws PoixpixelCustomException {

        if (essentials == null)
            throw new PoixpixelCustomException("Essentials is not installed, or not enabled!");
        else
            return essentials;
    }

    public World getServerWorld(String name) throws NotRegisteredException {
        World world = BukkitTools.getWorld(name);

        if (world == null)
            throw new NotRegisteredException(String.format("A world called '$%s' has not been registered.", name));

        return world;
    }

    private static boolean isSpigotOrDerivative() {
        try {
            Class.forName("org.bukkit.entity.Player$Spigot");
            return true;
        } catch (ClassNotFoundException tr) {
            return false;
        }

    }

    public boolean isCitizens2() {
        return citizens2;
    }

    public void setCitizens2(boolean b) {
        citizens2 = b;
    }

    public String getVersion() {
        return version;
    }

    public void addError(@NotNull PoixpixelCustomInitException.PoixpixelCustomError error) {
        errors.add(error);
    }

    public boolean isError() {
        return !errors.isEmpty();
    }

    private boolean isError(@NotNull PoixpixelCustomInitException.PoixpixelCustomError error) {
        return errors.contains(error);
    }

    private void removeError(@NotNull PoixpixelCustomInitException.PoixpixelCustomError error) {
        errors.remove(error);
    }

    @NotNull
    public List<PoixpixelCustomInitException.PoixpixelCustomError> getErrors() {
        return errors;
    }

}
