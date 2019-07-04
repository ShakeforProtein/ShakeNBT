package me.shakeforprotein.shakenbt.Commands;

import me.shakeforprotein.shakenbt.ShakeNBT;
import net.minecraft.server.v1_14_R1.NBTTagCompound;
import net.minecraft.server.v1_14_R1.NBTTagString;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_14_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public class AddNBT implements CommandExecutor {

    private ShakeNBT pl;

    public AddNBT(ShakeNBT main) {
        this.pl = main;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender instanceof Player){
            Player p = (Player) sender;
            if(p.getInventory().getItemInMainHand() != null && p.getInventory().getItemInMainHand().getType() != Material.AIR) {
                ItemStack bukkitItem = p.getInventory().getItemInMainHand();
                net.minecraft.server.v1_14_R1.ItemStack nmsItem = pl.getNMSItem(bukkitItem);
                NBTTagCompound compound = pl.getCompound(nmsItem);
                Set<String> compoundKeys = compound.getKeys();
                StringBuilder sb = new StringBuilder();
                int i = 0;
                for(i=1; i<args.length; i++){
                    sb.append(args[i]);
                }
                String newNBTItem = sb.toString();
                compound.set(args[0], new NBTTagString(newNBTItem));
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
