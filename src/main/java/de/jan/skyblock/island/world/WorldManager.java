package de.jan.skyblock.island.world;

import de.jan.skyblock.island.Island;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.generator.ChunkGenerator;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.*;

public class WorldManager {

    private final List<DummyWorld> dummyList;

    public WorldManager() {
        this.dummyList = new ArrayList<>();
    }

    public DummyWorld getDummyWorldWithFreeSlot() {
        Optional<DummyWorld> optionalWorld = dummyList.stream().filter(dummyWorld -> dummyWorld != null && dummyWorld.haveFreeSlot()).findFirst();
        if(optionalWorld.isPresent()) return optionalWorld.get();
        DummyWorld dummyWorld = new DummyWorld(generateVoidMap("dummyWorld" + dummyList.size()), 5);
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

    private World loadExistingWorld(UUID uuid) {
        File worldFolder = new File(Bukkit.getWorldContainer(), uuid.toString());
        //checks whether the world really exists in the folder, otherwise a new one is created when loading
        if(!worldFolder.exists() && !worldFolder.isDirectory()) return null;
        return Bukkit.createWorld(new WorldCreator(uuid.toString()));
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
