package de.jan.skyblock.island.schematic;

import de.jan.skyblock.location.Cube;
import lombok.Getter;
import org.bukkit.Location;

@Getter
public class Schematic {

    private final Cube cube;
    private final Location spawn;
    private final SchematicManager.Category category;

    public Schematic(Location point1, Location point2, Location spawn, SchematicManager.Category category) {
        this.cube = new Cube(point1, point2);
        this.spawn = spawn;
        this.category = category;
    }
}
