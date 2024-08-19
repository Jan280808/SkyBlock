package de.jan.skyblock.spawn;

import de.jan.skyblock.player.SkyPlayer;
import de.jan.skyblock.spawn.shop.ShopManager;
import lombok.Getter;
import org.bukkit.Location;

@Getter
public class SpawnIsland {

    private final Location location;
    private final ShopManager shopManager;

    public SpawnIsland() {
        this.location = readJson();
        this.shopManager = new ShopManager();
    }

    public void teleport(SkyPlayer skyPlayer) {
        if(location == null) return;
        skyPlayer.getPlayer().teleport(location);
    }

    private Location readJson() {
        return null;
    }

}
