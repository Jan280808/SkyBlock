package de.jan.skyblock.spawn.island;

import de.jan.skyblock.component.ComponentSerializer;
import de.jan.skyblock.island.IslandManager;
import de.jan.skyblock.island.schematic.SchematicManager;
import de.jan.skyblock.player.SkyPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class IslandWarden {

    private final IslandManager islandManager;
    private final Inventory inventory;

    public IslandWarden(IslandManager islandManager) {
        this.islandManager = islandManager;
        this.inventory = Bukkit.createInventory(null, 27, ComponentSerializer.deserialize("Erstelle eine Island"));
        Arrays.stream(SchematicManager.Category.values()).forEach(category -> inventory.addItem(category.getItemStack()));
    }

    public void openInventory(Player player) {
        player.openInventory(inventory);
    }

    public void clickInventory(SkyPlayer skyPlayer, Inventory clickInventory, ItemStack clickItemStack, InventoryClickEvent event) {
        if(!clickInventory.equals(inventory)) return;
        event.setCancelled(true);
        Arrays.stream(SchematicManager.Category.values()).filter(category -> clickItemStack.equals(category.getItemStack())).forEach(category -> islandManager.createNewIsland(skyPlayer, category));
    }
}
