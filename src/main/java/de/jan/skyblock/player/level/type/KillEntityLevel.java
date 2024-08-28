package de.jan.skyblock.player.level.type;

import de.jan.skyblock.builder.ItemBuilder;
import de.jan.skyblock.component.ComponentSerializer;
import de.jan.skyblock.player.SkyPlayer;
import de.jan.skyblock.player.level.Level;
import de.jan.skyblock.sound.SoundManager;
import de.jan.skyblock.sound.Sounds;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public class KillEntityLevel implements Level {

    private final SkyPlayer skyPlayer;

    private double currentXP = 0.00;
    private int currentLevel = 0;

    public KillEntityLevel(SkyPlayer skyPlayer) {
        this.skyPlayer = skyPlayer;
    }

    @Override
    public Component displayName() {
        return ComponentSerializer.deserialize("<gold>KillLevel ");
    }

    @Override
    public ItemStack displayItem() {
        return new ItemBuilder(Material.DIAMOND_SWORD).setDisplayName(displayName()).setLore("<gray>CurrentLevel: " + currentLevel, "<gray>neededXP: " + nextLevelXP() + " - currentXP: " + currentXP()).build();
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
    public String currentXP() {
        return String.format("%.2f", currentXP);
    }

    @Override
    public void addXP(double amount) {
        currentXP += amount;
        skyPlayer.getPlayer().sendActionBar(displayName().append(ComponentSerializer.deserialize("<gray>" + currentXP() + "/" + nextLevelXP())));
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
        SoundManager.playSound(Sounds.SUCCESSES, skyPlayer.getPlayer());
        skyPlayer.getPlayer().sendActionBar(displayName().append(ComponentSerializer.deserialize("<gold>LevelUP aktuelles MiningLevel: " + currentLevel())));
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
