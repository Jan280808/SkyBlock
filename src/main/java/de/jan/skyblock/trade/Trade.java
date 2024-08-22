package de.jan.skyblock.trade;

import de.jan.skyblock.builder.ItemBuilder;
import de.jan.skyblock.component.ComponentSerializer;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

@Getter
public class Trade {

    private final Player requester;
    private final Player recipient;
    private final Inventory inventory;
    private final ItemStack accept;
    private final ItemStack deny;
    private boolean allowClick;

    public Trade(Player requester, Player recipient) {
        this.requester = requester;
        this.recipient = recipient;
        this.inventory = Bukkit.createInventory(null, 54, ComponentSerializer.deserialize("Trade"));
        this.accept = new ItemBuilder(Material.GREEN_DYE).setDisplayName("finish trade").build();
        this.deny = new ItemBuilder(Material.RED_DYE).setDisplayName("cancel trade").build();
        setupInventory();
        requester.openInventory(inventory);
        recipient.openInventory(inventory);
        allowClick = true;
    }

    public void cancelTrade() {
        allowClick = false;
        requester.closeInventory(InventoryCloseEvent.Reason.PLUGIN);
        recipient.closeInventory(InventoryCloseEvent.Reason.PLUGIN);
    }

    public void clickItem(Player clicker, ItemStack clickItem, int slot) {
        if(!allowClick) return;
        if(clicker.equals(requester)) {
            if(slot == 45 && clickItem.equals(deny)) cancelTrade();
            if(slot == 46 && clickItem.equals(accept)) acceptTrade();
        }
        if(clicker.equals(recipient)) {
            if(slot == 52 && clickItem.equals(deny)) cancelTrade();
            if(slot == 53 && clickItem.equals(accept)) acceptTrade();
        }
    }

    int[] allowedSlotsRequester = {10, 11, 12, 19, 20, 21, 28, 29, 30, 37, 38, 39};
    int[] allowedSlotsRecipient = {14, 15, 16, 23, 24, 25, 32, 33, 34, 41, 42, 43};
    public boolean clickTradeItems(Player clicker, int clickSlot) {
        if(clicker.equals(requester) && Arrays.stream(allowedSlotsRequester).anyMatch(slot -> slot == clickSlot)) return true;
        if(clicker.equals(recipient) && Arrays.stream(allowedSlotsRecipient).anyMatch(slot -> slot == clickSlot)) return true;
        else return false;
    }

    private void acceptTrade() {
        allowClick = false;
        for(int slot : allowedSlotsRequester) {
            ItemStack itemStack = inventory.getItem(slot);
            if(itemStack != null) recipient.getInventory().addItem(itemStack);
        }
        for(int slot : allowedSlotsRecipient) {
            ItemStack itemStack = inventory.getItem(slot);
            if(itemStack != null) requester.getInventory().addItem(itemStack);
        }
        cancelTrade();
    }

    private void setupInventory() {
        ItemStack item = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName(" ").build();
        for (int i = 0; i < 36; i++) {
            int slot;
            if(i < 9) slot = i;
            else if(i < 14) slot = (i - 9) * 9 + 9;
            else if(i < 18) slot = (i - 14) * 9 + 17;
            else if(i < 27) slot = i + 27;
            else slot = 13 + (i - 27) * 9;
            if(slot < 54) inventory.setItem(slot, item);
        }
        inventory.setItem(2, new ItemBuilder(Material.PLAYER_HEAD).setDisplayName("Requester: " + requester.getName()).build());
        inventory.setItem(6, new ItemBuilder(Material.PLAYER_HEAD).setDisplayName("Recipient: " + recipient.getName()).build());
        inventory.setItem(45, deny);
        inventory.setItem(46, accept);
        inventory.setItem(52, deny);
        inventory.setItem(53, accept);
    }
}
