package com.Aleksy23.listeners;

import com.Aleksy23.CosmeticsPlugin;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import java.util.UUID;

public class PetDismountListener implements Listener {

    @EventHandler
    public void onPlayerSneak(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        
        if (player.getVehicle() instanceof LivingEntity && event.isSneaking()) {
            LivingEntity vehicle = (LivingEntity) player.getVehicle();
            
            // Sprawdź czy to jest pet
            boolean isPet = false;
            for (UUID playerUUID : CosmeticsPlugin.activePets.keySet()) {
                LivingEntity pet = CosmeticsPlugin.activePets.get(playerUUID);
                if (pet != null && pet.equals(vehicle)) {
                    isPet = true;
                    break;
                }
            }
            
            if (isPet) {
                player.leaveVehicle();
                player.sendMessage("§eZsiadłeś z peta!");
                
                // Wyłącz lot jeśli gracz nie ma uprawnień
                if (!player.hasPermission("essentials.fly")) {
                    player.setAllowFlight(false);
                    player.setFlying(false);
                }
            }
        }
    }
}
