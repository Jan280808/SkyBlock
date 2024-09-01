package de.jan.skyblock.island;

import de.jan.skyblock.SkyBlock;
import de.jan.skyblock.component.ComponentSerializer;
import de.jan.skyblock.island.generator.GeneratorManager;
import de.jan.skyblock.island.schematic.Category;
import de.jan.skyblock.island.schematic.SchematicManager;
import de.jan.skyblock.island.world.DummyWorld;
import de.jan.skyblock.island.world.WorldManager;
import de.jan.skyblock.player.SkyPlayer;
import lombok.Getter;
import org.bukkit.*;
import org.json.JSONObject;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

@Getter
public class IslandManager {

    private final File file;
    private JSONObject islandJson;
    private final List<Island> islandList;
    private final WorldManager worldManager;
    private final SchematicManager schematicManager;
    private final GeneratorManager generatorManager;

    public IslandManager() {
        this.file = new File("./plugins/SkyBlock/islands.json");
        this.islandList = new ArrayList<>();
        createJson();
        loadIslands();
        this.worldManager = new WorldManager(this);
        this.schematicManager = new SchematicManager(worldManager);
        this.generatorManager = new GeneratorManager(this);
    }

    public void createIsland(SkyPlayer skyPlayer, Category category) {
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
        safeIsland(island);
        SkyBlock.Logger.info("time: {}", time);
    }

    public Island getIslandFromLocation(Location location) {
        Optional<Island> optionalIsland = islandList.stream().filter(island -> island.getIslandSchematic().isIn(location)).findFirst();
        return optionalIsland.orElse(null);
    }

    private void safeIsland(Island island) {
        if(islandJson == null) throw new NullPointerException("island json is null");
        JSONObject newIsland = new JSONObject();
        newIsland.put("owner", island.getOwner());
        newIsland.put("createDate", island.getCreateDate());
        JSONObject center = new JSONObject();
        center.put("world", island.getCenter().getWorld().getName());
        center.put("x", island.getCenter().x());
        center.put("y", island.getCenter().y());
        center.put("z", island.getCenter().z());
        center.put("yaw", island.getCenter().getYaw());
        center.put("pitch", island.getCenter().getPitch());
        newIsland.put("center", center);
        islandJson.getJSONObject("islands").put(String.valueOf(island.getId()), newIsland);
        saveJsonToFile();
    }

    private void loadIslands() {
        if(islandJson == null || !islandJson.has("islands")) {
            SkyBlock.Logger.warn("No islands found to load.");
            return;
        }
        JSONObject islands = islandJson.getJSONObject("islands");
        for(String islandId : islands.keySet()) {
            JSONObject islandJson = islands.getJSONObject(islandId);
            String owner = islandJson.getString("owner");
            String createDate = islandJson.getString("createDate");

            JSONObject centerJson = islandJson.getJSONObject("center");
            String worldName = centerJson.getString("world");
            int x = centerJson.getInt("x");
            int y = centerJson.getInt("y");
            int z = centerJson.getInt("z");
            float yaw = centerJson.getFloat("yaw");
            float pitch = centerJson.getFloat("pitch");
            islandList.add(new Island(Integer.parseInt(islandId), worldName, UUID.fromString(owner), new Location(Bukkit.getWorld(worldName), x, y, z, yaw, pitch), createDate));
        }
        SkyBlock.Logger.info("Loaded {} islands from .json", islandList.size());
    }

    private void createJson() {
        File directory = file.getParentFile();
        if(file.exists()) {
            islandJson = loadJsonFromFile(file);
            return;
        }

        try {
            if(!directory.exists()) directory.mkdirs();
            file.createNewFile();
            islandJson = new JSONObject();
            islandJson.put("islands", new JSONObject());
            saveJsonToFile();
            SkyBlock.Logger.info("Created islands.json");
        } catch(IOException exception) {
            SkyBlock.Logger.error("Failed to create islands.json", exception);
        }
    }

    private void saveJsonToFile() {
        try(FileWriter writer = new FileWriter(file)) {
            writer.write(islandJson.toString(4));
            writer.flush();
            SkyBlock.Logger.info("Updated islands.json");
        } catch(IOException exception) {
            SkyBlock.Logger.error("Failed to save islands.json", exception);
        }
    }

    private JSONObject loadJsonFromFile(File file) {
        try(FileReader reader = new FileReader(file)) {
            char[] buffer = new char[(int) file.length()];
            reader.read(buffer);
            return new JSONObject(new String(buffer));
        } catch(IOException e) {
            SkyBlock.Logger.error("Failed to load islands.json", e);
            return new JSONObject();
        }
    }
}
