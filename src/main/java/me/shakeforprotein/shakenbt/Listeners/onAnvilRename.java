package me.shakeforprotein.shakenbt.Listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;

public class onAnvilRename implements Listener {

    @EventHandler
    public void onAnvilRenameEgg(PrepareAnvilEvent e){
        if(e.getResult().getType() == Material.VILLAGER_SPAWN_EGG && e.getInventory().getRenameText() != null){
            e.setResult(new ItemStack(Material.AIR, 1));
        }
    }
}
