package de.jan.skyblock.player.stats.type;

import de.jan.skyblock.component.ComponentSerializer;
import de.jan.skyblock.player.SkyPlayer;
import de.jan.skyblock.player.stats.Stats;
import de.jan.skyblock.player.stats.StatsManager;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;

public class FarmerStats implements Stats {

    private final SkyPlayer skyPlayer;

    private double currentXP = 0.00;
    private int currentLevel = 0;

    public FarmerStats(SkyPlayer skyPlayer) {
        this.skyPlayer = skyPlayer;
    }

    @Override
    public Component displayName() {
        return ComponentSerializer.deserialize("<aqua>FarmerStats ");
    }

    @Override
    public Material material() {
        return Material.GOLDEN_HOE;
    }

    @Override
    public String[] lore() {
        return new String[0];
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
        while(currentXP >= nextLevelXP()) {
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
    public enum FarmProducts {
        MELON(Material.MELON_SLICE),
        POTATO(Material.POTATO),
        CARROT(Material.CARROT),
        PUMPKIN(Material.PUMPKIN),
        SUGAR_CANE(Material.SUGAR_CANE),
        BAMBOO(Material.BAMBOO),
        CACTUS(Material.CACTUS),
        WHEAT(Material.WHEAT);

        private final Material material;

        FarmProducts(Material material) {
            this.material = material;
        }
    }
}
