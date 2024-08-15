package de.jan.skyblock.island;

import de.jan.skyblock.SkyBlock;
import de.jan.skyblock.component.ComponentSerializer;
import de.jan.skyblock.island.schematic.Category;
import de.jan.skyblock.island.schematic.SchematicManager;
import de.jan.skyblock.island.world.DummyWorld;
import de.jan.skyblock.island.world.WorldManager;
import de.jan.skyblock.player.SkyPlayer;
import lombok.Getter;
import org.bukkit.*;
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

    public void createNewIsland(SkyPlayer skyPlayer, Category category) {
        float start = System.currentTimeMillis();
        if(skyPlayer.getIsland() != null) {
            skyPlayer.getPlayer().sendMessage(ComponentSerializer.deserialize("<red>You already have an island"));
            return;
        }

        DummyWorld dummyWorld = worldManager.getDummyWorldWithFreeSlot();
        int id = dummyWorld.currentIsland()+1;
        int x = 0; int z = 0;
        for(int i = 1; i < id; i++) {
            x = 50*i;
            z = 50*i;
        }

        Location location = new Location(dummyWorld.getWorld(), x, 100, z);
        Island island = new Island(id, dummyWorld.getWorld(), skyPlayer.getUuid(), location);
        islandList.add(island);
        dummyWorld.addIsland(island);

        //place schematic on island
        schematicManager.generateSchematic(island, category);

        skyPlayer.setIsland(island);
        skyPlayer.teleportToIsland();
        float time = start - System.currentTimeMillis();
        SkyBlock.Logger.info("time: {}", time);
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
