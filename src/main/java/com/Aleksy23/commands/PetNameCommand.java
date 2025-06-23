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

        // Sprawdź uprawnienia
        if (!player.hasPermission("cosmetics.petname")) {
            player.sendMessage(ChatColor.RED + "Nie masz uprawnień do tej komendy!");
            return true;
        }

        // Sprawdź czy gracz ma aktywnego peta
        LivingEntity pet = CosmeticsPlugin.activePets.get(playerUUID);
        if (pet == null || pet.isDead()) {
            player.sendMessage(ChatColor.RED + "Nie masz aktywnego peta!");
            return true;
        }

        if (args.length == 0) {
            player.sendMessage(ChatColor.RED + "Użycie: /petname <nazwa>");
            return true;
        }

        String newName = String.join(" ", args);
        
        // Walidacja nazwy
        if (newName.length() > 32) {
            player.sendMessage(ChatColor.RED + "Nazwa nie może być dłuższa niż 32 znaki!");
            return true;
        }
        
        // Filtruj nielegalne znaki
        if (!newName.matches("^[a-zA-Z0-9&§ ]+$")) {
            player.sendMessage(ChatColor.RED + "Nazwa zawiera niedozwolone znaki!");
            return true;
        }

        String formattedName = ChatColor.translateAlternateColorCodes('&', newName);
        pet.setCustomName(formattedName);
        pet.setCustomNameVisible(true);
        
        player.sendMessage(ChatColor.GREEN + "Nazwano peta: " + formattedName);
        return true;
    }
}
