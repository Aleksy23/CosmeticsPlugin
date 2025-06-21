package com.Aleksy23.tasks;

import com.Aleksy23.CosmeticsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.Color; // Import dla koloru
import org.bukkit.util.Vector; // Potrzebne do rozmieszczania cząsteczek wokół gracza

import java.util.Random;
import java.util.UUID;

public class ParticleTask extends BukkitRunnable {

    private final CosmeticsPlugin plugin;
    private final Random random = new Random();

    public ParticleTask(CosmeticsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            UUID playerUUID = player.getUniqueId();

            if (CosmeticsPlugin.activeParticles.containsKey(playerUUID) && CosmeticsPlugin.activeParticles.get(playerUUID) != null) {
                Effect particleEffect = CosmeticsPlugin.activeParticles.get(playerUUID);
                Location loc = player.getLocation().clone();

                // Specjalna obsługa efektu tęczy - PRAWDZIWA TĘCZA
                if (particleEffect == Effect.HAPPY_VILLAGER) {
                    double time = System.currentTimeMillis() / 1000.0;
                    
                    // Tworzymy półkole tęczy nad graczem
                    for (int i = 0; i < 30; i++) { // Zmniejszono z 50 do 30
                        double angle = Math.PI * i / 29.0; // Półkole (180 stopni)
                        double radius = 1.8;
                        
                        double x = radius * Math.cos(angle);
                        double y = 1.2 + radius * Math.sin(angle) * 0.4; // Niższa tęcza
                        double z = 0;
                        
                        // Obracamy tęczę wokół gracza
                        double rotationAngle = time * 0.3;
                        double finalX = x * Math.cos(rotationAngle) - z * Math.sin(rotationAngle);
                        double finalZ = x * Math.sin(rotationAngle) + z * Math.cos(rotationAngle);
                        
                        Location rainbowLoc = loc.clone().add(finalX, y, finalZ);
                        
                        // Tylko kolorowe cząsteczki bez randomowych
                        double colorPosition = (double) i / 29.0;
                        Effect rainbowEffect;
                        
                        if (colorPosition < 0.14) {
                            rainbowEffect = Effect.HEART; // Czerwony
                        } else if (colorPosition < 0.28) {
                            rainbowEffect = Effect.LAVA_POP; // Pomarańczowy
                        } else if (colorPosition < 0.42) {
                            rainbowEffect = Effect.HAPPY_VILLAGER; // Żółto-zielony
                        } else if (colorPosition < 0.56) {
                            rainbowEffect = Effect.COLOURED_DUST;
                        } else if (colorPosition < 0.70) {
                            rainbowEffect = Effect.COLOURED_DUST;
                        } else if (colorPosition < 0.84) {
                            rainbowEffect = Effect.COLOURED_DUST;
                        } else {
                            rainbowEffect = Effect.COLOURED_DUST;
                        }
                        
                        player.getWorld().playEffect(rainbowLoc, rainbowEffect, 0);
                    }
                } 
                // Więcej iskier dla efektu CRIT
                else if (particleEffect == Effect.CRIT) {
                    for (int i = 0; i < 8; i++) { // Zmniejszono z 15 do 8
                        double offsetX = (random.nextDouble() - 0.5) * 2.0;
                        double offsetY = random.nextDouble() * 2.0;
                        double offsetZ = (random.nextDouble() - 0.5) * 2.0;
                        
                        Location sparkLoc = loc.clone().add(offsetX, offsetY + 0.5, offsetZ);
                        player.getWorld().playEffect(sparkLoc, Effect.CRIT, 0);
                    }
                }
                // Więcej śnieżynek dla efektu SNOW
                else if (particleEffect == Effect.SNOW_SHOVEL) {
                    for (int i = 0; i < 10; i++) { // Zmniejszono z 20 do 10
                        double offsetX = (random.nextDouble() - 0.5) * 3.0;
                        double offsetY = random.nextDouble() * 3.0 + 1.0;
                        double offsetZ = (random.nextDouble() - 0.5) * 3.0;
                        
                        Location snowLoc = loc.clone().add(offsetX, offsetY, offsetZ);
                        player.getWorld().playEffect(snowLoc, Effect.SNOW_SHOVEL, 0);
                        
                        // Dodatkowe płatki śniegu
                        if (random.nextDouble() < 0.2) { // Zmniejszono z 0.3 do 0.2
                            player.getWorld().playEffect(snowLoc.add(0, 0.5, 0), Effect.FIREWORKS_SPARK, 0);
                        }
                    }
                }
                // Dodatkowe bąbelki
                else if (particleEffect == Effect.WATERDRIP) {
                    for (int i = 0; i < 3; i++) { // Zmniejszono z 12 do 3
                        double offsetX = (random.nextDouble() - 0.5) * 2.5;
                        double offsetY = random.nextDouble() * 2.5;
                        double offsetZ = (random.nextDouble() - 0.5) * 2.5;
                        
                        Location bubbleLoc = loc.clone().add(offsetX, offsetY + 0.5, offsetZ);
                        player.getWorld().playEffect(bubbleLoc, Effect.WATERDRIP, 0);
                    }
                }
                // Zmniejszony efekt flame z iskrami
                else if (particleEffect == Effect.MOBSPAWNER_FLAMES) {
                    // Główny efekt flame - zmniejszono jeszcze bardziej
                    for (int i = 0; i < 2; i++) { // Zmniejszono z 4 do 2
                        double offsetX = (random.nextDouble() - 0.5) * 1.0;
                        double offsetY = random.nextDouble() * 1.0;
                        double offsetZ = (random.nextDouble() - 0.5) * 1.0;
                        
                        Location flameLoc = loc.clone().add(offsetX, offsetY + 0.5, offsetZ);
                        player.getWorld().playEffect(flameLoc, Effect.MOBSPAWNER_FLAMES, 0);
                        
                        // Dodatkowe iskry - jeszcze mniej
                        if (random.nextDouble() < 0.2) { // Zmniejszono z 0.3 do 0.2
                            player.getWorld().playEffect(flameLoc.add(0, 0.2, 0), Effect.CRIT, 0);
                        }
                    }
                }
                // Standardowe efekty z lekkim ulepszeniem
                else {
                    for (int i = 0; i < 1; i++) { // Zmniejszono z 3 do 2
                        double offsetX = (random.nextDouble() - 0.5) * 1.0;
                        double offsetY = random.nextDouble() * 1.0;
                        double offsetZ = (random.nextDouble() - 0.5) * 1.0;
                        
                        Location particleLoc = loc.clone().add(offsetX, offsetY + 0.5, offsetZ);
                        player.getWorld().playEffect(particleLoc, particleEffect, 0);
                    }
                }
            }
        }
    }
}