package com.Aleksy23.listeners;

import com.Aleksy23.CosmeticsPlugin;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import java.util.UUID;

public class PetRideListener implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (!(player.getVehicle() instanceof LivingEntity)) {
            return;
        }

        LivingEntity pet = (LivingEntity) player.getVehicle();

        // Sprawdź czy to jest pet któregoś gracza
        boolean isPet = false;
        for (UUID playerUUID : CosmeticsPlugin.activePets.keySet()) {
            LivingEntity p = CosmeticsPlugin.activePets.get(playerUUID);
            if (p != null && p.equals(pet)) {
                isPet = true;
                break;
            }
        }
        if (!isPet) return;

        Location from = event.getFrom();
        Location to = event.getTo();

        // Sprawdź czy gracz porusza myszką (zmiana kierunku patrzenia)
        boolean isLooking = Math.abs(from.getYaw() - to.getYaw()) > 1.0 || 
                           Math.abs(from.getPitch() - to.getPitch()) > 1.0;
        
        // Sprawdź czy gracz się porusza (WASD)
        boolean isMoving = from.distanceSquared(to) > 0.0001;

        if (isLooking) {
            // Synchronizuj kierunek patrzenia peta z graczem
            Location petLoc = pet.getLocation();
            petLoc.setYaw(to.getYaw());
            petLoc.setPitch(0); // Pet nie patrzy w górę/dół
            pet.teleport(petLoc);
        }

        if (isMoving) {
            // Poruszanie petem w kierunku patrzenia gracza
            Vector direction = to.getDirection().setY(0).normalize();
            
            double speed = 0.4; // Podstawowa prędkość
            if (player.isSprinting()) {
                speed = 0.8; // Szybsza prędkość podczas sprintu
            }
            if (player.isSneaking()) {
                speed = 0.2; // Wolniejsza prędkość podczas skradania
            }

            Vector velocity = direction.multiply(speed);
            velocity.setY(pet.getVelocity().getY()); // Zachowaj Y (skok/grawitacja)
            pet.setVelocity(velocity);
        } else {
            // Zatrzymaj peta gdy gracz nie naciska WASD
            Vector velocity = new Vector(0, pet.getVelocity().getY(), 0);
            pet.setVelocity(velocity);
        }

        // Pozwól na latanie tylko podczas jazdy
        if (!player.getAllowFlight()) {
            player.setAllowFlight(true);
        }
    }
}