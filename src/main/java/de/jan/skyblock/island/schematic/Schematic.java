package de.jan.skyblock.island.schematic;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.*;

@Getter
public class Schematic {

    private final int xMin;
    private final int xMax;
    private final int yMin;
    private final int yMax;
    private final int zMin;
    private final int zMax;
    private final World world;
    private final Location location1;
    private final Location location2;
    private final Category category;

    public Schematic(final Location point1, final Location point2, Category category) {
        this.xMin = Math.min(point1.getBlockX(), point2.getBlockX());
        this.xMax = Math.max(point1.getBlockX(), point2.getBlockX());
        this.yMin = Math.min(point1.getBlockY(), point2.getBlockY());
        this.yMax = Math.max(point1.getBlockY(), point2.getBlockY());
        this.zMin = Math.min(point1.getBlockZ(), point2.getBlockZ());
        this.zMax = Math.max(point1.getBlockZ(), point2.getBlockZ());
        this.world = point1.getWorld();
        this.location1 = point1;
        this.location2 = point2;
        this.category = category;
    }

    public List<Block> blockList() {
        final ArrayList<Block> bL = new ArrayList<>(this.getTotalBlockSize());
        for(int x = this.xMin; x <= this.xMax; ++x) {
            for(int y = this.yMin; y <= this.yMax; ++y) {
                for(int z = this.zMin; z <= this.zMax; ++z) {
                    final Block b = this.world.getBlockAt(x, y, z);
                    if(!b.getType().equals(Material.AIR)) bL.add(b);
                }
            }
        }
        return bL;
    }

    public Location getCenter() {
        return new Location(this.world, (float)(this.xMax - this.xMin) / 2 + this.xMin, (float)(this.yMax - this.yMin) / 2 + this.yMin, (float)(this.zMax - this.zMin) / 2 + this.zMin);
    }

    public int getHeight() {
        return this.yMax - this.yMin + 1;
    }

    public Location getRandomLocation() {
        final Random rand = new Random();
        final int x = rand.nextInt(Math.abs(this.xMax - this.xMin) + 1) + this.xMin;
        final int y = rand.nextInt(Math.abs(this.yMax - this.yMin) + 1) + this.yMin;
        final int z = rand.nextInt(Math.abs(this.zMax - this.zMin) + 1) + this.zMin;
        return new Location(this.world, x, y, z);
    }

    public int getTotalBlockSize() {
        return this.getHeight() * this.getXWidth() * this.getZWidth();
    }

    public int getXWidth() {
        return this.xMax - this.xMin + 1;
    }

    public int getZWidth() {
        return this.zMax - this.zMin + 1;
    }

    public boolean isIn(final Location loc) {
        return loc.getWorld() == this.world && loc.getBlockX() >= this.xMin && loc.getBlockX() <= this.xMax && loc.getBlockY() >= this.yMin && loc.getBlockY() <= this.yMax && loc
                .getBlockZ() >= this.zMin && loc.getBlockZ() <= this.zMax;
    }

    public boolean isIn(final Player player) {
        return this.isIn(player.getLocation());
    }

    public void showCube(Particle particle) {
        if(location1 == null && location2 == null)
            return;
        World world = Objects.requireNonNull(location1).getWorld();
        double minX = Math.min(location1.getX(), location2.getX());
        double minY = Math.min(location1.getY(), location2.getY());
        double minZ = Math.min(location1.getZ(), location2.getZ());
        double maxX = Math.max(location1.getX(), location2.getX());
        double maxY = Math.max(location1.getY(), location2.getY());
        double maxZ = Math.max(location1.getZ(), location2.getZ());
        for (double x = minX; x <= maxX; x++) {
            for (double y = minY; y <= maxY; y++) {
                for (double z = minZ; z <= maxZ; z++) {
                    int components = 0;
                    if (x == minX || x == maxX) components++;
                    if (y == minY || y == maxY) components++;
                    if (z == minZ || z == maxZ) components++;
                    if (components >= 2) {
                        Location area = new Location(world, x, y, z);
                        area.getWorld().spawnParticle(particle, area, 0);
                    }
                }
            }
        }
    }


}
