package de.jan.skyblock.player.level;

import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;

public interface Level {

    Component displayName();

    ItemStack displayItem();

    double baseXP();

    double multiplier();

    int currentLevel();

    //return string to avoid conversion errors
    String currentXP();

    void addXP(double amount);

    double nextLevelXP();

    void levelUP();

    boolean hasLevel(int requiredLevel);
}
