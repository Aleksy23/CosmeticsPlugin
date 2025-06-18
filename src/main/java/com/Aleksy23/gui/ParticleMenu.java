package com.Aleksy23.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class ParticleMenu {

    public static Inventory createMenu() {
        Inventory gui = Bukkit.createInventory(null, 18, ChatColor.DARK_BLUE + "Wybierz Efekt Particle");

        // Opcja 1: Serduszka (REDSTONE)
        ItemStack heartsItem = new ItemStack(Material.REDSTONE);
        ItemMeta heartsMeta = heartsItem.getItemMeta();
        heartsMeta.setDisplayName(ChatColor.RED + "Serduszka");
        heartsMeta.setLore(Collections.singletonList(ChatColor.GRAY + "Urocze serduszka beda Cie otaczac."));
        heartsItem.setItemMeta(heartsMeta);
        gui.setItem(2, heartsItem);

        // Opcja 2: Dym (SULPHUR)
        ItemStack smokeItem = new ItemStack(Material.SULPHUR);
        ItemMeta smokeMeta = smokeItem.getItemMeta();
        smokeMeta.setDisplayName(ChatColor.GRAY + "Dym");
        smokeMeta.setLore(Collections.singletonList(ChatColor.GRAY + "Tajemiczy dym bedzie za Toba podazal."));
        smokeItem.setItemMeta(smokeMeta);
        gui.setItem(4, smokeItem);

        // Opcja 3: Iskry (Crit - GLOWSTONE_DUST)
        ItemStack critItem = new ItemStack(Material.GLOWSTONE_DUST);
        ItemMeta critMeta = critItem.getItemMeta();
        critMeta.setDisplayName(ChatColor.YELLOW + "Iskry (Crit)");
        critMeta.setLore(Collections.singletonList(ChatColor.GRAY + "Rozswietl swoja droge iskrami! (jak krytyczne uderzenie)"));
        critItem.setItemMeta(critMeta);
        gui.setItem(6, critItem);

        // --- NOWE OPCJE ---

        // Opcja 4: Ender Rod (NETHER_STAR)
        ItemStack enderRodItem = new ItemStack(Material.NETHER_STAR);
        ItemMeta enderRodMeta = enderRodItem.getItemMeta();
        enderRodMeta.setDisplayName(ChatColor.DARK_PURPLE + "Kresowy Blask (Ender)");
        enderRodMeta.setLore(Collections.singletonList(ChatColor.GRAY + "Mocna aura z Kresu."));
        enderRodItem.setItemMeta(enderRodMeta);
        gui.setItem(10, enderRodItem);

        // Opcja 5: Tęcza (DIAMOND - specjalna obsługa)
        ItemStack rainbowItem = new ItemStack(Material.DIAMOND);
        ItemMeta rainbowMeta = rainbowItem.getItemMeta();
        rainbowMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "TECZA!");
        rainbowMeta.setLore(Collections.singletonList(ChatColor.GRAY + "Kolorowy efekt teczy."));
        rainbowItem.setItemMeta(rainbowMeta);
        gui.setItem(12, rainbowItem);

        // Opcja 6: Płomień (FIREBALL - MOBSPAWNER_FLAMES)
        ItemStack flameItem = new ItemStack(Material.FIREBALL);
        ItemMeta flameMeta = flameItem.getItemMeta();
        flameMeta.setDisplayName(ChatColor.GOLD + "Plomien");
        flameMeta.setLore(Collections.singletonList(ChatColor.GRAY + "Dookola Ciebie bedzie tanczyl ogien."));
        flameItem.setItemMeta(flameMeta);
        gui.setItem(14, flameItem);

        // Opcja 7: Bąbelki (PRISMARINE_SHARD - WATERDRIP)
        ItemStack bubbleItem = new ItemStack(Material.PRISMARINE_SHARD);
        ItemMeta bubbleMeta = bubbleItem.getItemMeta();
        bubbleMeta.setDisplayName(ChatColor.BLUE + "Babelek");
        bubbleMeta.setLore(Collections.singletonList(ChatColor.GRAY + "Wodne babelek! (jak spod wody)"));
        bubbleItem.setItemMeta(bubbleMeta);
        gui.setItem(8, bubbleItem);

        // Opcja 8: Śnieżki (SNOW_BALL - SNOW_SHOVEL)
        ItemStack snowItem = new ItemStack(Material.SNOW_BALL);
        ItemMeta snowMeta = snowItem.getItemMeta();
        snowMeta.setDisplayName(ChatColor.WHITE + "Sniezynki");
        snowMeta.setLore(Collections.singletonList(ChatColor.GRAY + "Lekkie sniezynki unosza sie wokol."));
        snowItem.setItemMeta(snowMeta);
        gui.setItem(16, snowItem);

        // Przycisk "Wyłącz" dla Particle
        ItemStack disableParticles = new ItemStack(Material.BARRIER);
        ItemMeta disableMeta = disableParticles.getItemMeta();
        disableMeta.setDisplayName(ChatColor.RED + "Wylacz Efekt");
        disableMeta.setLore(Collections.singletonList(ChatColor.GRAY + "Kliknij, aby wylaczyc aktywny efekt."));
        disableParticles.setItemMeta(disableMeta);
        gui.setItem(0, disableParticles); // Na przykład w lewym górnym rogu

        return gui;
    }

    public static void openMenu(Player player) {
        player.openInventory(createMenu());
    }
}