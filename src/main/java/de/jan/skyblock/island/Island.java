package de.jan.skyblock.island;

import de.jan.skyblock.SkyBlock;
import de.jan.skyblock.island.schematic.Category;
import de.jan.skyblock.island.schematic.Schematic;
import de.jan.skyblock.location.Locations;
import de.jan.skyblock.player.SkyPlayer;
import lombok.Getter;
import org.bukkit.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
public class Island implements Locations {

    private final int id;
    private final String worldName;
    private final UUID owner;
    private final Location center;
    private final int radius;
    private final Schematic islandSchematic;
    private final String createDate;
    private final List<Location> locations;

    private World world;

    public Island(int id, World world, UUID owner, Location center) {
        this.id = id;
        this.worldName = world.getName();
        this.world = world;
        this.owner = owner;
        this.center = center;
        this.radius = 10;
        this.islandSchematic = createSchematic();
        this.createDate = new SimpleDateFormat("dd,MM,yy").format(new Date());
        this.locations = new ArrayList<>();
        playBoarder();
    }

    public Island(int id, String worldName, UUID owner, Location center) {
        this.id = id;
        this.worldName = worldName;
        this.owner = owner;
        this.center = center;
        this.radius = 10;
        this.islandSchematic = createSchematic();
        this.createDate = new SimpleDateFormat("dd,MM,yy").format(new Date());
        this.locations = new ArrayList<>();
        playBoarder();
    }

    public Island(int id, String worldName, UUID owner, Location center, String createDate) {
        this.id = id;
        this.worldName = worldName;
        this.owner = owner;
        this.center = center;
        this.radius = 10;
        this.islandSchematic = createSchematic();
        this.createDate = createDate;
        this.locations = new ArrayList<>();
        playBoarder();
    }

    public void teleport(SkyPlayer skyPlayer) {
        loadWorld();
        skyPlayer.getPlayer().teleport(center);
        skyPlayer.setCurrentLocation(this);
    }

    private Schematic createSchematic() {
        World world = center.getWorld();
        int xMin = center.getBlockX() - radius;
        int xMax = center.getBlockX() + radius;
        int zMin = center.getBlockZ() - radius;
        int zMax = center.getBlockZ() + radius;
        int yMin = -64;
        int yMax = 319;
        Location point1 = new Location(world, xMin, yMin, zMin);
        Location point2 = new Location(world, xMax, yMax, zMax);
        return new Schematic(point1, point2, Category.ISLAND);
    }

    private void loadWorld() {
        if(world != null) return;
        world = Bukkit.createWorld(new WorldCreator(worldName));
    }

    //not final way to display the boarder of an island
    private void playBoarder() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(SkyBlock.instance, () -> islandSchematic.showCube(Particle.HAPPY_VILLAGER), 0, 20);
    }

    @Override
    public String locationName() {
        return owner.toString();
    }
}
