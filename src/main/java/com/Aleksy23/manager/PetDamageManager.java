package com.Aleksy23.manager;

import java.util.HashMap;
import java.util.Map;

public class PetDamageManager {
    private final Map<String, Integer> petDamageMap = new HashMap<>();

    public void dealDamage(String petId, int damage) {
        petDamageMap.put(petId, petDamageMap.getOrDefault(petId, 0) + damage);
    }

    public int getTotalDamage(String petId) {
        return petDamageMap.getOrDefault(petId, 0);
    }

    public void resetDamage(String petId) {
        petDamageMap.remove(petId);
    }
}
