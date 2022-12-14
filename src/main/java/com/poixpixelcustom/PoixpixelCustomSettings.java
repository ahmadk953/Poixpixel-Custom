package com.poixpixelcustom;

import com.github.bsideup.jabel.Desugar;
import com.poixpixelcustom.Config.ConfigNodes;
import com.poixpixelcustom.Config.CommentedConfiguration;
import com.poixpixelcustom.Exceptions.Initialization.PoixpixelCustomInitException;
import com.poixpixelcustom.util.FileMgmt;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static javax.swing.UIManager.*;

public class PoixpixelCustomSettings {

    private static CommentedConfiguration config;
    private static CommentedConfiguration newConfig;
    private static int uuidCount;
    private static boolean areLevelTypeLimitsConfigured;

    private static final EnumSet<Material> itemUseMaterials = EnumSet.noneOf(Material.class);
    private static final EnumSet<Material> switchUseMaterials = EnumSet.noneOf(Material.class);
    private static final List<Class<?>> protectedMobs = new ArrayList<>();

    private static final Map<NamespacedKey, Consumer<CommentedConfiguration>> CONFIG_RELOAD_LISTENERS = new HashMap<>();

    public static CommentedConfiguration getConfig() {
        return config;
    }


    public static void loadConfig(Path configPath, String version) {
        if (!FileMgmt.checkOrCreateFile(configPath.toString())) {
            throw new PoixpixelCustomInitException("Failed to touch '" + configPath + "'.", PoixpixelCustomInitException.PoixpixelCustomError.MAIN_CONFIG);
        }

        // read the config.yml into memory
        config = new CommentedConfiguration(configPath.toFile());
        if (!config.load()) {
            throw new PoixpixelCustomInitException("Failed to load PoixpixelCustoms's config.yml.", PoixpixelCustomInitException.PoixpixelCustomError.MAIN_CONFIG);
        }

        setDefaults(version, configPath);

        config.save();

        // Always run reload consumers after everything else is reloaded.
        CONFIG_RELOAD_LISTENERS.values().forEach(consumer -> consumer.accept(config));
    }

    public static String getConfirmCommand(){
        return getString(ConfigNodes.INVITE_SYSTEM_CONFIRM_COMMAND);
    }

    public static String getCancelCommand(){
        return getString(ConfigNodes.INVITE_SYSTEM_CANCEL_COMMAND);
    }

    public static int getConfirmationTimeoutSeconds() {
        return getInt(ConfigNodes.INVITE_SYSTEM_CONFIRMATION_TIMEOUT);
    }

    public static void addComment(String root, String... comments) {

        newConfig.addComment(root.toLowerCase(Locale.ROOT), comments);
    }

    public static boolean isUsingEssentials() {

        return getBoolean(ConfigNodes.PLUGIN_USING_ESSENTIALS);
    }

    private static void setDefaults(String version, Path configPath) {

        newConfig = new CommentedConfiguration(configPath.toFile());
        newConfig.load();

        for (ConfigNodes root : ConfigNodes.values()) {
            if (root.getComments().length > 0)
                addComment(root.getRoot(), root.getComments());
        }

        config = newConfig;
        newConfig = null;
    }
}
