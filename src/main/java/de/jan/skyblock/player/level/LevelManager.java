package de.jan.skyblock.player.level;

import de.jan.skyblock.component.ComponentSerializer;
import de.jan.skyblock.player.SkyPlayer;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;

public class LevelManager {

    public void createInventory(SkyPlayer skyPlayer) {
        Inventory inventory = Bukkit.createInventory(skyPlayer.getPlayer(), 27, ComponentSerializer.deserialize("Level"));
        Arrays.stream(skyPlayer.getLevels()).forEach(level -> inventory.addItem(level.displayItem()));
        skyPlayer.getPlayer().openInventory(inventory);
    }
}
