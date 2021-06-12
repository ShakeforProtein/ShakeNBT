package me.shakeforprotein.shakenbt.Commands;

import me.shakeforprotein.shakenbt.ShakeNBT;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;

public class ClearNBT implements CommandExecutor {

    private ShakeNBT pl;

    public ClearNBT(ShakeNBT main) {
        this.pl = main;
    }

/*    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.getInventory().getItemInMainHand() != null && p.getInventory().getItemInMainHand().getType() != Material.AIR) {
                ItemStack bukkitItem = p.getInventory().getItemInMainHand();
                net.minecraft.server.v1_16_R3.ItemStack nmsItem = pl.getNMSItem(bukkitItem);
                //NBTTagCompound compound = pl.getCompound(nmsItem);
                //Set<String> compoundKeys = compound.getKeys();
                NBTTagCompound blankCompound = new NBTTagCompound();
                nmsItem.setTag(blankCompound);
                p.getInventory().setItemInMainHand(pl.getBukkitItem(nmsItem));
            } else {
                p.sendMessage("You must have an item in your main hand");
            }
        } else {
            sender.sendMessage("This plugin can only be run as a player");
        }

        return true;
    }

 */

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.getInventory().getItemInMainHand() != null && p.getInventory().getItemInMainHand().getType() != Material.AIR && p.getInventory().getItemInMainHand().hasItemMeta()) {
                ItemStack bukkitItem = p.getInventory().getItemInMainHand();
                PersistentDataContainer itemNBT = bukkitItem.getItemMeta().getPersistentDataContainer();
                for (NamespacedKey key : itemNBT.getKeys()) {
                    itemNBT.remove(key);
                }
            } else {
                p.sendMessage(pl.err + "No item in your main hand / Item has no nbt to clear");
            }
        } else {
            sender.sendMessage("This plugin can only be run as a player");
        }

        return true;
    }
}