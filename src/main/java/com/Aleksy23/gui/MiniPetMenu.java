package com.Aleksy23.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.SkullMeta; // Import dla głów graczy/moba
import java.util.Collections;

public class MiniPetMenu {

    public static Inventory createMenu() {
        Inventory gui = Bukkit.createInventory(null, 9, ChatColor.GOLD + "Wybierz Mini Peta"); // Zmieniono tytuł

        // Opcja 1: Kurczaczek
        ItemStack chickenPet = new ItemStack(Material.SKULL_ITEM, 1, (short) 3); // Typ 3 dla głowy gracza
        SkullMeta chickenMeta = (SkullMeta) chickenPet.getItemMeta();
        chickenMeta.setDisplayName(ChatColor.YELLOW + "Kurczaczek");
        chickenMeta.setOwner("MHF_Chicken"); // Nick gracza, którego głowę chcemy
        chickenMeta.setLore(Collections.singletonList(ChatColor.GRAY + "Slodki kurczaczek bedzie za Toba podazal."));
        chickenPet.setItemMeta(chickenMeta);
        gui.setItem(2, chickenPet);

        // Opcja 2: Swinka
        ItemStack pigPet = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta pigMeta = (SkullMeta) pigPet.getItemMeta();
        pigMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Swinka");
        pigMeta.setOwner("MHF_Pig");
        pigMeta.setLore(Collections.singletonList(ChatColor.GRAY + "Rozkoszna swinka dla kazdego."));
        pigPet.setItemMeta(pigMeta);
        gui.setItem(4, pigPet);

        // Opcja 3: Wilk
        ItemStack wolfPet = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta wolfMeta = (SkullMeta) wolfPet.getItemMeta();
        wolfMeta.setDisplayName(ChatColor.DARK_GRAY + "Wilk");
        wolfMeta.setOwner("MHF_Wolf");
        wolfMeta.setLore(Collections.singletonList(ChatColor.GRAY + "Wierny wilk u Twojego boku."));
        wolfPet.setItemMeta(wolfMeta);
        gui.setItem(6, wolfPet);

        // Opcja wyłączania peta
        ItemStack disablePet = new ItemStack(Material.BARRIER);
        ItemMeta disableMeta = disablePet.getItemMeta();
        disableMeta.setDisplayName(ChatColor.RED + "Wylacz Peta");
        disableMeta.setLore(Collections.singletonList(ChatColor.GRAY + "Kliknij, aby usunac obecnego peta."));
        disablePet.setItemMeta(disableMeta);
        gui.setItem(8, disablePet); // Ostatni slot

        return gui;
    }

    public static void openMenu(Player player) {
        player.openInventory(createMenu());
    }
}