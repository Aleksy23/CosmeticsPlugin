package com.Aleksy23.tasks;

import com.Aleksy23.CosmeticsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class WingsFollowTask extends BukkitRunnable {

    private final CosmeticsPlugin plugin;

    public WingsFollowTask(CosmeticsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            UUID playerUUID = player.getUniqueId();

            if (CosmeticsPlugin.activeWings.containsKey(playerUUID) && CosmeticsPlugin.activeWings.get(playerUUID) != null) {
                Effect wingEffect = CosmeticsPlugin.activeWings.get(playerUUID);
                Location playerLoc = player.getLocation();

                // Obliczanie kierunku gracza
                double yawRad = Math.toRadians(playerLoc.getYaw());

                // Tworzenie różnych typów skrzydeł w zależności od efektu
                if (wingEffect == Effect.FIREWORKS_SPARK) {
                    // Anielskie skrzydła - duże, szerokie, ZŁOTO-ŻÓŁTE
                    createAngelWings(player, playerLoc, yawRad);
                } else if (wingEffect == Effect.SMOKE) {
                    // Mroczne skrzydła - małe, ostre, CIEMNO SZARE
                    createDarkWings(player, playerLoc, yawRad);
                } else if (wingEffect == Effect.FLAME) {
                    // Smocze skrzydła - długie, kościste, JASNO CZERWONE
                    createDragonWings(player, playerLoc, yawRad);
                } else if (wingEffect == Effect.ENDER_SIGNAL) {
                    // Endermanowe skrzydła - cienkie, eleganckie, RÓŻOWE/MAGENTA
                    createEnderWings(player, playerLoc, yawRad);
                }
            }
        }
    }

    // ANIELSKIE SKRZYDŁA - duże, okrągłe, ZŁOTO-ŻÓŁTE
    private void createAngelWings(Player player, Location playerLoc, double yawRad) {
        double[][] angelWingShape = {
            // Duże, okrągłe skrzydła anioła
            {0.3, 0.0}, {0.5, 0.2}, {0.7, 0.4}, {0.9, 0.6}, {1.1, 0.8}, {1.3, 0.9}, {1.5, 0.8},
            {0.4, -0.1}, {0.6, 0.1}, {0.8, 0.3}, {1.0, 0.5}, {1.2, 0.7}, {1.4, 0.8}, {1.6, 0.7},
            {0.2, 0.1}, {0.4, 0.3}, {0.6, 0.5}, {0.8, 0.7}, {1.0, 0.9}, {1.2, 1.0}, {1.4, 0.9},
            {0.3, 0.2}, {0.5, 0.4}, {0.7, 0.6}, {0.9, 0.8}, {1.1, 1.0}, {1.3, 1.1}, {1.5, 1.0},
            {0.5, 0.0}, {0.7, 0.2}, {0.9, 0.4}, {1.1, 0.6}, {1.3, 0.7}, {1.5, 0.6}
        };
        createWingFromShape(player, playerLoc, yawRad, angelWingShape, 0.5, 0.6, 4); // ŻÓŁTY kolor (4)
    }

    // MROCZNE SKRZYDŁA - małe, ostre, CIEMNO SZARE
    private void createDarkWings(Player player, Location playerLoc, double yawRad) {
        double[][] darkWingShape = {
            // Małe, ostre skrzydła demona
            {0.4, 0.0}, {0.6, 0.1}, {0.8, 0.3}, {1.0, 0.5}, {1.2, 0.4}, {1.4, 0.2},
            {0.5, -0.1}, {0.7, 0.0}, {0.9, 0.2}, {1.1, 0.4}, {1.3, 0.3}, {1.5, 0.1},
            {0.3, 0.1}, {0.5, 0.2}, {0.7, 0.4}, {0.9, 0.6}, {1.1, 0.5}, {1.3, 0.3},
            {0.6, 0.0}, {0.8, 0.1}, {1.0, 0.3}, {1.2, 0.2}, {1.4, 0.0}
        };
        createWingFromShape(player, playerLoc, yawRad, darkWingShape, 0.4, 0.5, 8); // CIEMNO SZARY kolor (8)
    }

    // SMOCZE SKRZYDŁA - długie, kościste, JASNO CZERWONE
    private void createDragonWings(Player player, Location playerLoc, double yawRad) {
        double[][] dragonWingShape = {
            // Długie, kościste skrzydła smoka
            {0.2, 0.0}, {0.4, 0.0}, {0.6, 0.1}, {0.8, 0.2}, {1.0, 0.4}, {1.2, 0.6}, {1.4, 0.8}, {1.6, 1.0}, {1.8, 0.9},
            {0.3, -0.1}, {0.5, -0.1}, {0.7, 0.0}, {0.9, 0.1}, {1.1, 0.3}, {1.3, 0.5}, {1.5, 0.7}, {1.7, 0.8},
            {0.4, 0.1}, {0.6, 0.2}, {0.8, 0.3}, {1.0, 0.5}, {1.2, 0.7}, {1.4, 0.9}, {1.6, 1.1},
            {0.5, 0.0}, {0.7, 0.1}, {0.9, 0.2}, {1.1, 0.4}, {1.3, 0.6}, {1.5, 0.8}, {1.7, 0.9},
            // Dodatkowe "kości" skrzydeł
            {0.8, 0.0}, {1.0, 0.0}, {1.2, 0.0}, {1.4, 0.1}, {1.6, 0.2}
        };
        createWingFromShape(player, playerLoc, yawRad, dragonWingShape, 0.3, 0.7, 14); // CZERWONY kolor (14)
    }

    // ENDERMANOWE SKRZYDŁA - cienkie, eleganckie, RÓŻOWE/MAGENTA
    private void createEnderWings(Player player, Location playerLoc, double yawRad) {
        double[][] enderWingShape = {
            // Cienkie, eleganckie skrzydła endermana
            {0.5, 0.0}, {0.7, 0.2}, {0.9, 0.4}, {1.1, 0.6}, {1.3, 0.8}, {1.5, 1.0}, {1.7, 1.1}, {1.9, 1.0},
            {0.6, -0.1}, {0.8, 0.1}, {1.0, 0.3}, {1.2, 0.5}, {1.4, 0.7}, {1.6, 0.9}, {1.8, 1.0},
            {0.4, 0.1}, {0.6, 0.3}, {0.8, 0.5}, {1.0, 0.7}, {1.2, 0.9}, {1.4, 1.1}, {1.6, 1.2},
            {0.7, 0.0}, {0.9, 0.2}, {1.1, 0.4}, {1.3, 0.6}, {1.5, 0.8}, {1.7, 0.9},
            // Cienkie linie - charakterystyczne dla endermana
            {0.3, 0.0}, {0.5, 0.1}, {0.7, 0.3}, {0.9, 0.5}, {1.1, 0.7}, {1.3, 0.9}, {1.5, 1.1}
        };
        createWingFromShape(player, playerLoc, yawRad, enderWingShape, 0.6, 0.8, 2); // MAGENTA/RÓŻOWY kolor (2)
    }

    // Uniwersalna metoda do tworzenia skrzydeł z podanego kształtu
    private void createWingFromShape(Player player, Location playerLoc, double yawRad, double[][] wingShape, double backOffset, double baseHeight, int colorData) {
        for (double[] point : wingShape) {
            // Lewe skrzydło
            createWingPoint(player, playerLoc, yawRad, -1.0, point, backOffset, baseHeight, colorData);
            // Prawe skrzydło
            createWingPoint(player, playerLoc, yawRad, 1.0, point, backOffset, baseHeight, colorData);
        }
    }

    private void createWingPoint(Player player, Location playerLoc, double yawRad, double side, double[] point, double backOffset, double baseHeight, int colorData) {
        // Pozycja każdego punktu skrzydła
        double xOffset = side * point[0];
        double yOffset = baseHeight + point[1];
        double zOffset = -backOffset;

        // Obracamy punkt zgodnie z kierunkiem gracza
        double cosYaw = Math.cos(yawRad);
        double sinYaw = Math.sin(yawRad);

        double rotatedX = xOffset * cosYaw - zOffset * sinYaw;
        double rotatedZ = xOffset * sinYaw + zOffset * cosYaw;

        // Finalna pozycja cząsteczki
        Location particleLoc = playerLoc.clone().add(rotatedX, yOffset, rotatedZ);

        // Spawowanie statycznej cząsteczki z odpowiednim kolorem
        player.getWorld().playEffect(particleLoc, Effect.COLOURED_DUST, colorData);
    }
}