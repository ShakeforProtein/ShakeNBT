package me.shakeforprotein.shakenbt.Listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;


public class onHoe implements Listener {

    @EventHandler
    public void onPlayerHoe(PlayerInteractEvent e) {
        if (e.getPlayer().getLocation().getWorld().getName().toLowerCase().contains("hardcore")) {
            if (e.getAction() == Action.LEFT_CLICK_BLOCK && e.getItem() != null && e.getItem().getType() == Material.WOODEN_HOE) {
                e.setCancelled(true);
                String type = e.getClickedBlock().getType().name().toLowerCase();
                String[] typeList = type.split("_");
                String newNewType = "";
                for (String newType : typeList) {
                    newType = newType.substring(0, 1).toUpperCase() + newType.substring(1);
                    if (newNewType.equals("")) {
                        newNewType = newType;
                    } else {
                        newNewType = newNewType + "_" + newType;
                    }
                }
                e.getPlayer().sendMessage("https://minecraft.gamepedia.com/" + newNewType);
            }
        }
    }

    @EventHandler
    public void onPlayerHoe(PlayerInteractAtEntityEvent e) {
        if (e.getPlayer().getLocation().getWorld().getName().toLowerCase().contains("hardcore")) {
            if (e.getPlayer().getInventory().getItemInMainHand().getType() == Material.WOODEN_HOE && e.getHand() == EquipmentSlot.HAND) {
                e.setCancelled(true);
                String type = e.getRightClicked().getType().name().toLowerCase();
                String[] typeList = type.split("_");
                String newNewType = "";
                for (String newType : typeList) {
                    newType = newType.substring(0, 1).toUpperCase() + newType.substring(1);
                    if (newNewType.equals("")) {
                        newNewType = newType;
                    } else {
                        newNewType = newNewType + "_" + newType;
                    }
                }
                e.getPlayer().sendMessage("https://minecraft.gamepedia.com/" + newNewType);
            }
        }
    }
}
