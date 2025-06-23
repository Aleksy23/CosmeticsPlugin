package com.Aleksy23.manager;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownManager {
    private final Map<UUID, Long> cooldowns = new HashMap<>();
    private final long cooldownTime;
    
    public CooldownManager(long cooldownTimeMs) {
        this.cooldownTime = cooldownTimeMs;
    }
    
    public boolean isOnCooldown(UUID playerId) {
        Long lastUsed = cooldowns.get(playerId);
        if (lastUsed == null) return false;
        
        return System.currentTimeMillis() - lastUsed < cooldownTime;
    }
    
    public void setCooldown(UUID playerId) {
        cooldowns.put(playerId, System.currentTimeMillis());
    }
    
    public long getRemainingCooldown(UUID playerId) {
        Long lastUsed = cooldowns.get(playerId);
        if (lastUsed == null) return 0;
        
        long remaining = cooldownTime - (System.currentTimeMillis() - lastUsed);
        return Math.max(0, remaining);
    }
}