package com.Aleksy23.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;

import com.Aleksy23.manager.PetManager;
import com.Aleksy23.CosmeticsPlugin;

public class PetJumpListener implements Listener {

    @EventHandler
    public void onPlayerJump(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();

        if (player.getVehicle() instanceof LivingEntity) {
            LivingEntity pet = (LivingEntity) player.getVehicle();

            // Sprawdź czy to jest nasz pet
            if (!PetManager.isPet(pet)) {
                return;
            }

            // Różne wysokości skoków dla różnych petów
            double jumpHeight = 0.7;
            if (pet.getType() == EntityType.CHICKEN) {
                jumpHeight = 0.9; // Kurczaki skaczą wyżej
            } else if (pet.getType() == EntityType.PIG) {
                jumpHeight = 0.6; // Świnie skaczą niżej
            } else if (pet.getType() == EntityType.WOLF) {
                jumpHeight = 0.8; // Wilki skaczą średnio
            }

            // Skok peta
            pet.setVelocity(pet.getVelocity().setY(jumpHeight));
            event.setCancelled(true);
            
            // Krótki cooldown na lot
            player.setAllowFlight(false);
            
            // Ponownie włącz lot po 10 tickach (0.5 sekundy)
            Bukkit.getScheduler().runTaskLater(
                CosmeticsPlugin.getInstance(), 
                () -> {
                    if (player.getVehicle() == pet) {
                        player.setAllowFlight(true);
                    }
                }, 
                10L
            );
        }
    }
}