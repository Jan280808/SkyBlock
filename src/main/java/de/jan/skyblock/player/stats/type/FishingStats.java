package de.jan.skyblock.player.stats.type;

import de.jan.skyblock.component.ComponentSerializer;
import de.jan.skyblock.player.SkyPlayer;
import de.jan.skyblock.player.stats.Stats;
import de.jan.skyblock.player.stats.StatsManager;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerFishEvent;

public class FishingStats implements Stats {

    private final SkyPlayer skyPlayer;

    private double currentXP = 0.00;
    private int currentLevel = 0;

    public FishingStats(SkyPlayer skyPlayer) {
        this.skyPlayer = skyPlayer;
    }

    @Override
    public Component displayName() {
        return ComponentSerializer.deserialize("<aqua>FishingLevel ");
    }

    @Override
    public Material material() {
        return Material.FISHING_ROD;
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
    public enum FishingOffer {
        FAILED(PlayerFishEvent.State.FAILED_ATTEMPT, 10),
        CAUGHT_FISH(PlayerFishEvent.State.CAUGHT_FISH, 20),
        CAUGHT_ITEM(PlayerFishEvent.State.CAUGHT_ENTITY, 30);

        private final PlayerFishEvent.State state;
        private final double xp;

        FishingOffer(PlayerFishEvent.State state, double xp) {
            this.state = state;
            this.xp = xp;
        }
    }
}
