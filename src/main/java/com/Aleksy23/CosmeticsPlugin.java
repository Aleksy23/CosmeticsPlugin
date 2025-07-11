package com.Aleksy23;

import com.Aleksy23.commands.CosmeticsCommand;
import com.Aleksy23.commands.PetNameCommand;
import com.Aleksy23.config.ConfigManager;
import com.Aleksy23.database.DatabaseManager;
import com.Aleksy23.listeners.MenuClickListener;
import com.Aleksy23.listeners.PetDamageListener;
import com.Aleksy23.listeners.PetRideListener;
import com.Aleksy23.listeners.PetJumpListener;
import com.Aleksy23.listeners.PetDismountListener;
import com.Aleksy23.listeners.PlayerJoinListener;
import com.Aleksy23.manager.PetDamageManager;
import com.Aleksy23.tasks.ParticleTask;
import com.Aleksy23.tasks.MiniPetFollowTask;
import com.Aleksy23.tasks.PetFollowTask;
import com.Aleksy23.tasks.WingsFollowTask;
import org.bukkit.Effect;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class CosmeticsPlugin extends JavaPlugin {

    public static HashMap<UUID, Effect> activeParticles = new HashMap<>();
    public static HashMap<UUID, ArmorStand> activeMiniPets = new HashMap<>();
    public static HashMap<UUID, LivingEntity> activePets = new HashMap<>();
    public static HashMap<UUID, Effect> activeWings = new HashMap<>();

    private static CosmeticsPlugin instance;
    private PetDamageManager petDamageManager;
    private DatabaseManager databaseManager;
    private ConfigManager configManager;

    @Override
    public void onEnable() {
        getLogger().info("CosmeticsPlugin zostal wlaczony!");
        instance = this;

        getCommand("cosmetics").setExecutor(new CosmeticsCommand());
        getCommand("petname").setExecutor(new PetNameCommand());
        
        getServer().getPluginManager().registerEvents(new MenuClickListener(), this);
        getServer().getPluginManager().registerEvents(new PetDamageListener(), this);
        getServer().getPluginManager().registerEvents(new PetRideListener(), this);
        getServer().getPluginManager().registerEvents(new PetJumpListener(), this);
        getServer().getPluginManager().registerEvents(new PetDismountListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);

        new ParticleTask(this).runTaskTimer(this, 0L, 5L);
        new MiniPetFollowTask(this).runTaskTimer(this, 0L, 1L);
        new PetFollowTask(this).runTaskTimer(this, 0L, 10L);
        new WingsFollowTask(this).runTaskTimer(this, 0L, 8L);
        petDamageManager = new PetDamageManager();

        // Inicjalizuj config manager
        configManager = new ConfigManager(this);

        // Inicjalizuj bazę danych
        try {
            databaseManager = new DatabaseManager(this);
        } catch (Exception e) {
            getLogger().warning("Nie można zainicjalizować bazy danych: " + e.getMessage());
            getLogger().warning("Plugin będzie działał bez zapisywania danych!");
        }
    }
    
    public PetDamageManager getPetDamageManager() {
        return petDamageManager;
    }
    
    @Override
    public void onDisable() {
        getLogger().info("CosmeticsPlugin zostal wylaczony!");

        // Usuń wszystkie aktywne mini pety
        for (ArmorStand miniPet : activeMiniPets.values()) {
            if (miniPet != null && !miniPet.isDead()) {
                miniPet.remove();
            }
        }
        activeMiniPets.clear();

        // Usuń wszystkie aktywne "prawdziwe" pety
        for (LivingEntity pet : activePets.values()) {
            if (pet != null && !pet.isDead()) {
                pet.remove();
            }
        }
        activePets.clear();

        // Usuń wszystkie aktywne skrzydła
        activeWings.clear();

        // Zamknij połączenie z bazą danych
        if (databaseManager != null) {
            databaseManager.closeConnection();
        }
    }
    
    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public static CosmeticsPlugin getInstance() {
        return instance;
    }
}