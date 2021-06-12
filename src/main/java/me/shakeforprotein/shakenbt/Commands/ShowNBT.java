package me.shakeforprotein.shakenbt.Commands;

import me.shakeforprotein.shakenbt.ShakeNBT;
import net.minecraft.nbt.NBTTagCompound;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public class ShowNBT  implements CommandExecutor {

    private ShakeNBT pl;

    public ShowNBT(ShakeNBT main) {
        this.pl = main;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender instanceof Player){
            Player p = (Player) sender;
            if(p.getInventory().getItemInMainHand() != null && p.getInventory().getItemInMainHand().getType() != Material.AIR) {
                ItemStack bukkitItem = p.getInventory().getItemInMainHand();
                net.minecraft.world.item.ItemStack nmsItem = pl.getNMSItem(bukkitItem);
                NBTTagCompound compound = pl.getCompound(nmsItem);
                Set<String> compoundKeys = compound.getKeys();
                p.sendMessage("Retrieving nbt");
                for(String item : compoundKeys){
                    p.sendMessage(ChatColor.stripColor(item + " = " + compound.get(item).asString()));
                }
            }
            else {p.sendMessage("You must have an item in your main hand");}
        }
        else{
            sender.sendMessage("This plugin can only be run as a player");
        }

        return true;
    }
}

