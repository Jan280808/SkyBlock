package de.jan.skyblock.island.world;

import de.jan.skyblock.island.Island;
import de.jan.skyblock.island.IslandManager;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.generator.ChunkGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class WorldManager {

    private final IslandManager islandManager;
    private final List<DummyWorld> dummyList;
    private final int maxIslandPerWorld = 5;

    public WorldManager(IslandManager islandManager) {
        this.islandManager = islandManager;
        this.dummyList = new ArrayList<>();
        load();
    }

    private void load() {
        Map<World, DummyWorld> worldToDummyWorldMap = new HashMap<>();
        for(Island island : islandManager.getIslandList()) {
            World world = island.getWorld();
            DummyWorld dummyWorld = worldToDummyWorldMap.get(world);

            if(dummyWorld == null) {
                dummyWorld = new DummyWorld(world, maxIslandPerWorld);
                worldToDummyWorldMap.put(world, dummyWorld);
                dummyList.add(dummyWorld);
            }
            dummyWorld.addIsland(island);
        }
    }

    public DummyWorld getDummyWorldWithFreeSlot() {
        Optional<DummyWorld> optionalWorld = dummyList.stream().filter(dummyWorld -> dummyWorld != null && dummyWorld.haveFreeSlot()).findFirst();
        if(optionalWorld.isPresent()) return optionalWorld.get();
        DummyWorld dummyWorld = new DummyWorld(generateVoidMap("dummyWorld" + dummyList.size()), maxIslandPerWorld);
        this.dummyList.add(dummyWorld);
        return dummyWorld;
    }

    public World generateVoidMap(String worldName) {
        if(Bukkit.getWorld(worldName) != null) return Bukkit.getWorld(worldName);
        WorldCreator worldCreator = new WorldCreator(worldName);
        worldCreator.generator(new VoidGenerator());
        worldCreator.environment(World.Environment.NORMAL);
        worldCreator.type(WorldType.FLAT);
        worldCreator.generateStructures(false);
        return Bukkit.getServer().createWorld(worldCreator);
    }

    //generate chunks with nothing "void"
    public static class VoidGenerator extends ChunkGenerator {

        public byte[] generate(World world, Random random, int x, int z) {
            return new byte[32768];
        }

        @Override
        public boolean canSpawn(@NotNull World world, int x, int z) {
            return true;
        }
    }
}
