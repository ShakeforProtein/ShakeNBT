package me.shakeforprotein.shakenbt.Listeners;

import me.shakeforprotein.shakenbt.ShakeNBT;
import net.minecraft.server.v1_15_R1.MinecraftServer;
import net.minecraft.server.v1_15_R1.NBTTagCompound;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class onBlockBreak implements Listener {
    private ShakeNBT pl;

    public onBlockBreak(ShakeNBT main) {
        this.pl = main;
    }

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent e) {
        Player p = e.getPlayer();
        ItemStack mHI = p.getInventory().getItemInMainHand();
        if (mHI.getType() != Material.AIR && (mHI.getType().name().toUpperCase().contains("_PICKAXE") || mHI.getType().name().toUpperCase().contains("_AXE"))) {

            ItemStack bukkitItem = p.getInventory().getItemInMainHand();
            net.minecraft.server.v1_15_R1.ItemStack nmsItem = pl.getNMSItem(bukkitItem);
            NBTTagCompound compound = pl.getCompound(nmsItem);
            Set<String> compoundKeys = compound.getKeys();
            if (compoundKeys.contains("ShakeEnchant")) {
                String ench = compound.get("ShakeEnchant").asString();
                if (ench.equalsIgnoreCase("Smelt")) {
                    Collection<ItemStack> drops = e.getBlock().getDrops();
                    Collection<ItemStack> newDrops = new ArrayList<>();
                    for (ItemStack drop : drops) {
                        if (mHI.getType().name().toUpperCase().contains("_PICKAXE")) {
                            if (drop.getType().name().toUpperCase().contains("COBBLESTONE")) {
                                newDrops.add(new ItemStack(Material.STONE, drop.getAmount()));
                            } else if (drop.getType().name().toUpperCase().contains("IRON_ORE")) {
                                newDrops.add(new ItemStack(Material.IRON_INGOT, drop.getAmount()));
                            } else if (drop.getType().name().toUpperCase().contains("GOLD_ORE")) {
                                newDrops.add(new ItemStack(Material.GOLD_INGOT, drop.getAmount()));
                            } else if (drop.getType().name().toUpperCase().contains("DIAMOND_ORE")) {
                                newDrops.add(new ItemStack(Material.DIAMOND, drop.getAmount()));
                            } else if (drop.getType().name().toUpperCase().contains("EMERALD_ORE")) {
                                newDrops.add(new ItemStack(Material.EMERALD, drop.getAmount()));
                            } else if (drop.getType().name().toUpperCase().contains("REDSTONE_ORE")) {
                                newDrops.add(new ItemStack(Material.REDSTONE, drop.getAmount()));
                            } else if (drop.getType().name().toUpperCase().contains("COAL_ORE")) {
                                newDrops.add(new ItemStack(Material.COAL, drop.getAmount()));
                            } else if (drop.getType().name().toUpperCase().contains("NETHER_QUARTZ_ORE")) {
                                newDrops.add(new ItemStack(Material.QUARTZ, drop.getAmount()));
                            } else if (drop.getType().name().toUpperCase().contains("WET_SPONGE")) {
                                newDrops.add(new ItemStack(Material.SPONGE, drop.getAmount()));
                            } else if (drop.getType().name().toUpperCase().contains("NETHERRACK")) {
                                newDrops.add(new ItemStack(Material.NETHER_BRICK, drop.getAmount()));
                            } else if (drop.getType().name().toUpperCase().contains("LAPIS_ORE")) {
                                newDrops.add(new ItemStack(Material.LAPIS_LAZULI, drop.getAmount()));
                            }

                        } else if (mHI.getType().name().toUpperCase().contains("_AXE")) {
                            if (drop.getType().name().toUpperCase().contains("WOOD")) {
                                newDrops.add(new ItemStack(Material.CHARCOAL, drop.getAmount()));
                            } else if (drop.getType().name().toUpperCase().contains("LOG")) {
                                newDrops.add(new ItemStack(Material.CHARCOAL, drop.getAmount()));
                            } else if (drop.getType().name().toUpperCase().contains("CACTUS")) {
                                newDrops.add(new ItemStack(Material.GREEN_DYE, drop.getAmount()));
                            }
                        } else if (mHI.getType().name().toUpperCase().contains("_SHOVEL")) {
                            if (drop.getType().name().toUpperCase().contains("SAND")) {
                                newDrops.add(new ItemStack(Material.GLASS, drop.getAmount()));
                            } else if (drop.getType().name().toUpperCase().contains("GRAVEL")) {
                                newDrops.add(new ItemStack(Material.FLINT, drop.getAmount()));
                            } else if (drop.getType().name().toUpperCase().contains("CLAY_BALL")) {
                                newDrops.add(new ItemStack(Material.BRICK, drop.getAmount()));
                            }
                        } else {
                            newDrops.add(drop);
                        }
                    }
                    if (!newDrops.isEmpty()) {
                        drops.clear();
                        drops.addAll(newDrops);
                        for (ItemStack drop : drops) {
                            e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), drop);
                        }
                        e.getBlock().setType(Material.AIR);
                    }
                } else if (ench.equalsIgnoreCase("+Size")) {
                    Location loc = e.getBlock().getLocation();
                    HashMap<Integer, Location> locationHashmap = new HashMap<>();
                    int i, j, k, l = 0;
                    for (i = 0; i < 3; i++) {
                        for (j = 0; j < 3; j++) {
                            for (k = 0; k < 3; k++) {
                                locationHashmap.put(l, loc.add(i - 1, j - 1, k - 1));
                                l++;
                            }
                        }
                    }
                    for (i = 0; i < l + 1; i++) {
                        if (locationHashmap.get(i).getBlock().getType().equals(loc.getBlock().getType())) {
                            locationHashmap.get(i).getBlock().breakNaturally(mHI);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void witherBreakBlocks(EntityExplodeEvent e) {
        double[] tps = MinecraftServer.getServer().recentTps;
        if (tps[0] < 18) {
            if (e.getEntity().getType().name().toUpperCase().contains("WITHER")) {
                e.setCancelled(true);
                for (Block b : e.blockList()) {
                    b.setType(Material.AIR);
                }
            }
        }
    }

    @EventHandler
    public void witherBreakBlocks2(EntityChangeBlockEvent e) {
        double[] tps = MinecraftServer.getServer().recentTps;
        if (tps[0] < 18) {
            if (e.getEntity().getType().name().toUpperCase().contains("WITHER")) {
                e.setCancelled(true);
                e.getBlock().setType(Material.AIR);
            }
        }
    }

}
