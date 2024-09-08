package de.jan.skyblock.island.schematic;

import de.jan.skyblock.SkyBlock;
import de.jan.skyblock.builder.ItemBuilder;
import de.jan.skyblock.island.Island;
import de.jan.skyblock.island.world.WorldManager;
import lombok.Getter;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.json.JSONObject;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SchematicManager {

    private final File file;
    private final JSONObject schematicJson;
    private final List<Schematic> schematicList;

    public SchematicManager(WorldManager worldManager) {
        this.file = new File("./plugins/SkyBlock/schematic.json");
        this.schematicJson = createJson();
        this.schematicList = new ArrayList<>();
        worldManager.generateVoidMap("schematicWorld");
        loadSchematicFromJson();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(SkyBlock.instance, () -> schematicList.forEach(schematic -> schematic.getCube().showCube(Particle.HAPPY_VILLAGER)), 0, 20);
    }

    public void generateSchematicIntoIsland(Island island, Category category) {
        if(schematicList.isEmpty()) throw new NullPointerException("No schematics are loaded");
        schematicList.stream().filter(schematic -> schematic.getCategory().equals(category)).forEach(schematic -> schematic.getCube().blockList().forEach(block -> {
            Location schematicCenter = schematic.getCube().getCenter();
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
                for(int i = 0; i < sourceInventory.getSize(); i++) {
                    ItemStack item = sourceInventory.getItem(i);
                    if(item != null) targetInventory.setItem(i, item.clone());
                }
            }
        }));
    }

    @Getter
    public enum Category {
        ISLAND_CLASSIC(new ItemBuilder(Material.GRASS_BLOCK).setDisplayName("<gray>Klassisch").setLore(" ", "<gray>Spiele auf eine klassische Insel wie von Früher", "<gray>- Kleine Insel", "<gray>- 1x Lavaeimer", "<gray>- 1x IceBlock", "<gray>- 8x Äpfel").build()),
        ISLAND_NORMAL(new ItemBuilder(Material.GRASS_BLOCK).setDisplayName("<gray>Normal").setLore(" ", "<gray>Erhalte eine großzügie Insel, mit vielen Rohstoffen", "<gray>- 1x Lavaeimer", "<gray>- 8x Äpfel", "<gray>- Ein angebissenen Kuchen", "<gray>- Einige Erze").build()),
        ISLAND_HARD(new ItemBuilder(Material.GRASS_BLOCK).setDisplayName("<red>Harte").setLore(" ", "<gray>Verusch es doch, wenn du dich traust", "<gray>Nur für Fortgeschrittene SkyBlock-Spieler", " ", "<gray>???", "<gray>???").build());

        private final ItemStack itemStack;

        Category(ItemStack itemStack) {
            this.itemStack = itemStack;
        }
    }

    private void loadSchematicFromJson() {
        double start = System.currentTimeMillis();
        if(schematicJson == null || !schematicJson.has("schematics")) {
            SkyBlock.Logger.warn("Schematics could not be loaded");
            return;
        }
        JSONObject schematics = schematicJson.getJSONObject("schematics");
        for(String schematicName : schematics.keySet()) {
            JSONObject schematicJson = schematics.getJSONObject(schematicName);

            Category category = Category.valueOf("ISLAND_" + schematicName.toUpperCase());
            Location spawn = getLocationFromJson(schematicJson.getJSONObject("spawn"));

            Location[] locations = new Location[2];
            for(int i = 1; i <= 2; i++) {
                JSONObject locationJson = schematicJson.getJSONObject("location"+i);
                locations[i -1] = getLocationFromJson(locationJson);
            }
            this.schematicList.add(new Schematic(locations[0], locations[1], spawn, category));
        }
        double time = start - System.currentTimeMillis();
        SkyBlock.Logger.info("load {} schematics in {}ms", schematicList.size(), time);
    }

    private Location getLocationFromJson(JSONObject jsonObject) {
        String worldName = jsonObject.getString("world");
        int x = jsonObject.getInt("x");
        int y = jsonObject.getInt("y");
        int z = jsonObject.getInt("z");
        float yaw = jsonObject.getFloat("yaw");
        float pitch = jsonObject.getFloat("pitch");
        return new Location(Bukkit.getWorld(worldName), x, y, z, yaw, pitch);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private JSONObject createJson() {
        File directory = file.getParentFile();
        if(file.exists()) return loadJsonFromFile(file);

        try {
            if(!directory.exists()) directory.mkdirs();
            file.createNewFile();
            JSONObject newJson = new JSONObject();
            newJson.put("schematics", new JSONObject());
            saveJsonToFile(newJson);
            SkyBlock.Logger.info("Created schematics.json");
            return newJson;
        } catch(IOException exception) {
            SkyBlock.Logger.error("Failed to create schematics.json", exception);
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
            SkyBlock.Logger.error("Failed to load schematic.json", exception);
            return new JSONObject();
        }
    }

    private void saveJsonToFile(JSONObject jsonObject) {
        try(FileWriter writer = new FileWriter(file)) {
            writer.write(jsonObject.toString(4));
            writer.flush();
            SkyBlock.Logger.info("Updated schematic.json");
        } catch(IOException exception) {
            SkyBlock.Logger.error("Failed to save schematic.json", exception);
        }
    }
}
