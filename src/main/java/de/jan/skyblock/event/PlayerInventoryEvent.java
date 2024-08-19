package de.jan.skyblock.event;

import de.jan.skyblock.spawn.shop.ShopManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PlayerInventoryEvent implements Listener {

    private final ShopManager shopManager;

    public PlayerInventoryEvent(ShopManager shopManager) {
        this.shopManager = shopManager;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if(event.getClickedInventory() == null) return;
        Inventory inventory = event.getClickedInventory();

        if(event.getCurrentItem() == null) return;
        ItemStack clickItem = event.getCurrentItem();
        shopManager.clickShopInventory(inventory, clickItem, player);
    }
}
