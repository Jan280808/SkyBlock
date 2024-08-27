package de.jan.skyblock.player.level;

import de.jan.skyblock.builder.ItemBuilder;
import de.jan.skyblock.component.ComponentSerializer;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MiningLevel implements Level {

    private double currentXP = 0.00;
    private int currentLevel = 0;

    @Override
    public Component displayName() {
        return ComponentSerializer.deserialize("<gold>Mining-Level");
    }

    @Override
    public ItemStack displayItem() {
        return new ItemBuilder(Material.GOLDEN_PICKAXE).setDisplayName(displayName()).setLore("CurrentLevel: " + currentLevel, "currentXP: " + currentXP).build();
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
        currentXP = currentXP + amount;
    }

    @Override
    public void levelUP() {
        currentLevel = currentLevel +1;
    }
}
