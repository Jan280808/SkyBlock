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
import org.bukkit.inventory.ItemStack;

public class LumberJackLevel implements Level {

    private final SkyPlayer skyPlayer;

    private double currentXP = 0.00;
    private int currentLevel = 0;

    public LumberJackLevel(SkyPlayer skyPlayer) {
        this.skyPlayer = skyPlayer;
    }

    @Override
    public Component displayName() {
        return ComponentSerializer.deserialize("<gold>LumberJack ");
    }

    @Override
    public ItemStack displayItem() {
        return new ItemBuilder(Material.IRON_AXE).setDisplayName(displayName()).setLore("<gray>CurrentLevel: " + currentLevel, "<gray>neededXP: " + nextLevelXP() + " - currentXP: " + currentXP()).build();
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
