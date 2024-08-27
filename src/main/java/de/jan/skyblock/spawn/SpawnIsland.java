package de.jan.skyblock.spawn;

import de.jan.skyblock.location.Locations;
import de.jan.skyblock.player.SkyPlayer;
import de.jan.skyblock.sound.SoundManager;
import de.jan.skyblock.sound.Sounds;
import de.jan.skyblock.spawn.shop.Shop;
import de.jan.skyblock.spawn.shop.ShopManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;

@Getter
public class SpawnIsland implements Locations {

    private final Location location;
    private final World world;
    private final ShopManager shopManager;
    private final Shop shop;

    public SpawnIsland() {
        this.location = readJson();
        this.world = location.getWorld();
        this.shopManager = new ShopManager();
        this.shop = shopManager.createShop("Blöcke", new ArrayList<>());
        world.setStorm(false);
        world.setThundering(false);
    }

    public void teleport(SkyPlayer skyPlayer) {
        if(location == null) return;
        skyPlayer.getPlayer().teleport(location);
        skyPlayer.setCurrentLocation(this);
        SoundManager.playSound(Sounds.TELEPORT, skyPlayer);
    }

    private Location readJson() {
        return new Location(Bukkit.getWorld("world"), 0, 100, 10, 180, 0);
    }

    @Override
    public String locationName() {
        return "spawnIsland";
    }
}
