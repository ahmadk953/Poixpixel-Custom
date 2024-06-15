package com.poixpixelcustom.utils;

import com.poixpixelcustom.PoixpixelCustom;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;

import java.io.File;

import static com.poixpixelcustom.utils.ExceptionHandeler.handleException;

public class ConfigHandler {

    private final static ConfigHandler instance = new ConfigHandler();

    private File file;
    private YamlConfiguration config;

    private EntityType explodingType;
    private float explosionPower;

    private ConfigHandler() {};

    /**
     * Loads the configuration from the file, handling exceptions if they occur.
     */
    public void load() {
        file = new File(PoixpixelCustom.getInstance().getDataFolder(), "config.yml");

        if (!file.exists()) PoixpixelCustom.getInstance().saveResource("config.yml", false);

        config = new YamlConfiguration();
        config.options().parseComments(true);

        try {
            config.load(file);
        } catch (Exception e) {
            handleException(e, false);
        }

        explodingType = EntityType.valueOf(config.getString("Explosion.Entity_Type"));
        explosionPower = (float) config.getDouble("Explosion.Explosion_Power");
    }

    /**
     * Saves the configuration to a file.
     *
     */
    public void save() {
        try {
            config.save(file);
        } catch (Exception e) {
            handleException(e, false);
        }
    }

    /**
     * Sets the value at the specified path in the configuration and saves the configuration.
     *
     * @param  path   the path in the configuration where the value should be set
     * @param  value  the value to be set at the specified path
     */
    public void set(String path, Object value) {
        config.set(path, value);

        save();
    }

    /**
     * Returns the current exploding type.
     *
     * @return the current exploding type
     */
    public EntityType getExplodingType() {
        return explodingType;
    }

    /**
     * Returns the current explosion power.
     *
     * @return the current explosion power
     */
    public float getExplosionPower() {
        // Temporarily print explosion power for debugging purposes
        // FIXME: Figure out why this does not work
        System.out.println(explosionPower);
        if (explosionPower <= 0F) return 2.5F;
        return explosionPower;
    }

    /**
     * Sets the exploding type to the given EntityType and updates the configuration file accordingly.
     *
     * @param  explodingType  the EntityType to set as the exploding type
     */
    public void setExplodingType(EntityType explodingType) {
        this.explodingType = explodingType;

        set("Explosion.Entity_Type", explodingType.name());
    }

    /**
     * Returns the singleton instance of the ConfigHandler.
     *
     * @return the singleton instance of ConfigHandler
     */
    public static ConfigHandler getInstance() {
        return instance;
    }

    /**
     * Returns the YamlConfiguration object stored in the config field.
     *
     * @return the YamlConfiguration object
     */
    public YamlConfiguration getConfig() {
        return config;
    }
}
