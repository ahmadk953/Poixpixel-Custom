package com.poixpixelcustom.utils;

import com.poixpixelcustom.PoixpixelCustom;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;

import java.io.File;

public class ConfigHandler {

    private final static ConfigHandler instance = new ConfigHandler();

    private File file;
    private YamlConfiguration config;

    private EntityType explodingType;
    private float explosionPower;

    private ConfigHandler() {};

    public void load() {
        file = new File(PoixpixelCustom.getInstance().getDataFolder(), "config.yml");

        if (!file.exists()) PoixpixelCustom.getInstance().saveResource("config.yml", false);

        config = new YamlConfiguration();
        config.options().parseComments(true);

        try {
            config.load(file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        explodingType = EntityType.valueOf(config.getString("Explosion.Entity_Type"));
        explosionPower = (float) config.getDouble("Explosion.Explosion_Power");
    }

    public void save() {
        try {
            config.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void set(String path, Object value) {
        config.set(path, value);

        save();
    }

    public EntityType getExplodingType() {
        return explodingType;
    }

    public float getExplosionPower() {
        // Temporarily print explosion power for debugging purposes
        // FIXME: Figure out why this does not work
        System.out.println(explosionPower);
        if (explosionPower <= 0F) return 2.5F;
        return explosionPower;
    }

    public void setExplodingType(EntityType explodingType) {
        this.explodingType = explodingType;

        set("Explosion.Entity_Type", explodingType.name());
    }

    public static ConfigHandler getInstance() {
        return instance;
    }

    public YamlConfiguration getConfig() {
        return config;
    }
}
