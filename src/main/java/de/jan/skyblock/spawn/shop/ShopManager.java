package de.jan.skyblock.spawn.shop;

import de.jan.skyblock.component.ComponentSerializer;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ShopManager {

    private final List<Shop> shopList;

    public ShopManager() {
        this.shopList = new ArrayList<>();
    }

    public Shop createShop(String shopName, List<ShopItem> shopItems) {
        Shop shop = new Shop(ComponentSerializer.deserialize(shopName), shopItems);
        shopList.add(shop);
        return shop;
    }

    public void clickShopInventory(Inventory inventory, ItemStack clickItem, Player player) {
        shopList.stream().filter(shop -> shop.getInventory().equals(inventory)).forEach(shop -> shop.clickItem(player, clickItem));
    }
}
