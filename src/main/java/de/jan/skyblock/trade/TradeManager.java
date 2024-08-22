package de.jan.skyblock.trade;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class TradeManager {

    private final Map<UUID, UUID> tradeMap;
    private final List<Trade> currentTrades;

    public TradeManager() {
        this.tradeMap = new HashMap<>();
        this.currentTrades = new ArrayList<>();
    }

    public void sendRequest(Player requester, Player recipient) {
        if(hasRequested(requester)) return;
        tradeMap.put(requester.getUniqueId(), recipient.getUniqueId());
        requester.sendMessage("du hast eine anfrage gestellt");
        recipient.sendMessage("du hast eine anfrage erhalten");
    }

    public void acceptRequest(Player requester, Player recipient) {
        if(!hasRequested(requester)) return;
        if(!tradeMap.get(requester.getUniqueId()).equals(recipient.getUniqueId())) return;
        currentTrades.add(new Trade(requester, recipient));
        tradeMap.remove(requester.getUniqueId());
        requester.sendMessage("deine anfrage wurde angenommen");
        recipient.sendMessage("du hast die anfrage angenommen");
    }

    public void denyRequest(Player requester, Player recipient) {
        if(!hasRequested(requester)) return;
        if(!tradeMap.get(requester.getUniqueId()).equals(recipient.getUniqueId())) return;
        tradeMap.remove(requester.getUniqueId());
        requester.sendMessage("deine anfrage wurde abgelehnt");
        recipient.sendMessage("du hast die anfrage abgelehnt");
    }

    public void clickInventory(Player player, Inventory clickInventory, InventoryClickEvent event) {
        currentTrades.stream().filter(trade -> player.equals(trade.getRecipient()) || player.equals(trade.getRequester())).forEach(trade -> {
            if(!trade.isAllowClick() || event.getClick().equals(ClickType.DOUBLE_CLICK)) {
                //duplication protection
                event.setCancelled(true);
                return;
            }
            //player click own inventory
            if(clickInventory.equals(player.getInventory())) {
                player.sendMessage("own inv");

            }

            //player click tradeInventory
            if(clickInventory.equals(trade.getInventory())) {
                if(!trade.clickTradeItems(player, event.getSlot())) event.setCancelled(true);
                ItemStack clickItem = event.getCurrentItem();
                if(clickItem == null) return;
                trade.clickItem(player, clickItem, event.getSlot());
            }
        });
    }

    public void cancelTrade(Player player, Inventory closedInventory) {
        Trade canceledTrade = null;
        for(Trade trade : currentTrades) {
            if(!trade.getInventory().equals(closedInventory)) return;
            if(player.equals(trade.getRecipient())) canceledTrade = trade;
            if(player.equals(trade.getRequester())) canceledTrade = trade;
        }
        if(canceledTrade == null) return;
        currentTrades.remove(canceledTrade);
        canceledTrade.cancelTrade();
        player.sendMessage("trade closed and removed");
    }

    public void canceledDrag(Player player, Inventory inventory, InventoryDragEvent event) {
        currentTrades.stream().filter(trade -> trade.getInventory().equals(inventory)).forEach(trade -> {
            event.setCancelled(true);
            player.sendMessage("Während eines Trades ist dieses feature deaktiviert");
        });
    }

    private boolean hasRequested(Player requester) {
        return tradeMap.containsKey(requester.getUniqueId());
    }
}
