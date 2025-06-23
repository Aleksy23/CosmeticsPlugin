package com.Aleksy23.manager;

import com.Aleksy23.CosmeticsPlugin;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.ChatColor;

import java.util.UUID;

public class PetManager {
    
    public static LivingEntity spawnPet(Player player, EntityType petType, String displayName) {
        // Usuń poprzedniego peta jeśli istnieje
        removePet(player.getUniqueId());
        
        // Znajdź bezpieczną lokację spawnu
        Location spawnLoc = findSafeSpawnLocation(player.getLocation());
        
        // Spawn peta
        LivingEntity pet = (LivingEntity) player.getWorld().spawnEntity(spawnLoc, petType);
        
        // Konfiguracja peta
        configurePet(pet, player, displayName);
        
        // Dodaj do listy aktywnych petów
        CosmeticsPlugin.activePets.put(player.getUniqueId(), pet);
        
        return pet;
    }
    
    private static void configurePet(LivingEntity pet, Player player, String displayName) {
        // Podstawowa konfiguracja
        pet.setCustomName(ChatColor.translateAlternateColorCodes('&', displayName));
        pet.setCustomNameVisible(true);
        pet.setRemoveWhenFarAway(false);
        pet.setCanPickupItems(false);
        
        // Ustawienia zdrowia
        double maxHealth = getMaxHealthForPetType(pet.getType());
        pet.setMaxHealth(maxHealth);
        pet.setHealth(maxHealth);
        
        // Konfiguracja specyficzna dla typu
        if (pet instanceof Tameable) {
            Tameable tameablePet = (Tameable) pet;
            tameablePet.setTamed(true);
            tameablePet.setOwner(player);
        }
        
        if (pet instanceof Wolf) {
            Wolf wolf = (Wolf) pet;
            wolf.setCollarColor(org.bukkit.DyeColor.BLUE); // Niebieski kolor obroży
            wolf.setSitting(false);
        }
        
        if (pet instanceof Ageable) {
            Ageable ageable = (Ageable) pet;
            ageable.setAdult(); // Zawsze dorosłe
        }
    }
    
    private static double getMaxHealthForPetType(EntityType type) {
        switch (type) {
            case WOLF:
                return 30.0;
            case PIG:
                return 25.0;
            case CHICKEN:
                return 15.0;
            default:
                return 20.0;
        }
    }
    
    private static Location findSafeSpawnLocation(Location playerLoc) {
        Location spawnLoc = playerLoc.clone().add(0, 0.5, 0);
        
        // Sprawdź czy lokacja jest bezpieczna
        for (int y = 0; y >= -3; y--) {
            Location testLoc = spawnLoc.clone().add(0, y, 0);
            if (testLoc.getBlock().getType().isSolid() && 
                testLoc.clone().add(0, 1, 0).getBlock().getType().isTransparent() &&
                testLoc.clone().add(0, 2, 0).getBlock().getType().isTransparent()) {
                return testLoc.add(0, 1, 0);
            }
        }
        
        return spawnLoc; // Fallback
    }
    
    public static void removePet(UUID playerUUID) {
        LivingEntity oldPet = CosmeticsPlugin.activePets.get(playerUUID);
        if (oldPet != null && !oldPet.isDead()) {
            oldPet.remove();
        }
        CosmeticsPlugin.activePets.remove(playerUUID);
    }
    
    public static boolean isPet(LivingEntity entity) {
        return CosmeticsPlugin.activePets.containsValue(entity);
    }
    
    public static Player getPetOwner(LivingEntity pet) {
        for (UUID playerUUID : CosmeticsPlugin.activePets.keySet()) {
            if (CosmeticsPlugin.activePets.get(playerUUID) == pet) {
                return org.bukkit.Bukkit.getPlayer(playerUUID);
            }
        }
        return null;
    }
}