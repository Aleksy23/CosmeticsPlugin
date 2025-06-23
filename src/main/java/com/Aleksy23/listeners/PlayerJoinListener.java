package com.Aleksy23.listeners;

import com.Aleksy23.CosmeticsPlugin;
import com.Aleksy23.database.DatabaseManager;
import org.bukkit.Effect;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class PlayerJoinListener implements Listener {
    
    private final CosmeticsPlugin plugin;
    
    public PlayerJoinListener(CosmeticsPlugin plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();
        
        // Załaduj kosmetyki gracza z bazy danych
        DatabaseManager.PlayerCosmetics cosmetics = plugin.getDatabaseManager().loadPlayerCosmetics(playerUUID);
        
        if (cosmetics.getParticle() != null) {
            CosmeticsPlugin.activeParticles.put(playerUUID, cosmetics.getParticle());
        }
        
        if (cosmetics.getWings() != null) {
            CosmeticsPlugin.activeWings.put(playerUUID, cosmetics.getWings());
        }
        
        // Pet zostanie przywrócony gdy gracz go wybierze z menu
    }
    
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();
        
        // Zapisz aktualne kosmetyki gracza
        Effect particle = CosmeticsPlugin.activeParticles.get(playerUUID);
        Effect wings = CosmeticsPlugin.activeWings.get(playerUUID);
        String pet = null; // TODO: dodaj logikę zapisywania typu peta
        
        plugin.getDatabaseManager().savePlayerCosmetics(playerUUID, particle, pet, wings);
        
        // Usuń kosmetyki z pamięci
        CosmeticsPlugin.activeParticles.remove(playerUUID);
        CosmeticsPlugin.activeWings.remove(playerUUID);
        // Pety są już usuwane w MenuClickListener
    }
}