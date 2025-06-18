package com.Aleksy23.tasks;

import com.Aleksy23.CosmeticsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.UUID;

public class PetFollowTask extends BukkitRunnable {

    private final CosmeticsPlugin plugin;

    public PetFollowTask(CosmeticsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            UUID playerUUID = player.getUniqueId();

            if (CosmeticsPlugin.activePets.containsKey(playerUUID) && CosmeticsPlugin.activePets.get(playerUUID) != null) {
                LivingEntity pet = CosmeticsPlugin.activePets.get(playerUUID);

                if (pet == null || pet.isDead()) {
                    CosmeticsPlugin.activePets.remove(playerUUID);
                    continue;
                }

                Location playerLoc = player.getLocation();
                Location petLoc = pet.getLocation();

                double distance = playerLoc.distance(petLoc);

                // Jeśli pet jest za daleko, teleportuj go
                if (distance > 8.0) {
                    Vector direction = playerLoc.getDirection().multiply(-2); // Za graczem
                    Location teleportLoc = playerLoc.clone().add(direction).add(0, 0.5, 0);
                    pet.teleport(teleportLoc);
                } 
                // Jeśli pet jest w średniej odległości, przesuń go w stronę gracza
                else if (distance > 3.0) {
                    Vector direction = playerLoc.toVector().subtract(petLoc.toVector()).normalize().multiply(0.3);
                    direction.setY(0); // Nie lataj w górę
                    pet.setVelocity(direction);
                }
            }
        }
    }
}