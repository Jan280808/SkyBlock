package de.jan.skyblock.spawn.shop;

import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Getter
public class Shop {

    private final Component shopName;
    private final Inventory inventory;
    private final List<ShopItem> shopItems;

    public Shop(Component shopName, @NotNull List<ShopItem> shopItems) {
        this.shopName = shopName;
        this.inventory = Bukkit.createInventory(null, 54, this.shopName);
        this.shopItems = shopItems;
        setShopItems();
    }

    public void clickItem(Player player, ItemStack clickItem) {
        shopItems.stream().filter(shopItem -> shopItem.required().equals(clickItem)).forEach(shopItem -> {
            player.sendMessage("click");
        });
    }

    public void openShop(Player player) {
        player.openInventory(inventory);
    }

    private void setShopItems() {
        if(shopItems.isEmpty()) return;
        shopItems.forEach(shopItem -> inventory.addItem(shopItem.required()));
    }
}
