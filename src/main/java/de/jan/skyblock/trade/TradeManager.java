package de.jan.skyblock.trade;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;

import java.util.*;

public class TradeManager {

    private final Map<UUID, UUID> tradeMap;
    private final List<Trade> currentTrades;

    public TradeManager() {
        this.tradeMap = new HashMap<>();
        this.currentTrades = new ArrayList<>();
    }

    public void sendRequest(Player requester, Player recipient) {
        UUID requesterUUID = requester.getUniqueId();
        UUID recipientUUID = recipient.getUniqueId();
        if(tradeMap.containsKey(recipientUUID) && tradeMap.get(recipientUUID).equals(requesterUUID)) {
            acceptRequest(requester, recipient);
            return;
        }

        if(hasRequested(requester)) return;
        tradeMap.put(recipientUUID, requesterUUID);
        sendClickableRequest(recipient, requester);
    }

    public void acceptRequest(Player requester, Player recipient) {
        if(!hasRequested(requester)) return;
        if(!tradeMap.get(requester.getUniqueId()).equals(recipient.getUniqueId())) return;
        currentTrades.add(new Trade(requester, recipient));
        tradeMap.remove(requester.getUniqueId());
        requester.sendMessage("Deine Tradeanfrage an " + recipient.getName() + " wurde angenommen");
        recipient.sendMessage("Du hast die Tradeanfrage von " + requester.getName() + " angenommen");
    }

    public void denyRequest(Player requester, Player recipient) {
        if(!hasRequested(requester)) return;
        if(!tradeMap.get(requester.getUniqueId()).equals(recipient.getUniqueId())) return;
        tradeMap.remove(requester.getUniqueId());
        requester.sendMessage("Deine Tradeanfrage an " + recipient.getName() + " wurde abgelehnt");
        recipient.sendMessage("Du hast die Tradeanfrage von " + requester.getName() + " abgelehnt");
    }

    public void clickInventory(Player player, Inventory clickInventory, InventoryClickEvent event) {
        currentTrades.stream().filter(trade -> player.equals(trade.getRecipient()) || player.equals(trade.getRequester())).forEach(trade -> {
            if(!trade.isAllowClick() || event.getClick().equals(ClickType.DOUBLE_CLICK)) {
                //duplication protection
                event.setCancelled(true);
                return;
            }
            trade.clickItem(player, clickInventory, event.getCurrentItem(), event);
        });
    }

    //canceled trade when player close tradeInventory
    public void playerCloseTradeInventory(Player player, Inventory closedInventory, InventoryCloseEvent.Reason closeReason) {
        if(!closeReason.equals(InventoryCloseEvent.Reason.PLUGIN)) return;
        Trade canceledTrade = null;
        for(Trade trade : currentTrades) {
            if(!trade.getInventory().equals(closedInventory)) return;
            if(!trade.isAllowClick() || player.equals(trade.getRecipient()) || player.equals(trade.getRequester())) canceledTrade = trade;
        }
       cancelTrade(canceledTrade);
    }

    //canceled trade when player quit server
    public void playerQuit(Player quitPlayer) {
        Trade canceledTrade = null;
        for(Trade trade : currentTrades) {
            if(trade.getRecipient().equals(quitPlayer) || trade.getRequester().equals(quitPlayer)) canceledTrade = trade;
        }
        cancelTrade(canceledTrade);
    }

    //canceled drag and drop feature in tradeInventory
    public void canceledDrag(Player player, Inventory inventory, InventoryDragEvent event) {
        currentTrades.stream().filter(trade -> trade.getInventory().equals(inventory)).forEach(trade -> {
            event.setCancelled(true);
            player.sendMessage("Während eines Trades ist dieses feature deaktiviert");
        });
    }

    private void cancelTrade(Trade canceledTrade) {
        if(canceledTrade == null) return;
        currentTrades.remove(canceledTrade);
        canceledTrade.playerQuitTrade();
    }

    private void sendClickableRequest(Player recipient, Player requester) {
        Component message = Component.text("Du hast eine Tradeanfrage von ").append(Component.text(requester.getName()).color(TextColor.color(0x00FF00))).append(Component.text(" erhalten. "));
        Component accept = Component.text("[ANNEHMEN]").color(TextColor.color(0x00FF00)).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/trade accept " + requester.getName())).hoverEvent(HoverEvent.hoverEvent(HoverEvent.Action.SHOW_TEXT, Component.text("Klicke hier, um die Anfrage von " + requester.getName() + " anzunehmen")));
        Component deny = Component.text("[ABLEHNEN]").color(TextColor.color(0xFF0000)).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/trade deny " + requester.getName())).hoverEvent(HoverEvent.hoverEvent(HoverEvent.Action.SHOW_TEXT, Component.text("Klicke hier, um die Anfrage von " + requester.getName() + " abzulehnen")));
        message = message.append(accept ).append(deny);
        recipient.sendMessage(message);
    }

    private boolean hasRequested(Player requester) {
        return tradeMap.containsKey(requester.getUniqueId());
    }
}
