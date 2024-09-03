package de.jan.skyblock.player.stats.type;

import de.jan.skyblock.component.ComponentSerializer;
import de.jan.skyblock.player.SkyPlayer;
import de.jan.skyblock.player.stats.Stats;
import de.jan.skyblock.player.stats.StatsManager;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;

import java.util.stream.IntStream;

@Getter
public class MiningStats implements Stats {

    private final SkyPlayer skyPlayer;

    private double currentXP = 0.00;
    private int currentLevel = 0;

    private final double defaultOreProbability = 10.0;
    private double oreProbability = defaultOreProbability;

    private final OreProbability[] oreList;
    private final OreProbability coalOre;
    private final OreProbability copperOre;
    private final OreProbability ironOre;
    private final OreProbability redstoneOre;
    private final OreProbability lapisOre;
    private final OreProbability goldOre;
    private final OreProbability diamondOre;
    private final OreProbability emeraldOre;

    public MiningStats(SkyPlayer skyPlayer) {
        this.skyPlayer = skyPlayer;
        this.oreList = new OreProbability[8];
        oreList[0] = this.coalOre = new OreProbability(Material.COAL_ORE, 70);
        oreList[1] = this.copperOre = new OreProbability(Material.COPPER_ORE, 60);
        oreList[2] = this.ironOre = new OreProbability(Material.IRON_ORE, 40);
        oreList[3] = this.redstoneOre = new OreProbability(Material.REDSTONE_ORE, 20);
        oreList[4] = this.lapisOre = new OreProbability(Material.LAPIS_ORE, 20);
        oreList[5] = this.goldOre = new OreProbability(Material.GOLD_ORE, 20);
        oreList[6] = this.diamondOre = new OreProbability(Material.DIAMOND_ORE, 10);
        oreList[7] = this.emeraldOre = new OreProbability(Material.EMERALD_ORE, 5);
    }

    public void increaseOreProbability(double amount) {
        this.oreProbability = oreProbability + amount;
    }

    public void decreaseOreProbability(double amount) {
        this.oreProbability = oreProbability - amount;
    }

    public void resetOreProbability() {
        this.oreProbability = defaultOreProbability;
    }

    @Override
    public Component displayName() {
        return ComponentSerializer.deserialize("<gold>MiningLevel ");
    }

    @Override
    public Material material() {
        return Material.GOLDEN_PICKAXE;
    }

    @Override
    public String[] lore() {
        return IntStream.range(0, 10).mapToObj(i -> {
                    if(i == 0) return "OreProbability: " + oreProbability;
                    else if(i == 1) return "";
                    else {
                        int oreIndex = (i - 2) % oreList.length;
                        return "<gray>" + oreList[oreIndex].material.name() + ": <gold>" + oreList[oreIndex].probability + "%";
                    }
                }).toArray(String[]::new);
    }

    @Override
    public double baseXP() {
        return 100.0;
    }

    @Override
    public double multiplier() {
        return 1.5;
    }

    @Override
    public int currentLevel() {
        return currentLevel;
    }

    @Override
    public double currentXP() {
        return currentXP;
    }

    @Override
    public void addXP(double amount) {
        currentXP += amount;
        StatsManager.actionbarXP(skyPlayer, this);
        while (currentXP >= nextLevelXP()) {
            currentXP -= nextLevelXP();
            levelUP();
        }
    }

    @Override
    public double nextLevelXP() {
        return baseXP() * Math.pow(multiplier(), currentLevel);
    }

    @Override
    public void levelUP() {
        currentLevel = currentLevel +1;
        StatsManager.actionbarLevelUP(skyPlayer, this);
    }

    @Override
    public boolean hasLevel(int requiredLevel) {
        return currentLevel >= requiredLevel;
    }

    @Getter
    public enum MiningBlocks {
        COBBLESTONE(Material.COBBLESTONE, 0.01),
        DEEPSLATE(Material.DEEPSLATE, 0.02), 
        ORE_COAL(Material.COAL_ORE, 10),
        ORE_IRON(Material.IRON_ORE, 10),
        ORE_GOLD(Material.GOLD_ORE, 10),
        ORE_REDSTONE(Material.REDSTONE_ORE, 10),
        ORE_LAPIS(Material.LAPIS_ORE, 10),
        ORE_DIAMOND(Material.DIAMOND_ORE, 10),
        ORE_EMERALD(Material.EMERALD_ORE, 10);

        private final Material material;
        private final double xp;

        MiningBlocks(Material material, double xp) {
            this.material = material;
            this.xp = xp;
        }
    }

    @Getter
    public static class OreProbability {

        private final Material material;
        private final double baseProbability;
        private double probability;

        public OreProbability(Material material, double baseProbability) {
            this.material = material;
            this.baseProbability = baseProbability;
            this.probability = baseProbability;
        }

        public void addProbability(double amount) {
            probability = probability + amount;
        }

        public void resetProbability() {
            probability = baseProbability;
        }
    }
}
