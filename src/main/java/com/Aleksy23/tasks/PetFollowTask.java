package com.Aleksy23.tasks;

import com.Aleksy23.CosmeticsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
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
        try {
            for (Player player : Bukkit.getOnlinePlayers()) {
                UUID playerUUID = player.getUniqueId();
                LivingEntity pet = CosmeticsPlugin.activePets.get(playerUUID);

                if (pet == null || pet.isDead()) {
                    CosmeticsPlugin.activePets.remove(playerUUID);
                    continue;
                }

                // Sprawdź czy gracz i pet są w tym samym świecie
                if (!player.getWorld().equals(pet.getWorld())) {
                    pet.teleport(player.getLocation());
                    continue;
                }

                // Sprawdź czy ktoś jedzie na pecie
                if (pet.getPassenger() != null) {
                    continue;
                }

                Location playerLoc = player.getLocation();
                Location petLoc = pet.getLocation();
                double distance = playerLoc.distance(petLoc);

                // Jeśli pet jest za daleko, teleportuj go
                if (distance > 20.0) {
                    Vector direction = playerLoc.getDirection().multiply(-2);
                    Location teleportLoc = playerLoc.clone().add(direction).add(0, 0.5, 0);

                    // Sprawdź czy lokacja teleportacji jest bezpieczna
                    if (teleportLoc.getBlock().getType() == Material.AIR &&
                            teleportLoc.clone().add(0, 1, 0).getBlock().getType() == Material.AIR) {
                        pet.teleport(teleportLoc);
                    } else {
                        pet.teleport(playerLoc.clone().add(0, 1, 0));
                    }
                }
                // Jeśli pet jest w zasięgu ale daleko, niech idzie
                else if (distance > 4.0 && distance <= 20.0) {
                    // Oblicz kierunek do gracza
                    Vector direction = playerLoc.toVector().subtract(petLoc.toVector()).normalize();

                    // Sprawdź czy gracz się porusza
                    if (player.getVelocity().lengthSquared() > 0.01) {
                        direction.multiply(0.4); // Szybsze podążanie gdy gracz się rusza
                    } else {
                        direction.multiply(0.2); // Wolniejsze gdy gracz stoi
                    }

                    direction.setY(0); // Nie lataj w górę
                    pet.setVelocity(direction);

                    // Pet patrzy na gracza
                    Vector lookDirection = playerLoc.toVector().subtract(petLoc.toVector()).normalize();
                    Location newLoc = petLoc.clone();
                    newLoc.setDirection(lookDirection);
                    pet.teleport(newLoc);
                }
                // Jeśli pet jest blisko, zatrzymaj go
                else if (distance <= 4.0) {
                    pet.setVelocity(new Vector(0, pet.getVelocity().getY(), 0));
                }
            }
        } catch (Exception e) {
            CosmeticsPlugin.getInstance().getLogger().severe("Błąd w PetFollowTask: " + e.getMessage());
            e.printStackTrace();
        }
    }
}