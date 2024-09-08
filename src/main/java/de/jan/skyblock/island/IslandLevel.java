package de.jan.skyblock.island;

import de.jan.skyblock.location.Cube;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.World;

@Getter
public class IslandLevel {

    private final Island island;
    private final Cube cube;
    private final Level level;
    private final int radius;

    //for new island
    public IslandLevel(Island island) {
        this.island = island;
        this.level = Level.BEGINNER;
        this.radius = level.radius;
        this.cube = createCube();
    }

    //for island from .json
    public IslandLevel(Island island, String islandLevel) {
        this.island = island;
        this.level = Level.valueOf(islandLevel);
        this.radius = level.radius;
        this.cube = createCube();
    }

    private Cube createCube() {
        Location center = island.getCenter();
        World world = center.getWorld();
        int xMin = center.getBlockX() - radius;
        int xMax = center.getBlockX() + radius;
        int zMin = center.getBlockZ() - radius;
        int zMax = center.getBlockZ() + radius;
        int yMin = -64;
        int yMax = 319;
        Location point1 = new Location(world, xMin, yMin, zMin);
        Location point2 = new Location(world, xMax, yMax, zMax);
        return new Cube(point1, point2);
    }

    public enum Level {
        BEGINNER(10),
        ADVANCED(15),
        EXPERT(20),
        MASTER(50),
        GRANDMASTER(100);

        private final int radius;

        Level(int radius) {
            this.radius = radius;
        }
    }
}
