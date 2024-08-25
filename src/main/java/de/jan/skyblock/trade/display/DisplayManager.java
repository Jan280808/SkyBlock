package de.jan.skyblock.trade.display;

import de.jan.skyblock.component.ComponentSerializer;
import de.jan.skyblock.sound.SoundManager;
import de.jan.skyblock.sound.Sounds;
import de.jan.skyblock.trade.TradeManager;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.*;

@Getter
public class DisplayManager {

    private final TradeManager tradeManager;
    private final Map<UUID, DisplayItem> displayItemMap;

    public DisplayManager(TradeManager tradeManager) {
        this.tradeManager = tradeManager;
        this.displayItemMap = new HashMap<>();
    }

    public void openDisplay(Player player, Player displayCreator) {
        UUID creatorUUID = displayCreator.getUniqueId();
        if(player.equals(displayCreator)) return;
        if(!displayItemMap.containsKey(creatorUUID)) {
            player.sendMessage(TradeManager.Prefix.append(ComponentSerializer.deserialize("<red>Der Spieler <gray>" + displayCreator.getName() + " <red>hat kein offenen Display")));
            SoundManager.playSound(Sounds.ERROR, player);
            return;
        }
        DisplayItem displayItem = displayItemMap.get(creatorUUID);
        displayItem.openInventory(player);
    }

    @Deprecated
    public void createDisplay(Player player, String chatMassage, AsyncPlayerChatEvent event) {
        Arrays.stream(DisplayCode.values()).filter(displayCode -> chatMassage.equalsIgnoreCase(displayCode.getString())).findFirst().ifPresent(displayCode -> {
            PlayerInventory inventory = player.getInventory();
            if(inventory.getItemInMainHand().getType().equals(Material.AIR)) return;
            if(displayCode == DisplayCode.MAIN_HAND) {
                createDisplay(player, displayCode, inventory.getItemInMainHand());
                event.setCancelled(true);
            }
        });
    }

    public void clickInventory(Player player, Inventory clickInventory, ItemStack clickItem, InventoryClickEvent event) {
        displayItemMap.values().stream().filter(displayItem -> displayItem.getInventory().equals(clickInventory)).forEach(displayItem -> {
            event.setCancelled(true);
            SoundManager.playSound(Sounds.ERROR, (Player) event.getWhoClicked());
            if(clickItem == null) return;
            if(clickItem.equals(displayItem.getTpaStack())) player.sendMessage("send tpa...");
            if(clickItem.equals(displayItem.getTradeStack())) {
                tradeManager.sendRequest(player, displayItem.getPlayer());
                player.closeInventory();
            }
        });
    }

    private void createDisplay(Player player, DisplayCode displayCode, ItemStack itemStack) {
        UUID uuid = player.getUniqueId();
        if(displayItemMap.containsKey(uuid)) {
            DisplayItem displayItem = displayItemMap.get(uuid);
            if(!displayItem.isOlderThanFiveMinutes()) {
                player.sendMessage(displayItem.requiredTime());
                SoundManager.playSound(Sounds.ERROR, player);
                return;
            }
            displayItemMap.remove(uuid);
        }
        DisplayItem displayItem = new DisplayItem(player, displayCode, itemStack);
        displayItemMap.put(uuid, displayItem);
        Component playerComponent = ComponentSerializer.deserialize("<gray>" + player.getName() + " hat ein Display erstellt").hoverEvent(HoverEvent.showText(Component.text("Klicke hier, um das Display von " + player.getName() + " anzusehen"))).clickEvent(ClickEvent.runCommand("/display " + player.getName()));
        Component itemComponent = ComponentSerializer.deserialize("<gray>Name: ").append(itemStack.displayName()).append(ComponentSerializer.deserialize(" <gray>Material: " + itemStack.getType())).hoverEvent(HoverEvent.showText(Component.text("Klicke hier, um das Display von " + player.getName() + " anzusehen"))).clickEvent(ClickEvent.runCommand("/display " + player.getName()));
        Bukkit.broadcast(TradeManager.Prefix.append(playerComponent));
        Bukkit.broadcast(TradeManager.Prefix.append(itemComponent));
        SoundManager.playSound(Sounds.SUCCESSES, player);
    }
}
