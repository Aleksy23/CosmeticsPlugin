package com.Aleksy23.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class CosmeticsMenu {

    public static Inventory createMenu() {
        Inventory gui = Bukkit.createInventory(null, 9, ChatColor.DARK_AQUA + "Menu Kosmetykow");

        // Mini Pets
        ItemStack miniPetItem = new ItemStack(Material.MONSTER_EGG);
        ItemMeta miniPetMeta = miniPetItem.getItemMeta();
        miniPetMeta.setDisplayName(ChatColor.GREEN + "Mini Pets");
        miniPetMeta.setLore(Collections.singletonList(ChatColor.GRAY + "Kliknij, aby wybrac malego peta!"));
        miniPetItem.setItemMeta(miniPetMeta);
        gui.setItem(1, miniPetItem);

        // Pets (prawdziwe moby)
        ItemStack petItem = new ItemStack(Material.LEASH);
        ItemMeta petMeta = petItem.getItemMeta();
        petMeta.setDisplayName(ChatColor.AQUA + "Pets");
        petMeta.setLore(Collections.singletonList(ChatColor.GRAY + "Kliknij, aby wybrac prawdziwego peta!"));
        petItem.setItemMeta(petMeta);
        gui.setItem(3, petItem);

        // Skrzydła
        ItemStack wingsItem = new ItemStack(Material.FEATHER);
        ItemMeta wingsMeta = wingsItem.getItemMeta();
        wingsMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Skrzydla");
        wingsMeta.setLore(Collections.singletonList(ChatColor.GRAY + "Dodaj skrzydla swojemu graczowi!"));
        wingsItem.setItemMeta(wingsMeta);
        gui.setItem(5, wingsItem); // <-- Upewnij się, że jest na slocie 5

        // Particle
        ItemStack particleItem = new ItemStack(Material.SULPHUR);
        ItemMeta particleMeta = particleItem.getItemMeta();
        particleMeta.setDisplayName(ChatColor.AQUA + "Particle");
        particleMeta.setLore(Collections.singletonList(ChatColor.GRAY + "Rozswietl swoja obecnosc!"));
        particleItem.setItemMeta(particleMeta);
        gui.setItem(7, particleItem);

        return gui;
    }

    public static void openMenu(Player player) {
        player.openInventory(createMenu());
    }
}