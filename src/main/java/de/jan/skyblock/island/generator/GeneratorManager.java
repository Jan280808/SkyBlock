package de.jan.skyblock.island.generator;

import de.jan.skyblock.island.IslandManager;
import de.jan.skyblock.player.SkyPlayer;
import de.jan.skyblock.player.stats.type.MiningStats;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Getter
public class GeneratorManager {

    private final IslandManager islandManager;
    private final Map<Block, SkyPlayer> blockMap;
    private final Random random;

    public GeneratorManager(IslandManager islandManager) {
        this.islandManager = islandManager;
        this.blockMap = new HashMap<>();
        this.random = new Random();
    }

    public Material generateOre(SkyPlayer skyPlayer) {
        MiningStats miningStats = skyPlayer.getMiningStats();
        if(random.nextDouble() * 100 < (100 - miningStats.getOreProbability())) return Material.COBBLESTONE;
        double totalProbability = 0;
        for(MiningStats.OreProbability ore : miningStats.getOreList()) totalProbability += ore.getProbability();

        double randomValue = random.nextDouble() * totalProbability;
        double cumulativeProbability = 0;

        for(MiningStats.OreProbability ore : miningStats.getOreList()) {
            cumulativeProbability += ore.getProbability();
            if(randomValue < cumulativeProbability) return ore.getMaterial();
        }

        return Material.COBBLESTONE;
    }
}
