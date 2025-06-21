package com.Aleksy23.commands;

import com.Aleksy23.CosmeticsPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PetNameCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Ta komenda może być używana tylko przez graczy!");
            return true;
        }

        Player player = (Player) sender;
        UUID playerUUID = player.getUniqueId();

        // Sprawdź czy gracz ma aktywnego peta
        if (!CosmeticsPlugin.activePets.containsKey(playerUUID) || CosmeticsPlugin.activePets.get(playerUUID) == null) {
            player.sendMessage(ChatColor.RED + "Nie masz aktywnego peta! Wybierz peta używając /cosmetics");
            return true;
        }

        // Sprawdź czy podano nazwę
        if (args.length == 0) {
            player.sendMessage(ChatColor.RED + "Użycie: /petname <nazwa>");
            player.sendMessage(ChatColor.YELLOW + "Przykład: /petname Mój Ulubiony Pet");
            return true;
        }

        // Połącz wszystkie argumenty w jedną nazwę
        StringBuilder nameBuilder = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            nameBuilder.append(args[i]);
            if (i < args.length - 1) {
                nameBuilder.append(" ");
            }
        }
        String newName = nameBuilder.toString();

        // Sprawdź długość nazwy
        if (newName.length() > 32) {
            player.sendMessage(ChatColor.RED + "Nazwa peta nie może być dłuższa niż 32 znaki!");
            return true;
        }

        // Ustaw nową nazwę
        LivingEntity pet = CosmeticsPlugin.activePets.get(playerUUID);
        if (pet != null && !pet.isDead()) {
            // Formatuj nazwę z kolorami
            String formattedName = ChatColor.translateAlternateColorCodes('&', newName);
            pet.setCustomName(formattedName);
            pet.setCustomNameVisible(true);
            
            player.sendMessage(ChatColor.GREEN + "Nazwano peta: " + formattedName);
            player.sendMessage(ChatColor.GRAY + "Możesz używać kodów kolorów (&a, &c, &b itp.)");
        } else {
            player.sendMessage(ChatColor.RED + "Twój pet już nie istnieje! Wybierz nowego peta.");
            CosmeticsPlugin.activePets.remove(playerUUID);
        }

        return true;
    }
}
