package com.Aleksy23.listeners;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;

public class PetJumpListener implements Listener {

    @EventHandler
    public void onPlayerJump(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();

        if (player.getVehicle() instanceof LivingEntity) {
            LivingEntity pet = (LivingEntity) player.getVehicle();

            // Skok peta
            pet.setVelocity(pet.getVelocity().setY(0.7));
            event.setCancelled(true);
            player.setAllowFlight(false); // wyłącz lot do następnego dotknięcia ziemi
        }
    }
}