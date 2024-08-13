package de.jan.skyblock.island.world;

import de.jan.skyblock.island.Island;
import lombok.Getter;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

@Getter
public class DummyWorld {

    private final World world;
    private final int maxIsland;
    private final List<Island> islandList;

    public DummyWorld(World world, int maxIsland) {
        this.world = world;
        this.maxIsland = maxIsland;
        this.islandList = new ArrayList<>();
    }

    public void addIsland(Island island) {
        if(islandList.contains(island)) return;
        islandList.add(island);
    }

    public int currentIsland() {
        return islandList.size();
    }

    public boolean haveFreeSlot() {
        return currentIsland() < maxIsland;
    }
}
