package de.jan.skyblock.player.stats.type;

import de.jan.skyblock.component.ComponentSerializer;
import de.jan.skyblock.player.SkyPlayer;
import de.jan.skyblock.player.stats.Stats;
import de.jan.skyblock.player.stats.StatsManager;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;

public class LumberJackStats implements Stats {

    private final SkyPlayer skyPlayer;

    private double currentXP = 0.00;
    private int currentLevel = 0;

    public LumberJackStats(SkyPlayer skyPlayer) {
        this.skyPlayer = skyPlayer;
    }

    @Override
    public Component displayName() {
        return ComponentSerializer.deserialize("<gold>LumberJack ");
    }

    @Override
    public Material material() {
        return Material.IRON_AXE;
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
    public enum Wood {
        OAK(Material.OAK_LOG, 1),
        BIRCH(Material.BIRCH_LOG, 1),
        JUNGLE(Material.JUNGLE_LOG, 1),
        ACACIA(Material.ACACIA_LOG, 1),
        DARK_OAK(Material.DARK_OAK_LOG, 1),
        SPRUCE(Material.SPRUCE_LOG, 1);

        private final Material material;
        private final double xp;

        Wood(Material material, double xp) {
            this.material = material;
            this.xp = xp;
        }
    }
}
