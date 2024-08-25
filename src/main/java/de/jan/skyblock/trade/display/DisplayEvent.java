package de.jan.skyblock.trade.display;

import de.jan.skyblock.sound.SoundManager;
import de.jan.skyblock.sound.Sounds;
import de.jan.skyblock.trade.TradeManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;

public class DisplayEvent implements Listener {

    private final DisplayManager displayManager;

    public DisplayEvent(TradeManager tradeManager) {
        this.displayManager = tradeManager.getDisplayManager();
    }

    @EventHandler
    @Deprecated
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        displayManager.createDisplay(player, message, event);
    }

    @EventHandler
    public void onClickDisplay(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory clickInventory = event.getClickedInventory();
        if(clickInventory == null) return;
        displayManager.clickInventory(player, clickInventory, event.getCurrentItem(), event);
    }

    @EventHandler
    public void onDragDisplay(InventoryDragEvent event) {
        Inventory clickInventory = event.getInventory();
        displayManager.getDisplayItemMap().values().stream().filter(displayItem -> displayItem.getInventory().equals(clickInventory)).forEach(displayItem -> {
            event.setCancelled(true);
            SoundManager.playSound(Sounds.ERROR, (Player) event.getWhoClicked());
        });
    }
}
