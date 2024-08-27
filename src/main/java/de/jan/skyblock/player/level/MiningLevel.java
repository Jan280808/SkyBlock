package de.jan.skyblock.player.level;

import de.jan.skyblock.builder.ItemBuilder;
import de.jan.skyblock.component.ComponentSerializer;
import de.jan.skyblock.player.SkyPlayer;
import de.jan.skyblock.sound.SoundManager;
import de.jan.skyblock.sound.Sounds;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MiningLevel implements Level {

    private final SkyPlayer skyPlayer;

    private double currentXP = 0.00;
    private int currentLevel = 0;

    public MiningLevel(SkyPlayer skyPlayer) {
        this.skyPlayer = skyPlayer;
    }

    @Override
    public Component displayName() {
        return ComponentSerializer.deserialize("<gold>Mining-Level ");
    }

    @Override
    public ItemStack displayItem() {
        return new ItemBuilder(Material.GOLDEN_PICKAXE).setDisplayName(displayName()).setLore("<gray>CurrentLevel: " + currentLevel, "<gray>neededXP: " + nextLevelXP() + " - currentXP: " + currentXP()).build();
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
}
