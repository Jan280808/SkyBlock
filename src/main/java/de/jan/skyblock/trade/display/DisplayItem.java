package de.jan.skyblock.trade.display;

import de.jan.skyblock.builder.ItemBuilder;
import de.jan.skyblock.component.ComponentSerializer;
import de.jan.skyblock.trade.TradeManager;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@Getter
public class DisplayItem {

    private final Player player;
    private final DisplayCode displayCode;
    private final Inventory inventory;
    private final ItemStack tpaStack;
    private final ItemStack tradeStack;
    private final long createTime;

    public DisplayItem(Player player, DisplayCode displayCode, ItemStack offerStack) {
        this.player = player;
        this.displayCode = displayCode;
        this.inventory = Bukkit.createInventory(player, 27, ComponentSerializer.deserialize("<gray>Display von " + player.getName()));
        this.tpaStack = new ItemBuilder(Material.ENDER_PEARL).setDisplayName("<gray>Sende dem Spieler eine TPA-Anfrage").build();
        this.tradeStack = new ItemBuilder(Material.EMERALD).setDisplayName("<gray>Sende dem Spieler eine Trade-Anfrage").build();
        this.createTime = System.currentTimeMillis();
        setupInventory(offerStack);
    }

    public void openInventory(Player player) {
        player.openInventory(inventory);
    }

    public boolean isOlderThanFiveMinutes() {
        long currentTime = System.currentTimeMillis();
        return (currentTime - createTime) > 5 * 60 * 1000;
    }

    public Component requiredTime() {
        long remainingTimeMillis = getRemainingTime();
        long minutes = remainingTimeMillis / 1000 / 60;
        long seconds = (remainingTimeMillis / 1000) % 60;
        String timeMessage = String.format("<red>Warte noch %d Minuten und %d Sekunden, bis du ein weiteres Display erstellen kannst...", minutes, seconds);
        return TradeManager.Prefix.append(ComponentSerializer.deserialize(timeMessage));
    }

    private void setupInventory(ItemStack offerStack) {
        inventory.setItem(10, tpaStack);
        inventory.setItem(13, offerStack);
        inventory.setItem(16, tradeStack);
    }

    private long getRemainingTime() {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - createTime;
        long fiveMinutesInMillis = 5 * 60 * 1000;
        return Math.max(0, fiveMinutesInMillis - elapsedTime);
    }
}
