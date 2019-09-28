package me.shakeforprotein.shakenbt;

import me.shakeforprotein.shakenbt.Commands.*;
import me.shakeforprotein.shakenbt.Listeners.onBlockBreak;
import net.minecraft.server.v1_14_R1.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_14_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public final class ShakeNBT extends JavaPlugin {


    public String badge = ChatColor.translateAlternateColorCodes('&', getConfig().getString("general.messages.badge") + " ");
    public String err = badge + ChatColor.translateAlternateColorCodes('&', getConfig().getString("general.messages.error") + " ");
    private UpdateChecker uc = new UpdateChecker(this);
    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getCommand("nbtadd").setExecutor(new AddNBT(this));
        this.getCommand("nbtclear").setExecutor(new ClearNBT(this));
        this.getCommand("nbtremove").setExecutor(new RemoveNBT(this));
        this.getCommand("nbtset").setExecutor(new SetNBT(this));
        this.getCommand("nbtshow").setExecutor(new ShowNBT(this));
        this.getCommand("nameit").setExecutor(new NameIt(this));
        this.getCommand("loreit").setExecutor(new LoreIt(this));
        //uc.getCheckDownloadURL();
        Bukkit.getPluginManager().registerEvents(new onBlockBreak(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


    public net.minecraft.server.v1_14_R1.ItemStack  getNMSItem(ItemStack item){
        net.minecraft.server.v1_14_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        return nmsItem;
    }

    public NBTTagCompound getCompound(net.minecraft.server.v1_14_R1.ItemStack nmsItem){
        NBTTagCompound nmsCompound = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();
        return nmsCompound;
    }

    public ItemStack getBukkitItem(net.minecraft.server.v1_14_R1.ItemStack nmsItem){
        ItemStack bukkitItem = CraftItemStack.asBukkitCopy(nmsItem);
        return bukkitItem;
    }
}



