package com.Aleksy23.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.Collections;

public class PetMenu { // NOWA KLASA

    public static Inventory createMenu() {
        Inventory gui = Bukkit.createInventory(null, 9, ChatColor.AQUA + "Wybierz Peta"); // Tytuł dla prawdziwych petów

        // Opcja 1: Kurczak
        ItemStack chickenPet = new ItemStack(Material.EGG); // Jajko jako ikona
        ItemMeta chickenMeta = chickenPet.getItemMeta();
        chickenMeta.setDisplayName(ChatColor.YELLOW + "Kurczak");
        chickenMeta.setLore(Collections.singletonList(ChatColor.GRAY + "Normalny kurczak podazajacy za Toba."));
        chickenPet.setItemMeta(chickenMeta);
        gui.setItem(2, chickenPet);

        // Opcja 2: Świnia
        ItemStack pigPet = new ItemStack(Material.PORK); // Surowa wieprzowina jako ikona
        ItemMeta pigMeta = pigPet.getItemMeta();
        pigMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Swinka");
        pigMeta.setLore(Collections.singletonList(ChatColor.GRAY + "Normalna swinka podazajaca za Toba."));
        pigPet.setItemMeta(pigMeta);
        gui.setItem(4, pigPet);

        // Opcja 3: Wilk
        ItemStack wolfPet = new ItemStack(Material.BONE); // Kość jako ikona
        ItemMeta wolfMeta = wolfPet.getItemMeta();
        wolfMeta.setDisplayName(ChatColor.DARK_GRAY + "Wilk");
        wolfMeta.setLore(Collections.singletonList(ChatColor.GRAY + "Normalny wilk podazajacy za Toba."));
        wolfPet.setItemMeta(wolfMeta);
        gui.setItem(6, wolfPet);

        // Opcja wyłączania peta
        ItemStack disablePet = new ItemStack(Material.BARRIER);
        ItemMeta disableMeta = disablePet.getItemMeta();
        disableMeta.setDisplayName(ChatColor.RED + "Wylacz Peta");
        disableMeta.setLore(Collections.singletonList(ChatColor.GRAY + "Kliknij, aby usunac obecnego peta."));
        disablePet.setItemMeta(disableMeta);
        gui.setItem(8, disablePet);

        return gui;
    }

    public static void openMenu(Player player) {
        player.openInventory(createMenu());
    }
}