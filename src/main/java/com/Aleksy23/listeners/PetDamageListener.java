package com.Aleksy23.listeners;

import com.Aleksy23.CosmeticsPlugin;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.UUID;

public class PetDamageListener implements Listener {

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        // Zabezpieczenie mini petów (ArmorStand)
        if (event.getEntity() instanceof ArmorStand) {
            ArmorStand armorStand = (ArmorStand) event.getEntity();
            
            // Sprawdź czy to jest mini pet któregoś gracza
            for (UUID playerUUID : CosmeticsPlugin.activeMiniPets.keySet()) {
                ArmorStand miniPet = CosmeticsPlugin.activeMiniPets.get(playerUUID);
                if (miniPet != null && miniPet.equals(armorStand)) {
                    event.setCancelled(true); // Anuluj obrażenia
                    return;
                }
            }
        }
        
        // Zabezpieczenie prawdziwych petów (LivingEntity)
        if (event.getEntity() instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) event.getEntity();
            
            // Sprawdź czy to jest pet któregoś gracza
            for (UUID playerUUID : CosmeticsPlugin.activePets.keySet()) {
                LivingEntity pet = CosmeticsPlugin.activePets.get(playerUUID);
                if (pet != null && pet.equals(entity)) {
                    event.setCancelled(true); // Anuluj obrażenia
                    return;
                }
            }
        }
    }
    
    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        // Zabezpieczenie przed śmiercią petów - usuń z listy dropów i przywróć zdrowie
        if (event.getEntity() instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) event.getEntity();
            
            // Sprawdź czy to jest pet któregoś gracza
            for (UUID playerUUID : CosmeticsPlugin.activePets.keySet()) {
                LivingEntity pet = CosmeticsPlugin.activePets.get(playerUUID);
                if (pet != null && pet.equals(entity)) {
                    // Wyczyść dropy żeby nic nie spadło
                    event.getDrops().clear();
                    event.setDroppedExp(0);
                    
                    // Przywróć pełne zdrowie petowi (żeby nie umarł)
                    entity.setHealth(entity.getMaxHealth());
                    return;
                }
            }
        }
    }
}