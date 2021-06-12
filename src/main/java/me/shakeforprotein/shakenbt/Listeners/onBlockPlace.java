package me.shakeforprotein.shakenbt.Listeners;

import me.shakeforprotein.shakenbt.ShakeNBT;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Beacon;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Wall;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class onBlockPlace implements Listener {

    private ShakeNBT pl;
    private List<Material> walls = new ArrayList<>();
    private List<Location> placedWalls = new ArrayList<>();

    public onBlockPlace(ShakeNBT main) {
        this.pl = main;
        for (Material material : Material.values()) {
            if (material.name().toUpperCase().endsWith("WALL")) {
                walls.add(material);
            }
        }
    }

    @EventHandler
    private void onBlockPlace(BlockPlaceEvent e) {
        if (walls.contains(e.getItemInHand().getType())) {
            if (e.getItemInHand().hasItemMeta() && e.getItemInHand().getItemMeta().hasDisplayName()) {
                ItemStack item = e.getItemInHand();
                String name = item.getItemMeta().getDisplayName();
                if (name.toLowerCase().startsWith("vslab;")) {
                    Bukkit.getScheduler().runTaskLater(pl, new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 1; i < name.split(";").length; i++) {
                                if (name.split(";")[i].length() == 2) {
                                    String firstChar = name.split(";")[i].substring(0, 1).toUpperCase();
                                    String secondChar = name.split(";")[i].substring(1, 2).toUpperCase();


                                    BlockFace face = BlockFace.UP;
                                    Wall.Height height = Wall.Height.TALL;

                                    switch (firstChar) {

                                        case "N":
                                            face = BlockFace.NORTH;
                                            break;
                                        case "S":
                                            face = BlockFace.SOUTH;
                                            break;
                                        case "E":
                                            face = BlockFace.EAST;
                                            break;
                                        case "W":
                                            face = BlockFace.WEST;
                                            break;
                                        case "C":
                                            face = BlockFace.SELF;
                                            break;
                                        case "P":
                                            break;
                                    }

                                    switch (secondChar) {

                                        case "L":
                                            height = Wall.Height.LOW;
                                            break;
                                        case "T":
                                            height = Wall.Height.TALL;
                                            break;
                                        case "N":
                                            height = Wall.Height.NONE;
                                            break;
                                    }

                                    Location loc = e.getBlockPlaced().getLocation();
                                    setState(e.getBlockPlaced().getType(), e.getBlockPlaced().getLocation(), face, height);
                                    Bukkit.getScheduler().runTaskLater(pl, new Runnable() {
                                        @Override
                                        public void run() {
                                            placedWalls.remove(loc);
                                        }
                                    }, 100L);
                                }

                            }
                        }
                    }, 1L);
                }
            }
        }
    }


    public void setState(Material mat, Location loc, BlockFace face, Wall.Height height) {

        BlockData blockData = loc.getBlock().getBlockData();

        if (blockData instanceof Wall) {
            Wall wall = (Wall) blockData;

            if (face != BlockFace.SELF) {
                wall.setHeight(face, height);
            } else {
                if (height == Wall.Height.TALL) {
                    wall.setUp(true);
                } else {
                    wall.setUp(false);
                }
            }

            loc.getBlock().setBlockData(wall, false);
        }
    }


    @EventHandler
    public void playerInteract(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getType() == Material.BEACON) {
            if (e.getPlayer().getInventory().getItemInMainHand().getType() == Material.WITHER_ROSE || e.getPlayer().getInventory().getItemInMainHand().getType() == Material.POTION || e.getPlayer().getInventory().getItemInMainHand().getType() == Material.SPLASH_POTION) {
                e.setCancelled(true);
                if (e.getPlayer().hasPermission("shakenbt.setbeacon")) {
                    e.setUseInteractedBlock(Event.Result.DENY);
                    e.setUseItemInHand(Event.Result.DENY);
                    Block block = e.getClickedBlock();

                    BlockState state = block.getState();

                    if (state instanceof Beacon) {
                        Beacon beaconState = (Beacon) state;

                        ItemStack mh = e.getPlayer().getInventory().getItemInMainHand();
                        if (mh.getType() == Material.WITHER_ROSE) {
                            e.setCancelled(true);
                            beaconState.setPrimaryEffect(PotionEffectType.WITHER);
                            beaconState.setSecondaryEffect(PotionEffectType.CONFUSION);
                            mh.setAmount(mh.getAmount() - 1);
                            state = beaconState;
                            state.update();

                        } else if (mh.hasItemMeta() && mh.getItemMeta() instanceof PotionMeta) {
                            e.setCancelled(true);
                            PotionMeta meta = (PotionMeta) mh.getItemMeta();
                            if (meta.hasEnchants() && meta.hasEnchant(Enchantment.BINDING_CURSE)) {
                                if (mh.getType() == Material.POTION) {
                                    beaconState.setPrimaryEffect(meta.getBasePotionData().getType().getEffectType());
                                    mh.setAmount(mh.getAmount() - 1);
                                } else {
                                    beaconState.setSecondaryEffect(meta.getBasePotionData().getType().getEffectType());
                                    mh.setAmount(mh.getAmount() - 1);
                                }
                            }
                            state = beaconState;
                            state.update();

                        }
                    }
                }
            }
        }
    }
}

