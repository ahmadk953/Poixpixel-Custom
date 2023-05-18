package com.poixpixelcustom;

import com.poixpixelcustom.commands.EnrichCommand;
import com.poixpixelcustom.listeners.BreakBlockListener;
import com.poixpixelcustom.listeners.RewardListener;
import com.poixpixelcustom.util.BukkitTools;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.milkbowl.vault.economy.Economy;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public class PoixpixelCustom extends JavaPlugin {

    private static final Logger log = Logger.getLogger("Minecraft");
    private static PoixpixelCustom plugin;
    public static Economy economy;
    private static BukkitAudiences adventure;
    private int pluginsFound = 0;
    private final PluginManager pluginManager = getServer().getPluginManager();

    public static void setPlugin(PoixpixelCustom plugin) {
        PoixpixelCustom.plugin = plugin;
    }

    public void onEnable() {

        BukkitTools.initialize(this);
        setPlugin(this);

        try {
            this.loadFoundation();
            this.registerSpecialCommands();
            this.registerCommands();
            this.addMetricsCharts();
        } catch (Exception e) {
            log.severe("Error loading plugin. Plugin was loaded with 1 or more errors. Disabling plugin.");
            log.severe("Errors:" + e);
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }

        adventure = BukkitAudiences.create(this);
        this.checkPlugins();
    }

    public void onDisable() {

        adventure = null;
        log.info("Goodbye!");

    }

    private void registerSpecialCommands() throws NoSuchFieldException {
        final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");

        bukkitCommandMap.setAccessible(true);
    }


    private void registerCommands() {
        Objects.requireNonNull(this.getCommand("enrich")).setExecutor(new EnrichCommand());
        getLogger().info("Added the Enrich command.");
    }

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new BreakBlockListener(), this);
        pluginManager.registerEvents(new RewardListener((PoixpixelCustom) economy), this);
    }

    public void loadFoundation() {
        if (setupEconomy() == null) {
            log.warning(String.format("[%s] - No Vault dependency found! Plugin might run with limited functionality.", getDescription().getName()));
            return;
        }
        registerListeners();
    }

    private Economy setupEconomy() {
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            getLogger().severe("Unable to find an economy plugin!");
            return economy = null;
        }
        economy = rsp.getProvider();
        return economy;
    }

    public static Economy getEconomy() {
        return economy;
    }


    private void checkPlugins() {

        plugin.getLogger().info("Searching for third-party plugins...");
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

        if (pluginsFound > 0) {
            getLogger().info("Add-on plugins found!");
            getLogger().info(pluginsFound + " add-on plugins found");
        } else {
            getLogger().warning("No add-on plugins were found.");
            getLogger().warning("The plugin might run with limited functionality.");
        }

    }

    private void addMetricsCharts() {
        /*
         * Register bStats Metrics
         */
        int pluginId = 17328;
        Metrics metrics = new Metrics(this, pluginId);

        metrics.addCustomChart(new SimplePie("server_type", () -> {
            if (Bukkit.getServer().getName().equalsIgnoreCase("paper"))
                return "Paper";
            else
                return "Other";
        }));

    }
}
