package de.jan.skyblock.spawn;

import de.jan.skyblock.SkyBlock;
import de.jan.skyblock.npc.Type;
import de.jan.skyblock.player.SkyPlayer;
import de.jan.skyblock.spawn.shop.Shop;
import de.jan.skyblock.spawn.shop.ShopManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;

@Getter
public class SpawnIsland {

    private final Location location;
    private final ShopManager shopManager;
    private final Shop shop;

    public SpawnIsland() {
        this.location = readJson();
        this.shopManager = new ShopManager();
        this.shop = shopManager.createShop("Blöcke", new ArrayList<>());
        createShopNPC();
    }

    public void teleport(SkyPlayer skyPlayer) {
        if(location == null) return;
        skyPlayer.getPlayer().teleport(location);
    }

    private Location readJson() {
        return new Location(Bukkit.getWorld("world"), 0, 100, 10, 180, 0);
    }

    private void createShopNPC() {
        SkyBlock.instance.getNpcManager().createNPC("items", EntityType.VILLAGER, location, Type.SHOP, event -> {
            shop.openShop(event.getPlayer());
        });
    }

}
