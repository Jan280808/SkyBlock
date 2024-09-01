package de.jan.skyblock.player.stats.type;

import de.jan.skyblock.component.ComponentSerializer;
import de.jan.skyblock.player.SkyPlayer;
import de.jan.skyblock.player.stats.Stats;
import de.jan.skyblock.player.stats.StatsManager;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class KillHostileStats implements Stats {

    private final SkyPlayer skyPlayer;

    private double currentXP = 0.00;
    private int currentLevel = 0;

    public KillHostileStats(SkyPlayer skyPlayer) {
        this.skyPlayer = skyPlayer;
    }

    @Override
    public Component displayName() {
        return ComponentSerializer.deserialize("<gold>KillLevel ");
    }

    @Override
    public Material material() {
        return Material.IRON_SWORD;
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
    public enum KillingEntity {
        ZOMBIE(EntityType.ZOMBIE, 50),
        CREEPER(EntityType.CREEPER, 50),
        SPIDER(EntityType.SPIDER, 25),
        ENDERMAN(EntityType.ENDERMAN, 100);

        private final EntityType entityType;
        private final double xp;

        KillingEntity(EntityType entityType, double xp) {
            this.entityType = entityType;
            this.xp = xp;
        }
    }
}
