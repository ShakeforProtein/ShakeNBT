package me.shakeforprotein.shakenbt.Commands;

import me.shakeforprotein.shakenbt.ShakeNBT;
import net.minecraft.server.v1_15_R1.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public class RemoveNBT  implements CommandExecutor {

    private ShakeNBT pl;

    public RemoveNBT(ShakeNBT main) {
        this.pl = main;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender instanceof Player){
            Player p = (Player) sender;
            if(p.getInventory().getItemInMainHand() != null && p.getInventory().getItemInMainHand().getType() != Material.AIR) {
                ItemStack bukkitItem = p.getInventory().getItemInMainHand();
                net.minecraft.server.v1_15_R1.ItemStack nmsItem = pl.getNMSItem(bukkitItem);
                NBTTagCompound compound = pl.getCompound(nmsItem);

                if(compound.hasKey(args[0])){
                    compound.remove(args[0]);
                }
                else{p.sendMessage("Key - " + args[0] + " - Not found on this iem");
                }

                nmsItem.setTag(compound);
                ItemStack newItem = CraftItemStack.asBukkitCopy(nmsItem);
                p.getInventory().setItemInMainHand(newItem);
            }
            else {p.sendMessage("You must have an item in your main hand");}
        }
        else{
            sender.sendMessage("This plugin can only be run as a player");
        }

        return true;
    }
}