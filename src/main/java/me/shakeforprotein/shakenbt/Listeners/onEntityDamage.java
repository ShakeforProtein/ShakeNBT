package me.shakeforprotein.shakenbt.Listeners;

import me.shakeforprotein.shakenbt.ShakeNBT;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.VillagerCareerChangeEvent;

public class onEntityDamage implements Listener {

    private ShakeNBT pl;

    public  onEntityDamage(ShakeNBT main){
        this.pl = main;
    }

    private void onHitVillager(VillagerCareerChangeEvent e){
        if(!((Villager) e.getEntity()).getProfession().equals(Villager.Profession.NITWIT) && ((Villager) e.getEntity()).getProfession().equals(Villager.Profession.NONE) && ((Villager)e.getEntity()).getRecipeCount() == 0){
            e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), e.getEntityType());
            e.getEntity().remove();
        }
    }
}
