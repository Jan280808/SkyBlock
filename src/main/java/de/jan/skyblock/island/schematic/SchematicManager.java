package de.jan.skyblock.island.schematic;

import de.jan.skyblock.SkyBlock;
import de.jan.skyblock.island.IslandManager;
import org.bukkit.*;

import java.util.ArrayList;
import java.util.List;

public class SchematicManager {

    private final List<Schematic> schematicList;
    private final World schematicWorld;

    public SchematicManager(IslandManager islandManager) {
        this.schematicList = new ArrayList<>();
        this.schematicWorld = islandManager.generateVoidMap("schematicWorld");
        loadSchematic();
    }

    private void loadSchematic() {
        Location point1 = new Location(schematicWorld, 9, 92, 53);
        Location point2 = new Location(schematicWorld, 28, 103, 116);
        Schematic schematic = new Schematic(point1, point2, Category.ISLAND_NORMAL);
        this.schematicList.add(schematic);

        Bukkit.getScheduler().scheduleSyncRepeatingTask(SkyBlock.instance, () -> schematic.showCube(Particle.HAPPY_VILLAGER), 0, 20);
    }

    public Schematic getSchematic(Category category) {
        if(schematicList.isEmpty()) return null;
        for(Schematic schematic : schematicList) {
            if(schematic.getCategory().equals(category)) return schematic;
        }
        return null;
    }
}
