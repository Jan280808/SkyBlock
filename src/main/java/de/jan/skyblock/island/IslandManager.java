package de.jan.skyblock.island;

import de.jan.skyblock.SkyBlock;
import de.jan.skyblock.island.schematic.Category;
import de.jan.skyblock.island.schematic.Schematic;
import de.jan.skyblock.island.schematic.SchematicManager;
import de.jan.skyblock.island.world.WorldManager;
import de.jan.skyblock.player.SkyPlayer;
import lombok.Getter;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileInputStream;
import java.util.*;

@Getter
public class IslandManager {

    private final WorldManager worldManager;
    private final SchematicManager schematicManager;
    private final List<Island> islandList;

    public IslandManager() {
        this.worldManager = new WorldManager();
        this.schematicManager = new SchematicManager(this);
        this.islandList = new ArrayList<>();
    }

    public void loadExistingIsland(SkyPlayer skyPlayer) {
        World world = loadExistingWorld(skyPlayer.getUuid());
        //if world is null then player has never created an island
        if(world == null) return;

        UUID worldUUID = UUID.nameUUIDFromBytes(world.getName().getBytes());
        if(!worldUUID.equals(skyPlayer.getUuid())) return;
        Island island = new Island(skyPlayer.getUuid(), world.getSpawnLocation());
        this.islandList.add(island);
        skyPlayer.setIsland(island);
    }

    public void createNewIsland(SkyPlayer skyPlayer, Category category) {
        if(skyPlayer.getIsland() != null) return;
        World world = generateVoidMap(skyPlayer.getUuid().toString());
        Location spawnLocation = world.getSpawnLocation();

        //create and safe islandObject
        Island island = new Island(skyPlayer.getUuid(), spawnLocation);
        this.islandList.add(island);
        skyPlayer.setIsland(island);

        //load schematic in new voidMap
        long start = System.currentTimeMillis();
        Schematic schematic = schematicManager.getSchematic(category);
        Bukkit.getScheduler().scheduleSyncDelayedTask(SkyBlock.instance, () -> {
            schematic.blockList().forEach(block -> {
                Location schematicCenter = schematic.getCenter();
                int deltaX = block.getX() - schematicCenter.getBlockX();
                int deltaY = block.getY() - schematicCenter.getBlockY();
                int deltaZ = block.getZ() - schematicCenter.getBlockZ();
                Location newBlockLocation = spawnLocation.clone().add(deltaX, deltaY, deltaZ);
                Block newBlock = world.getBlockAt(newBlockLocation);
                newBlock.setType(block.getType());
                newBlock.setBlockData(block.getBlockData());

                //chest copy
            });
        }, 0);
        long time = System.currentTimeMillis()-start;
        SkyBlock.Logger.info("copy paste: {}ms", time);
        //teleport player to island
        skyPlayer.teleportToIsland();
    }

    private void safeIsland() {
        try {
            JSONObject jsonObject = new JSONObject(new JSONTokener(new FileInputStream("./plugins/skyBlock/island.json")));
            JSONObject islandsObject = jsonObject.getJSONObject("Islands");
            Iterator<String> levels = islandsObject.keys();
            while(levels.hasNext()) {
                String levelKey = levels.next();
                JSONObject levelObject = islandsObject.getJSONObject(levelKey);

                Iterator<String> islands = levelObject.keys();
                while(islands.hasNext()) {
                    String islandKey = islands.next();
                    int id = Integer.parseInt(levelKey+islandKey);
                    UUID uuid = UUID.fromString(levelObject.getString(islandKey));

                }
            }
        } catch (Exception exception) {
            SkyBlock.Logger.error("safe island has failed", exception);
        }
    }
}
