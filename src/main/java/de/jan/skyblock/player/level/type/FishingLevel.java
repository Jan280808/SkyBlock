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
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

public class FishingLevel implements Level {

    private final SkyPlayer skyPlayer;

    private double currentXP = 0.00;
    private int currentLevel = 0;

    public FishingLevel(SkyPlayer skyPlayer) {
        this.skyPlayer = skyPlayer;
    }

    @Override
    public Component displayName() {
        return ComponentSerializer.deserialize("<aqua>FishingLevel ");
    }

    @Override
    public ItemStack displayItem() {
        return new ItemBuilder(Material.FISHING_ROD).setDisplayName(displayName()).setLore("<gray>CurrentLevel: " + currentLevel, "<gray>neededXP: " + nextLevelXP() + " - currentXP: " + currentXP()).build();
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
        skyPlayer.getPlayer().sendActionBar(displayName().append(ComponentSerializer.deserialize("<gold>LevelUP aktuelles FishingLevel: " + currentLevel())));
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
