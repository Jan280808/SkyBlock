package de.jan.skyblock.player.level;

import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;

public interface Level {

    Component displayName();

    ItemStack displayItem();

    int currentLevel();

    double currentXP();

    void addXP(double amount);

    void levelUP();
}
