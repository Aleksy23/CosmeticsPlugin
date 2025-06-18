package com.Aleksy23.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class WingsMenu {

    public static Inventory createMenu() {
        Inventory gui = Bukkit.createInventory(null, 9, ChatColor.LIGHT_PURPLE + "Wybierz Skrzydla");

        // Opcja 1: Anielskie skrzydła (iskry) - Biale
        ItemStack angelWings = new ItemStack(Material.FEATHER);
        ItemMeta angelMeta = angelWings.getItemMeta();
        angelMeta.setDisplayName(ChatColor.WHITE + "Anielskie Skrzydla");
        angelMeta.setLore(Collections.singletonList(ChatColor.GRAY + "Iskrzace biale skrzydla anioła."));
        angelWings.setItemMeta(angelMeta);
        gui.setItem(1, angelWings);

        // Opcja 2: Mroczne skrzydła (dym) - Czarne
        ItemStack darkWings = new ItemStack(Material.COAL);
        ItemMeta darkMeta = darkWings.getItemMeta();
        darkMeta.setDisplayName(ChatColor.DARK_GRAY + "Mroczne Skrzydla");
        darkMeta.setLore(Collections.singletonList(ChatColor.GRAY + "Dymne, mroczne skrzydla."));
        darkWings.setItemMeta(darkMeta);
        gui.setItem(3, darkWings);

        // Opcja 3: Ogniste skrzydła smoka - Czerwone/Pomarańczowe
        ItemStack dragonWings = new ItemStack(Material.BLAZE_ROD);
        ItemMeta dragonMeta = dragonWings.getItemMeta();
        dragonMeta.setDisplayName(ChatColor.GOLD + "Smocze Skrzydla");
        dragonMeta.setLore(Collections.singletonList(ChatColor.GRAY + "Plonace skrzydła smoka."));
        dragonWings.setItemMeta(dragonMeta);
        gui.setItem(5, dragonWings);

        // Opcja 4: Endermanowe Skrzydla (fioletowe/purpurowe) - NOWY EFEKT/IKONA
        ItemStack enderWings = new ItemStack(Material.ENDER_STONE); // End Stone dobrze pasuje do tematyki Endermana
        ItemMeta enderMeta = enderWings.getItemMeta();
        enderMeta.setDisplayName(ChatColor.DARK_PURPLE + "Endermanowe Skrzydla");
        enderMeta.setLore(Collections.singletonList(ChatColor.GRAY + "Mroczne skrzydła Endermana."));
        enderWings.setItemMeta(enderMeta);
        gui.setItem(7, enderWings);

        // Opcja wyłączania skrzydeł
        ItemStack disableWings = new ItemStack(Material.BARRIER);
        ItemMeta disableMeta = disableWings.getItemMeta();
        disableMeta.setDisplayName(ChatColor.RED + "Wylacz Skrzydla");
        disableMeta.setLore(Collections.singletonList(ChatColor.GRAY + "Kliknij, aby usunac obecne skrzydla."));
        disableWings.setItemMeta(disableMeta);
        gui.setItem(8, disableWings);

        return gui;
    }

    public static void openMenu(Player player) {
        player.openInventory(createMenu());
    }
}