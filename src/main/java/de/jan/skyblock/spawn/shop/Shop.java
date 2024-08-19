package de.jan.skyblock.spawn.shop;

import de.jan.skyblock.player.SkyPlayer;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Getter
public class Shop {

    private final Component shopName;
    private final Inventory inventory;
    private final List<ShopItem> shopItems;

    public Shop(Component shopName, List<ShopItem> shopItems) {
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

    public void openShop(SkyPlayer skyPlayer) {
        skyPlayer.getPlayer().openInventory(inventory);
    }

    private void setShopItems() {
        shopItems.forEach(shopItem -> inventory.addItem(shopItem.required()));
    }
}
