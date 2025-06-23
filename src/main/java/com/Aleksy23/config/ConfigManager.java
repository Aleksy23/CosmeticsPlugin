package com.Aleksy23.config;

import com.Aleksy23.CosmeticsPlugin;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {
    private final CosmeticsPlugin plugin;
    private FileConfiguration config;
    
    public ConfigManager(CosmeticsPlugin plugin) {
        this.plugin = plugin;
        saveDefaultConfig();
        loadConfig();
    }
    
    private void saveDefaultConfig() {
        plugin.saveDefaultConfig();
    }
    
    private void loadConfig() {
        plugin.reloadConfig();
        config = plugin.getConfig();
        
        // Ustaw domyślne wartości
        config.addDefault("particles.enabled", true);
        config.addDefault("particles.max-distance", 50);
        config.addDefault("pets.max-health", 40.0);
        config.addDefault("pets.follow-distance", 6.0);
        config.addDefault("pets.teleport-distance", 15.0);
        
        config.options().copyDefaults(true);
        plugin.saveConfig();
    }
    
    public boolean isParticlesEnabled() {
        return config.getBoolean("particles.enabled");
    }
    
    public int getMaxParticleDistance() {
        return config.getInt("particles.max-distance");
    }
    
    public double getPetMaxHealth() {
        return config.getDouble("pets.max-health");
    }
}