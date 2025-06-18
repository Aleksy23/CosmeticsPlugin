package com.Aleksy23.listeners;

import com.Aleksy23.CosmeticsPlugin;
import com.Aleksy23.gui.ParticleMenu;
import com.Aleksy23.gui.MiniPetMenu;
import com.Aleksy23.gui.PetMenu;
import com.Aleksy23.gui.WingsMenu;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.Location;

import java.util.UUID;

public class MenuClickListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();

        // GŁÓWNE MENU kosmetyków
        if (event.getClickedInventory().getTitle().equals(ChatColor.DARK_AQUA + "Menu Kosmetykow")) {
            event.setCancelled(true);

            if (clickedItem == null || clickedItem.getType() == Material.AIR) {
                return;
            }

            if (clickedItem.getType() == Material.MONSTER_EGG) {
                MiniPetMenu.openMenu(player);
                player.sendMessage(ChatColor.GREEN + "Otworzono menu wyboru Mini Petow.");
            } else if (clickedItem.getType() == Material.LEASH) {
                PetMenu.openMenu(player);
                player.sendMessage(ChatColor.AQUA + "Otworzono menu wyboru Petow.");
            } else if (clickedItem.getType() == Material.FEATHER) {
                WingsMenu.openMenu(player);
                player.sendMessage(ChatColor.LIGHT_PURPLE + "Otworzono menu wyboru Skrzydeł.");
            } else if (clickedItem.getType() == Material.SULPHUR) {
                ParticleMenu.openMenu(player);
                player.sendMessage(ChatColor.AQUA + "Otworzono menu wyboru efektow czasteczkowych.");
            }
        }
        // OBSŁUGA KLIKNIĘĆ W MENU PARTICLE (bez zmian)
        else if (event.getClickedInventory().getTitle().equals(ChatColor.DARK_BLUE + "Wybierz Efekt Particle")) {
            event.setCancelled(true);

            if (clickedItem == null || clickedItem.getType() == Material.AIR) {
                return;
            }

            UUID playerUUID = player.getUniqueId();
            Effect currentEffect = CosmeticsPlugin.activeParticles.get(playerUUID);
            Effect chosenEffect = null;

            if (clickedItem.getType() == Material.REDSTONE) {
                chosenEffect = Effect.HEART;
            } else if (clickedItem.getType() == Material.SULPHUR) {
                chosenEffect = Effect.SMOKE;
            } else if (clickedItem.getType() == Material.GLOWSTONE_DUST) {
                chosenEffect = Effect.CRIT;
            } else if (clickedItem.getType() == Material.NETHER_STAR) {
                chosenEffect = Effect.ENDER_SIGNAL;
            } else if (clickedItem.getType() == Material.DIAMOND) {
                chosenEffect = Effect.HAPPY_VILLAGER;
            } else if (clickedItem.getType() == Material.FIREBALL) {
                chosenEffect = Effect.MOBSPAWNER_FLAMES;
            } else if (clickedItem.getType() == Material.PRISMARINE_SHARD) {
                chosenEffect = Effect.WATERDRIP;
            } else if (clickedItem.getType() == Material.SNOW_BALL) {
                chosenEffect = Effect.SNOW_SHOVEL;
            } else if (clickedItem.getType() == Material.BARRIER) {
                chosenEffect = null;
            }

            if (chosenEffect != null) {
                if (currentEffect == chosenEffect) {
                    CosmeticsPlugin.activeParticles.put(playerUUID, null);
                    player.sendMessage(ChatColor.RED + "Efekt " + clickedItem.getItemMeta().getDisplayName() + " WYLACZONO.");
                } else {
                    CosmeticsPlugin.activeParticles.put(playerUUID, chosenEffect);
                    player.sendMessage(ChatColor.AQUA + "Efekt " + clickedItem.getItemMeta().getDisplayName() + " WLACZONO!");
                }
            } else {
                CosmeticsPlugin.activeParticles.put(playerUUID, null);
                player.sendMessage(ChatColor.RED + "Wszystkie efekty czasteczkowe WYLACZONO.");
            }
            player.closeInventory();
        }
        // OBSŁUGA KLIKNIĘĆ W MENU MINI PETÓW (bez zmian)
        else if (event.getClickedInventory().getTitle().equals(ChatColor.GOLD + "Wybierz Mini Peta")) {
            event.setCancelled(true);

            if (clickedItem == null || clickedItem.getType() == Material.AIR) {
                return;
            }

            UUID playerUUID = player.getUniqueId();

            if (CosmeticsPlugin.activeMiniPets.containsKey(playerUUID) && CosmeticsPlugin.activeMiniPets.get(playerUUID) != null) {
                ArmorStand oldMiniPet = CosmeticsPlugin.activeMiniPets.get(playerUUID);
                if (oldMiniPet != null && !oldMiniPet.isDead()) {
                    oldMiniPet.remove();
                }
                CosmeticsPlugin.activeMiniPets.remove(playerUUID);
            }

            if (clickedItem.getType() == Material.BARRIER) {
                player.sendMessage(ChatColor.RED + "Mini Pet zostal WYLACZONY.");
                player.closeInventory();
                return;
            }

            String petSkullOwner = null;
            if (clickedItem.getType() == Material.SKULL_ITEM && clickedItem.hasItemMeta()) {
                SkullMeta skullMeta = (SkullMeta) clickedItem.getItemMeta();
                if (skullMeta != null && skullMeta.hasOwner()) {
                    petSkullOwner = skullMeta.getOwner();
                }
            }

            if (petSkullOwner != null) {
                Location spawnLoc = player.getLocation().add(0, -1, 0);
                ArmorStand miniPetStand = (ArmorStand) player.getWorld().spawnEntity(spawnLoc, EntityType.ARMOR_STAND);

                miniPetStand.setGravity(false);
                miniPetStand.setCanPickupItems(false);
                miniPetStand.setVisible(false);
                miniPetStand.setSmall(true);
                miniPetStand.setBasePlate(false);

                ItemStack miniPetHead = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
                SkullMeta miniPetHeadMeta = (SkullMeta) miniPetHead.getItemMeta();
                miniPetHeadMeta.setOwner(petSkullOwner);
                miniPetHead.setItemMeta(miniPetHeadMeta);

                miniPetStand.setHelmet(miniPetHead);

                CosmeticsPlugin.activeMiniPets.put(playerUUID, miniPetStand);
                player.sendMessage(ChatColor.GOLD + "Wlaczono mini peta: " + clickedItem.getItemMeta().getDisplayName() + ChatColor.GOLD + "!");
            } else {
                player.sendMessage(ChatColor.YELLOW + "Nie wybrano zadnego mini peta.");
            }
            player.closeInventory();
        }
        // OBSŁUGA KLIKNIĘĆ W MENU "PRAWDZIWYCH" PETÓW (bez zmian)
        else if (event.getClickedInventory().getTitle().equals(ChatColor.AQUA + "Wybierz Peta")) {
            event.setCancelled(true);

            if (clickedItem == null || clickedItem.getType() == Material.AIR) {
                return;
            }

            UUID playerUUID = player.getUniqueId();

            if (CosmeticsPlugin.activePets.containsKey(playerUUID) && CosmeticsPlugin.activePets.get(playerUUID) != null) {
                LivingEntity oldPet = CosmeticsPlugin.activePets.get(playerUUID);
                if (oldPet != null && !oldPet.isDead()) {
                    oldPet.remove();
                }
                CosmeticsPlugin.activePets.remove(playerUUID);
            }

            if (clickedItem.getType() == Material.BARRIER) {
                player.sendMessage(ChatColor.RED + "Pet zostal WYLACZONY.");
                player.closeInventory();
                return;
            }

            EntityType chosenPetType = null;
            if (clickedItem.getType() == Material.EGG) {
                chosenPetType = EntityType.CHICKEN;
            } else if (clickedItem.getType() == Material.PORK) {
                chosenPetType = EntityType.PIG;
            } else if (clickedItem.getType() == Material.BONE) {
                chosenPetType = EntityType.WOLF;
            }

            if (chosenPetType != null) {
                Location spawnLoc = player.getLocation().add(0, 0.5, 0);
                LivingEntity pet = (LivingEntity) player.getWorld().spawnEntity(spawnLoc, chosenPetType);

                if (pet instanceof Tameable) {
                    Tameable tameablePet = (Tameable) pet;
                    tameablePet.setTamed(true);
                    tameablePet.setOwner(player);
                }

                pet.setCustomName(player.getName() + "'s " + clickedItem.getItemMeta().getDisplayName());
                pet.setCustomNameVisible(true);

                CosmeticsPlugin.activePets.put(playerUUID, pet);
                player.sendMessage(ChatColor.AQUA + "Wlaczono peta: " + clickedItem.getItemMeta().getDisplayName() + ChatColor.AQUA + "!");
            } else {
                player.sendMessage(ChatColor.YELLOW + "Nie wybrano zadnego peta.");
            }
            player.closeInventory();
        }
        // OBSŁUGA KLIKNIĘĆ W MENU SKRZYDEŁ - TERAZ CZĄSTECZKI
        else if (event.getClickedInventory().getTitle().equals(ChatColor.LIGHT_PURPLE + "Wybierz Skrzydla")) {
            event.setCancelled(true);

            if (clickedItem == null || clickedItem.getType() == Material.AIR) {
                return;
            }

            UUID playerUUID = player.getUniqueId();

            // Sprawdzamy, czy gracz kliknął przycisk "Wyłącz Skrzydła"
            if (clickedItem.getType() == Material.BARRIER) {
                CosmeticsPlugin.activeWings.remove(playerUUID);
                player.sendMessage(ChatColor.RED + "Skrzydla zostaly WYLACZONE.");
                player.closeInventory();
                return;
            }

            Effect chosenWingEffect = null;
            if (clickedItem.getType() == Material.FEATHER) {
                chosenWingEffect = Effect.FIREWORKS_SPARK; // Białe iskrzące skrzydła
            } else if (clickedItem.getType() == Material.COAL) {
                chosenWingEffect = Effect.SMOKE; // Czarne dymne skrzydła
            } else if (clickedItem.getType() == Material.BLAZE_ROD) {
                chosenWingEffect = Effect.FLAME; // Ogniste skrzydła smoka
            } else if (clickedItem.getType() == Material.ENDER_STONE) { // <-- ZMIANA TYPU MATERIALU NA ENDER_STONE
                chosenWingEffect = Effect.ENDER_SIGNAL; // <-- ZMIANA EFEKTU NA ENDER_SIGNAL dla fioletu
            }

            if (chosenWingEffect != null) {
                CosmeticsPlugin.activeWings.put(playerUUID, chosenWingEffect);
                player.sendMessage(ChatColor.LIGHT_PURPLE + "Wlaczono skrzydla: " + clickedItem.getItemMeta().getDisplayName() + ChatColor.LIGHT_PURPLE + "!");
            } else {
                player.sendMessage(ChatColor.YELLOW + "Nie wybrano zadnych skrzydel.");
            }
            player.closeInventory();
        }
    }

    // Obsługa rozłączenia gracza (aby kosmetyki nie zostawały w świecie)
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        // Usuń Mini Peta
        if (CosmeticsPlugin.activeMiniPets.containsKey(playerUUID)) {
            ArmorStand miniPet = CosmeticsPlugin.activeMiniPets.get(playerUUID);
            if (miniPet != null && !miniPet.isDead()) {
                miniPet.remove();
            }
            CosmeticsPlugin.activeMiniPets.remove(playerUUID);
        }

        // Usuń "prawdziwego" Peta
        if (CosmeticsPlugin.activePets.containsKey(playerUUID)) {
            LivingEntity pet = CosmeticsPlugin.activePets.get(playerUUID);
            if (pet != null && !pet.isDead()) {
                pet.remove();
            }
            CosmeticsPlugin.activePets.remove(playerUUID);
        }

        // Usuń Skrzydla - już nie trzeba usuwać ArmorStandów
        if (CosmeticsPlugin.activeWings.containsKey(playerUUID)) {
            CosmeticsPlugin.activeWings.remove(playerUUID);
        }
    }
}