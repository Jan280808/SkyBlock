package de.jan.skyblock.trade;

import de.jan.skyblock.SkyBlock;
import de.jan.skyblock.builder.ItemBuilder;
import de.jan.skyblock.component.ComponentSerializer;
import de.jan.skyblock.sound.SoundManager;
import de.jan.skyblock.sound.Sounds;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

@Getter
public class Trade {

    private final Player requester;
    private final Player recipient;
    private final Inventory inventory;

    private boolean allowClick;
    private final AcceptStatus requestStatus;
    private final AcceptStatus recipientStatus;

    private int taskID;
    private boolean runAnimation;

    public Trade(Player requester, Player recipient) {
        this.requester = requester;
        this.recipient = recipient;
        this.inventory = Bukkit.createInventory(null, 54, ComponentSerializer.deserialize("Handel: " + requester.getName() + " - " + recipient.getName()));
        this.requestStatus = new AcceptStatus(requester, inventory, 47);
        this.recipientStatus = new AcceptStatus(recipient, inventory, 51);
        setupInventory();
        requester.openInventory(inventory);
        recipient.openInventory(inventory);
        allowClick = true;
    }

    public void playerQuitTrade() {
        allowClick = false;
        requester.closeInventory(InventoryCloseEvent.Reason.PLAYER);
        recipient.closeInventory(InventoryCloseEvent.Reason.PLAYER);
    }

    public void clickItem(Player clicker, Inventory clickInventory, ItemStack clickItem, InventoryClickEvent event) {
        if(!clickInventory.equals(inventory)) return;
        if(!allowClick) return;
        if(clickTradeItems(clicker, event.getSlot())) changeOffer(clicker);
        else event.setCancelled(true);
        if(clickItem == null) return;
        if(clicker.equals(requester)) requestStatus.clickItem(clickItem);
        if(clicker.equals(recipient)) recipientStatus.clickItem(clickItem);
        checkStatus();
    }

    //when player change the offer, his status will be reset
    private void changeOffer(Player player) {
        if(player.equals(requester)) requestStatus.reset();
        if(player.equals(recipient)) recipientStatus.reset();
    }

    int[] allowedSlotsRequester = {10, 11, 12, 19, 20, 21, 28, 29, 30, 37, 38, 39};
    int[] allowedSlotsRecipient = {14, 15, 16, 23, 24, 25, 32, 33, 34, 41, 42, 43};
    private boolean clickTradeItems(Player clicker, int clickSlot) {
        if(clicker.equals(requester) && Arrays.stream(allowedSlotsRequester).anyMatch(slot -> slot == clickSlot)) return true;
        return clicker.equals(recipient) && Arrays.stream(allowedSlotsRecipient).anyMatch(slot -> slot == clickSlot);
    }

    private void checkStatus() {
        if(recipientStatus.isAccepted() && requestStatus.isAccepted()) runFinishAnimation();
        else cancelFinishAnimation();
    }

    int timer = 3;
    ItemStack finishStack = new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).build();
    private void runFinishAnimation() {
        if(runAnimation) return;
        runAnimation = true;
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(SkyBlock.instance, () -> {
            checkStatus();
            switch (timer) {
                case 3: {
                    inventory.setItem(4, finishStack);
                    inventory.setItem(49, finishStack);
                    break;
                }
                case 2: {
                    inventory.setItem(13, finishStack);
                    inventory.setItem(40, finishStack);
                    break;
                }
                case 1: {
                    inventory.setItem(22, finishStack);
                    inventory.setItem(31, finishStack);
                    break;
                }
                case 0: {
                    transferItems();
                    endTrade();
                    Bukkit.getScheduler().cancelTask(taskID);
                    break;
                }
            }
            SoundManager.playSound(Sounds.TRADE_LOADING, requester, recipient);
            timer--;
        },0 , 20);
    }

    private void cancelFinishAnimation() {
        if(!runAnimation) return;
        Bukkit.getScheduler().cancelTask(taskID);
        runAnimation = false;
        for(int i = 4; i <= 49; i += 9) {
            inventory.setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName(" ").build());
        }
        timer = 3;
    }


    private void transferItems() {
        for(int slot : allowedSlotsRequester) {
            ItemStack itemStack = inventory.getItem(slot);
            if(itemStack != null) recipient.getInventory().addItem(itemStack);
        }
        for(int slot : allowedSlotsRecipient) {
            ItemStack itemStack = inventory.getItem(slot);
            if(itemStack != null) requester.getInventory().addItem(itemStack);
        }
    }

    private void endTrade() {
        allowClick = false;
        requester.closeInventory(InventoryCloseEvent.Reason.PLUGIN);
        recipient.closeInventory(InventoryCloseEvent.Reason.PLUGIN);
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
        inventory.setItem(requestStatus.slot, requestStatus.getNotYetAcceptedStack());
        inventory.setItem(recipientStatus.slot, recipientStatus.getNotYetAcceptedStack());
    }

    @Getter @Setter
    public static class AcceptStatus {

        private final Player player;
        private final Inventory inventory;
        private final int slot;
        private boolean accepted;
        private final ItemStack notYetAcceptedStack;
        private final ItemStack acceptedStack;

        public AcceptStatus(Player player, Inventory inventory, int slot) {
            this.player = player;
            this.inventory = inventory;
            this.slot = slot;
            this.accepted = false;
            this.notYetAcceptedStack = new ItemBuilder(Material.WHITE_CONCRETE).setDisplayName("<gray>Noch nicht Akzeptiert").setLore("<gray>Klick um den Handel zu <green>akzeptieren").build();
            this.acceptedStack = new ItemBuilder(Material.LIME_CONCRETE).setDisplayName("<green>Handel Akzeptiert").build();
        }

        public void clickItem(ItemStack clickItem) {
            if(clickItem.equals(acceptedStack)) return;
            if(clickItem.equals(notYetAcceptedStack)) {
                accepted = true;
                inventory.setItem(slot, acceptedStack);
                SoundManager.playSound(Sounds.TRADE_ACCEPT, player);
            }
        }

        public void reset() {
            accepted = false;
            inventory.setItem(slot, notYetAcceptedStack);
            SoundManager.playSound(Sounds.TRADE_RESET, player);
        }
    }
}
