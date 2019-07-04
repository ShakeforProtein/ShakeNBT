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

public class NameIt implements CommandExecutor {

    private ShakeNBT pl;

    public NameIt(ShakeNBT main) {
        this.pl = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            if (((Player) sender).getInventory().getItemInMainHand() != null && ((Player) sender).getInventory().getItemInMainHand().getType() != Material.AIR) {
                StringBuilder fullText = new StringBuilder();
                int i;

                for (i = 0; i < args.length; i++) {
                    fullText.append(args[i] + " ");
                }

                String theText = fullText.toString().trim();
                theText = ChatColor.translateAlternateColorCodes('&', theText);

                ItemStack item = ((Player) sender).getInventory().getItemInMainHand();
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName(theText);
                item.setItemMeta(meta);
                ((Player) sender).getInventory().setItemInMainHand(item);
            }
            else{
                sender.sendMessage(pl.err + "You require an item in your main hand for this command.");
            }
        } else {
            sender.sendMessage(pl.err + "This command can only run as a player");
        }
        return true;
    }

}
