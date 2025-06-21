package com.Aleksy23;

import com.Aleksy23.commands.CosmeticsCommand;
import com.Aleksy23.commands.PetNameCommand;
import com.Aleksy23.listeners.MenuClickListener;
import com.Aleksy23.listeners.PetDamageListener;
import com.Aleksy23.listeners.PetRideListener;
import com.Aleksy23.listeners.PetJumpListener;
import com.Aleksy23.listeners.PetDismountListener;
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

        new ParticleTask(this).runTaskTimer(this, 0L, 5L);
        new MiniPetFollowTask(this).runTaskTimer(this, 0L, 1L);
        new PetFollowTask(this).runTaskTimer(this, 0L, 10L);
        new WingsFollowTask(this).runTaskTimer(this, 0L, 8L);
        petDamageManager = new PetDamageManager();
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
    }

    public static CosmeticsPlugin getInstance() {
        return instance;
    }
}