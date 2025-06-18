package com.Aleksy23.tasks;

import com.Aleksy23.CosmeticsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.UUID;

public class MiniPetFollowTask extends BukkitRunnable { // <-- Zmieniona nazwa klasy

    private final CosmeticsPlugin plugin;

    public MiniPetFollowTask(CosmeticsPlugin plugin) { // <-- Zmieniona nazwa konstruktora
        this.plugin = plugin;
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            UUID playerUUID = player.getUniqueId();

            if (CosmeticsPlugin.activeMiniPets.containsKey(playerUUID) && CosmeticsPlugin.activeMiniPets.get(playerUUID) != null) { // <-- Zmieniona HashMapa
                ArmorStand miniPet = CosmeticsPlugin.activeMiniPets.get(playerUUID); // <-- Zmieniona HashMapa

                if (miniPet == null || miniPet.isDead()) {
                    CosmeticsPlugin.activeMiniPets.remove(playerUUID); // <-- Zmieniona HashMapa
                    continue;
                }

                Location playerLoc = player.getLocation();
                Location miniPetLoc = miniPet.getLocation();

                double distance = playerLoc.distance(miniPetLoc);

                double offsetX = -Math.sin(Math.toRadians(playerLoc.getYaw() + 90)) * 0.8;
                double offsetZ = Math.cos(Math.toRadians(playerLoc.getYaw() + 90)) * 0.8;
                double offsetY = -0.5;

                Location targetLoc = playerLoc.clone().add(offsetX, offsetY, offsetZ);

                if (distance > 0.5) {
                    Vector direction = targetLoc.toVector().subtract(miniPetLoc.toVector()).normalize().multiply(0.4);
                    miniPet.teleport(miniPetLoc.add(direction));

                    Location newMiniPetLoc = miniPet.getLocation();
                    newMiniPetLoc.setYaw(playerLoc.getYaw());
                    newMiniPetLoc.setPitch(0);
                    miniPet.teleport(newMiniPetLoc);
                } else {
                    miniPet.teleport(targetLoc);
                }
            }
        }
    }
}