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

        // Sprawdź czy gracz się porusza
        boolean isMoving = from.distanceSquared(to) > 0.0001;

        if (isMoving) {
            // Tylko gdy gracz się porusza, synchronizuj kierunek patrzenia peta z graczem
            Location petLoc = pet.getLocation();
            petLoc.setYaw(to.getYaw());
            // Nie zmieniamy pitch peta - pozostaje naturalny
            pet.teleport(petLoc);

            // Poruszanie petem w kierunku patrzenia gracza
            Vector direction = to.getDirection().setY(0).normalize();
            double speed = 0.6;
            if (player.isSprinting()) speed = 1.0;

            Vector velocity = direction.multiply(speed);
            velocity.setY(pet.getVelocity().getY()); // zachowaj Y (skok/grawitacja)
            pet.setVelocity(velocity);
        } else {
            // Zatrzymaj peta gdy gracz stoi - ale nie zmieniaj kierunku patrzenia
            Vector velocity = new Vector(0, pet.getVelocity().getY(), 0);
            pet.setVelocity(velocity);
        }

        // Pozwól na skakanie (lot) tylko podczas jazdy na petach
        if (!player.getAllowFlight()) {
            player.setAllowFlight(true);
        }
    }
}