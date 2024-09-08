package de.jan.skyblock.island;

import de.jan.skyblock.SkyBlock;
import de.jan.skyblock.component.ComponentSerializer;
import de.jan.skyblock.island.generator.GeneratorManager;
import de.jan.skyblock.island.schematic.SchematicManager;
import de.jan.skyblock.island.world.DummyWorld;
import de.jan.skyblock.island.world.WorldManager;
import de.jan.skyblock.player.SkyPlayer;
import de.jan.skyblock.sound.SoundManager;
import de.jan.skyblock.sound.Sounds;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
public class IslandManager {

    private final File file;
    private final JSONObject islandJson;
    private final List<Island> islandList;
    private final WorldManager worldManager;
    private final SchematicManager schematicManager;
    private final GeneratorManager generatorManager;

    public IslandManager() {
        this.file = new File("./plugins/SkyBlock/islands.json");
        this.islandJson = createJson();
        this.islandList = new ArrayList<>();
        this.worldManager = new WorldManager(this);
        this.schematicManager = new SchematicManager(worldManager);
        this.generatorManager = new GeneratorManager();
        loadIslandsFromJson();
    }

    public void createNewIsland(SkyPlayer skyPlayer, SchematicManager.Category category) {
        if(skyPlayer.hasIsland()) {
            skyPlayer.getPlayer().sendMessage(SkyBlock.Prefix.append(ComponentSerializer.deserialize("<red>Du hast bereits eine Island")));
            SoundManager.playSound(Sounds.ERROR, skyPlayer);
            return;
        }

        DummyWorld dummyWorld = worldManager.getDummyWorldWithFreeSlot();
        int id = dummyWorld.currentIsland()+1;
        int x = 50*id;
        int z = 50*id;

        Location center = new Location(dummyWorld.getWorld(), x, 100, z);
        Island island = new Island(id, skyPlayer.getUuid(), center);
        dummyWorld.addIsland(island);

        schematicManager.generateSchematicIntoIsland(island, category);

        skyPlayer.setIsland(island);
        skyPlayer.teleportToIsland();
        saveIsland(island);
    }

    public Island getIslandFromLocation(Location location) {
        Optional<Island> optionalIsland = islandList.stream().filter(island -> island.getIslandLevel().getCube().isIn(location)).findFirst();
        return optionalIsland.orElse(null);
    }

    private void loadIslandsFromJson() {
        if(islandJson == null || !islandJson.has("islands")) {
            SkyBlock.Logger.warn("Islands could not be loaded");
            return;
        }
        JSONObject islands = islandJson.getJSONObject("islands");
        for(String islandId : islands.keySet()) {
            JSONObject islandJson = islands.getJSONObject(islandId);
            int id = Integer.parseInt(islandId);
            UUID owner = UUID.fromString(islandJson.getString("owner"));
            String createDate = islandJson.getString("createDate");
            String islandLevel = islandJson.getString("level");

            JSONObject centerJson = islandJson.getJSONObject("center");
            String worldName = centerJson.getString("world");
            int x = centerJson.getInt("x");
            int y = centerJson.getInt("y");
            int z = centerJson.getInt("z");
            float yaw = centerJson.getFloat("yaw");
            float pitch = centerJson.getFloat("pitch");
            Location center = new Location(Bukkit.getWorld(worldName), x, y, z, yaw, pitch);

            JSONArray membersArray = islandJson.getJSONArray("members");
            List<UUID> members = new ArrayList<>();
            if(!membersArray.isEmpty()) {
                members = new ArrayList<>();
                for(int i = 0; i < membersArray.length(); i++) members.add(UUID.fromString(membersArray.getString(i)));
            }

            islandList.add(new Island(id, owner, center, members, islandLevel, createDate));
        }
        SkyBlock.Logger.info("Loaded {} islands from .json", islandList.size());
    }

    private void saveIsland(Island island) {
        if(islandJson == null) throw new NullPointerException("islandJson is null");
        islandList.add(island);

        JSONObject newIsland = new JSONObject();
        newIsland.put("owner", island.getOwner().toString());
        newIsland.put("createDate", island.getCreateDate());
        newIsland.put("level", island.getIslandLevel().getLevel().name());

        JSONObject center = new JSONObject();
        center.put("world", island.getCenter().getWorld().getName());
        center.put("x", island.getCenter().getBlockX());
        center.put("y", island.getCenter().getBlockY());
        center.put("z", island.getCenter().getBlockZ());
        center.put("yaw", island.getCenter().getYaw());
        center.put("pitch", island.getCenter().getPitch());
        newIsland.put("center", center);

        JSONArray membersArray = new JSONArray();
        if(!island.getMembers().isEmpty()) island.getMembers().forEach(uuid -> membersArray.put(uuid.toString()));
        newIsland.put("members", membersArray);

        islandJson.getJSONObject("islands").put(String.valueOf(island.getId()), newIsland);
        saveJsonToFile(islandJson);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private JSONObject createJson() {
        File directory = file.getParentFile();
        if(file.exists()) return loadJsonFromFile(file);

        try {
            if(!directory.exists()) directory.mkdirs();
            file.createNewFile();
            JSONObject newJson = new JSONObject();
            newJson.put("islands", new JSONObject());
            saveJsonToFile(newJson);
            SkyBlock.Logger.info("Created islands.json");
            return newJson;
        } catch(IOException exception) {
            SkyBlock.Logger.error("Failed to create islands.json", exception);
            return null;
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private JSONObject loadJsonFromFile(File file) {
        try(FileReader reader = new FileReader(file)) {
            char[] buffer = new char[(int) file.length()];
            reader.read(buffer);
            return new JSONObject(new String(buffer));
        } catch(IOException exception) {
            SkyBlock.Logger.error("Failed to load islands.json", exception);
            return new JSONObject();
        }
    }

    private void saveJsonToFile(JSONObject jsonObject) {
        try(FileWriter writer = new FileWriter(file)) {
            writer.write(jsonObject.toString(4));
            writer.flush();
            SkyBlock.Logger.info("Updated islands.json");
        } catch(IOException exception) {
            SkyBlock.Logger.error("Failed to save islands.json", exception);
        }
    }
}
