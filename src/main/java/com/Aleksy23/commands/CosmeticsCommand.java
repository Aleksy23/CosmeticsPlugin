package com.Aleksy23.commands;

import com.Aleksy23.gui.CosmeticsMenu; // Importuj nową klasę GUI
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;

public class CosmeticsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Tylko gracze moga uzyc tej komendy!");
            return true;
        }

        Player player = (Player) sender;

        // Zamiast wysyłać wiadomość, otwieramy menu kosmetyków
        CosmeticsMenu.openMenu(player);
        player.sendMessage(ChatColor.GREEN + "Otworzono menu kosmetykow!");

        return true;
    }
}