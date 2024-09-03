package de.jan.skyblock.player.stats;

import de.jan.skyblock.builder.ItemBuilder;
import de.jan.skyblock.component.ComponentSerializer;
import de.jan.skyblock.player.SkyPlayer;
import de.jan.skyblock.sound.SoundManager;
import de.jan.skyblock.sound.Sounds;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;

public class StatsManager {

    public void createInventory(SkyPlayer skyPlayer) {
        Inventory inventory = Bukkit.createInventory(skyPlayer.getPlayer(), 27, ComponentSerializer.deserialize("Level"));
        Arrays.stream(skyPlayer.getStats()).forEach(stats -> inventory.addItem(new ItemBuilder(stats.material()).setDisplayName(stats.displayName()).setLore(createProgressbar(stats)).setLore(stats.lore()).setLore(" ", "CurrentLevel: " + stats.currentLevel()).build()));
        skyPlayer.getPlayer().openInventory(inventory);
    }

    public static void actionbarXP(SkyPlayer skyPlayer, Stats stats) {
        skyPlayer.getPlayer().sendActionBar(stats.displayName().append(createProgressbar(stats)));
        SoundManager.playSound(Sounds.LEVEL_XP, skyPlayer);
    }

    public static void actionbarLevelUP(SkyPlayer skyPlayer, Stats level) {
        skyPlayer.getPlayer().sendActionBar(level.displayName().append(ComponentSerializer.deserialize("<Level> currentLevel: " + level.currentLevel())));
        SoundManager.playSound(Sounds.LEVEL_UP, skyPlayer);
    }

    private static Component createProgressbar(Stats level) {
        int totalBars = 20;
        double progressPercentage = (level.currentXP() / level.nextLevelXP()) * 100;
        int filledBars = (int) (progressPercentage / (100.0 / totalBars));

        Component progressBar = Component.empty();
        for(int i = 0; i < totalBars; i++) {
            if(i < filledBars) progressBar = progressBar.append(ComponentSerializer.deserialize("<green>|"));
            else progressBar = progressBar.append(ComponentSerializer.deserialize("<gray>|"));
        }

        progressBar = progressBar.append(ComponentSerializer.deserialize(" <white>" + String.format("%.2f", progressPercentage) + "%"));
        return progressBar;
    }
}
