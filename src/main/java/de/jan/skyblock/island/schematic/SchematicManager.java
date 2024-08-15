package de.jan.skyblock.island.schematic;

import de.jan.skyblock.SkyBlock;
import de.jan.skyblock.island.Island;
import de.jan.skyblock.island.IslandManager;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SchematicManager {

    private final List<Schematic> schematicList;
    private final World schematicWorld;

    public SchematicManager(IslandManager islandManager) {
        this.schematicList = new ArrayList<>();
        this.schematicWorld = islandManager.getWorldManager().generateVoidMap("schematicWorld");
        loadSchematic();
    }

    public void generateSchematic(Island island, Category category) {
        if(schematicList.isEmpty()) throw new NullPointerException("No schematics are loaded");
        schematicList.stream().filter(schematic -> schematic.getCategory().equals(category)).forEach(schematic -> schematic.blockList().forEach(block -> {
            Location schematicCenter = schematic.getCenter();
            int deltaX = block.getX() - schematicCenter.getBlockX();
            int deltaY = block.getY() - schematicCenter.getBlockY();
            int deltaZ = block.getZ() - schematicCenter.getBlockZ();
            Location newBlockLocation = island.getCenter().clone().add(deltaX, deltaY, deltaZ);
            Block newBlock = island.getWorld().getBlockAt(newBlockLocation);
            newBlock.setType(block.getType());
            newBlock.setBlockData(block.getBlockData());

            //copy items from schematicChest
            if(block.getType() == Material.CHEST) {
                Chest sourceChest = (Chest) block.getState();
                Chest targetChest = (Chest) newBlock.getState();

                Inventory sourceInventory = sourceChest.getInventory();
                Inventory targetInventory = targetChest.getInventory();

                targetInventory.clear();
                for (int i = 0; i < sourceInventory.getSize(); i++) {
                    ItemStack item = sourceInventory.getItem(i);
                    if (item != null) {
                        targetInventory.setItem(i, item.clone());
                    }
                }
            }
        }));
    }

    private void loadSchematic() {
        Location point1 = new Location(schematicWorld, -2, 99, -13);
        Location point2 = new Location(schematicWorld, 8, 113,-25);
        Schematic schematic = new Schematic(point1, point2, Category.ISLAND_NORMAL);
        this.schematicList.add(schematic);

        Bukkit.getScheduler().scheduleSyncRepeatingTask(SkyBlock.instance, () -> schematic.showCube(Particle.HAPPY_VILLAGER), 0, 20);
    }
}
