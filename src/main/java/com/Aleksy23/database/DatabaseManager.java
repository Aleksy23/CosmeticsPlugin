package com.Aleksy23.database;

import com.Aleksy23.CosmeticsPlugin;
import org.bukkit.Effect;
import java.sql.*;
import java.util.UUID;

public class DatabaseManager {
    private Connection connection;
    private final CosmeticsPlugin plugin;
    
    public DatabaseManager(CosmeticsPlugin plugin) {
        this.plugin = plugin;
        initDatabase();
    }
    
    private void initDatabase() {
        try {
            // Załaduj sterownik SQLite
            Class.forName("org.sqlite.JDBC");
            
            // Upewnij się że folder istnieje
            if (!plugin.getDataFolder().exists()) {
                plugin.getDataFolder().mkdirs();
            }
            
            String dbPath = plugin.getDataFolder().getAbsolutePath() + "/cosmetics.db";
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            createTables();
            plugin.getLogger().info("Połączono z bazą danych SQLite: " + dbPath);
        } catch (SQLException e) {
            plugin.getLogger().severe("Nie można połączyć z bazą danych: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            plugin.getLogger().severe("Nie można załadować sterownika SQLite: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void createTables() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS player_cosmetics (" +
                    "uuid TEXT PRIMARY KEY," +
                    "active_particle TEXT," +
                    "active_pet TEXT," +
                    "active_wings TEXT," +
                    "last_login TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                    ")";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.execute();
        }
    }
    
    public void savePlayerCosmetics(UUID playerId, Effect particle, String pet, Effect wings) {
        String sql = "INSERT OR REPLACE INTO player_cosmetics (uuid, active_particle, active_pet, active_wings) " +
                    "VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, playerId.toString());
            stmt.setString(2, particle != null ? particle.name() : null);
            stmt.setString(3, pet);
            stmt.setString(4, wings != null ? wings.name() : null);
            stmt.executeUpdate();
        } catch (SQLException e) {
            plugin.getLogger().severe("Błąd zapisywania danych: " + e.getMessage());
        }
    }
    
    // Dodaj metodę do ładowania danych gracza
    public PlayerCosmetics loadPlayerCosmetics(UUID playerId) {
        String sql = "SELECT active_particle, active_pet, active_wings FROM player_cosmetics WHERE uuid = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, playerId.toString());
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                String particleName = rs.getString("active_particle");
                String petName = rs.getString("active_pet");
                String wingsName = rs.getString("active_wings");
                
                Effect particle = null;
                Effect wings = null;
                
                // Bezpieczne parsowanie Effect
                if (particleName != null) {
                    try {
                        particle = Effect.valueOf(particleName);
                    } catch (IllegalArgumentException e) {
                        plugin.getLogger().warning("Nieznany efekt particle: " + particleName);
                    }
                }
                
                if (wingsName != null) {
                    try {
                        wings = Effect.valueOf(wingsName);
                    } catch (IllegalArgumentException e) {
                        plugin.getLogger().warning("Nieznany efekt wings: " + wingsName);
                    }
                }
                
                return new PlayerCosmetics(particle, petName, wings);
            }
        } catch (SQLException e) {
            plugin.getLogger().severe("Błąd ładowania danych: " + e.getMessage());
            e.printStackTrace();
        }
        
        return new PlayerCosmetics(null, null, null); // Domyślne wartości
    }
    
    // Dodaj metodę do zamykania połączenia
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            plugin.getLogger().severe("Błąd zamykania bazy danych: " + e.getMessage());
        }
    }
    
    // Klasa pomocnicza do przechowywania danych kosmetyków gracza
    public static class PlayerCosmetics {
        private final Effect particle;
        private final String pet;
        private final Effect wings;
        
        public PlayerCosmetics(Effect particle, String pet, Effect wings) {
            this.particle = particle;
            this.pet = pet;
            this.wings = wings;
        }
        
        public Effect getParticle() { return particle; }
        public String getPet() { return pet; }
        public Effect getWings() { return wings; }
    }
}