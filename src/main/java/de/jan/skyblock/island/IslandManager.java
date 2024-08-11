package de.jan.skyblock.island;

import de.jan.skyblock.island.schematic.Category;
import de.jan.skyblock.island.schematic.Schematic;
import de.jan.skyblock.island.schematic.SchematicManager;
import de.jan.skyblock.player.SkyPlayer;
import lombok.Getter;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.*;

@Getter
public class IslandManager {

    private final SchematicManager schematicManager;
    private final List<Island> islandList;
    private final World islandWorld;

    public IslandManager() {
        this.schematicManager = new SchematicManager();
        this.islandList = new ArrayList<>();
        this.islandWorld = Bukkit.createWorld(new WorldCreator("islandWorld"));
    }

    public void createIsland(SkyPlayer skyPlayer) {
        int lastIndex = islandList.size();
        int x = 0;
        int z = 0;

        for(int i = 1; i < lastIndex; i++) {
            x = 50*i;
            z = 50*i;
        }

        Location location = new Location(islandWorld, x, 100, z);
        location.getBlock().setType(Material.BEDROCK);
        Island island = new Island(skyPlayer.getUuid(), lastIndex+1, location);
        skyPlayer.setIsland(island);
        islandList.add(island);

        Schematic schematic = schematicManager.getSchematic(Category.ISLAND_NORMAL);
        schematic.blockList().forEach(block -> {
            Location schematicCenter = schematic.getCenter();
            int deltaX = block.getX() - schematicCenter.getBlockX();
            int deltaY = block.getY() - schematicCenter.getBlockY();
            int deltaZ = block.getZ() - schematicCenter.getBlockZ();
            Location newBlockLocation = location.clone().add(deltaX, deltaY, deltaZ);
            Block newBlock = islandWorld.getBlockAt(newBlockLocation);
            newBlock.setType(block.getType());
            newBlock.setBlockData(block.getBlockData());
        });

        Player player = skyPlayer.getPlayer();
        player.teleport(island.getCenter().add( 0, 2.5, 0).toCenterLocation());
        player.sendMessage("owner: " + island.getOwner());
        player.sendMessage("center: " + island.getCenter().x() + ", " + island.getCenter().z());
        player.sendMessage("id: " + island.getId());
    }

    public Island getIslandPlayerIsOn(Player player) {
        Location playerLocation = player.getLocation();
        if(!playerLocation.getWorld().equals(islandWorld)) return null;
        for(Island island : islandList) {
            double distance = playerLocation.distance(island.getCenter());
            if(distance <= island.getMaxRadius()) return island;
        }
        return null;
    }
}
