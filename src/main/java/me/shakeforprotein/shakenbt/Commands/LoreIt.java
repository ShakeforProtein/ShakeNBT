package me.shakeforprotein.shakenbt.Commands;

import me.shakeforprotein.shakenbt.ShakeNBT;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class LoreIt implements CommandExecutor {

    private ShakeNBT pl;

    public LoreIt(ShakeNBT main) {
        this.pl = main;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.getInventory().getItemInMainHand() != null && p.getInventory().getItemInMainHand().getType() != Material.AIR) {
                ItemStack bukkitItem = p.getInventory().getItemInMainHand();
                ItemMeta bukkitItemMeta = bukkitItem.getItemMeta();
                List<String> currentLore = new ArrayList<>();
                if (bukkitItemMeta.hasLore()) {
                    currentLore = bukkitItemMeta.getLore();
                }

                if (args.length > 0) {
                    if (args[0].equalsIgnoreCase("get")) {
                        p.sendMessage("Retrieving item Lore");
                        int i = 0;
                        for (String loreText : currentLore) {
                            p.sendMessage(i + " - " + loreText);
                            i++;
                        }
                    } else if (args[0].equalsIgnoreCase("clear")) {
                        p.sendMessage("Clearing Item Lore");
                        currentLore.clear();
                        bukkitItemMeta.setLore(currentLore);
                        bukkitItem.setItemMeta(bukkitItemMeta);
                        p.sendMessage("Lore Cleared");
                    } else if (args.length > 1) {
                        if (args[0].equalsIgnoreCase("set")) {
                            StringBuilder sb = new StringBuilder();
                            int i = 0;
                            for (i = 2; i < args.length; i++) {
                                sb.append(args[i] +" ");
                            }
                            if (Integer.parseInt(args[1]) > currentLore.size()) {
                                for (i = currentLore.size(); i < Integer.parseInt(args[1]) +1; i++) {
                                        currentLore.add(" ");
                                }
                            }
                            currentLore.set(Integer.parseInt(args[1]), (ChatColor.translateAlternateColorCodes('&', sb.toString())));
                            bukkitItemMeta.setLore(currentLore);
                            bukkitItem.setItemMeta(bukkitItemMeta);
                        } else if (args[0].equalsIgnoreCase("add")) {
                            StringBuilder sb = new StringBuilder();
                            int i = 0;
                            for (i = 1; i < args.length; i++) {
                                sb.append(args[i] + " ");
                            }
                            currentLore.add(ChatColor.translateAlternateColorCodes('&', sb.toString()));
                            bukkitItemMeta.setLore(currentLore);
                            bukkitItem.setItemMeta(bukkitItemMeta);
                        }
                    }
                } else {
                    p.sendMessage("This command requires arguments");
                }

            } else {
                p.sendMessage("You must have an item in your main hand");
            }
        } else {
            sender.sendMessage("This plugin can only be run as a player");
        }

        return true;
    }
}
