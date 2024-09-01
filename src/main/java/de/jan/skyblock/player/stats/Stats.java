package de.jan.skyblock.player.stats;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;

public interface Stats {

    Component displayName();

    Material material();

    String[] lore();

    double baseXP();

    double multiplier();

    int currentLevel();

    double currentXP();

    void addXP(double amount);

    double nextLevelXP();

    void levelUP();

    boolean hasLevel(int requiredLevel);
}
