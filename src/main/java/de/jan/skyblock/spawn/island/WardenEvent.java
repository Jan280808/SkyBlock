package de.jan.skyblock.spawn.island;

import de.jan.skyblock.player.PlayerManager;
import de.jan.skyblock.player.SkyPlayer;
import de.jan.skyblock.spawn.SpawnIsland;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class WardenEvent implements Listener {

    private final PlayerManager playerManager;
    private final IslandWarden islandWarden;

    public WardenEvent(PlayerManager playerManager, SpawnIsland spawnIsland) {
        this.playerManager = playerManager;
        this.islandWarden = spawnIsland.getWarden();
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if(event.getClickedInventory() == null) return;
        Inventory inventory = event.getClickedInventory();

        Player player = (Player) event.getWhoClicked();
        SkyPlayer skyPlayer = playerManager.getSkyPlayer(player.getUniqueId());

        if(event.getCurrentItem() == null) return;
        ItemStack itemStack = event.getCurrentItem();
        islandWarden.clickInventory(skyPlayer, inventory, itemStack, event);
    }
}
