package de.jan.skyblock.spawn;

import de.jan.skyblock.event.custom.SkyPlayerQuitIslandEvent;
import de.jan.skyblock.island.IslandManager;
import de.jan.skyblock.location.Locations;
import de.jan.skyblock.player.SkyPlayer;
import de.jan.skyblock.sound.SoundManager;
import de.jan.skyblock.sound.Sounds;
import de.jan.skyblock.spawn.island.IslandWarden;
import de.jan.skyblock.spawn.pinata.Pinata;
import de.jan.skyblock.spawn.shop.ShopManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;

@Getter
public class SpawnIsland implements Locations {

    private final World world;
    private final ShopManager shopManager;
    private final Pinata pinata;
    private final IslandWarden warden;

    public SpawnIsland(IslandManager islandManager) {
        this.world = Bukkit.createWorld(new WorldCreator("world"));
        this.shopManager = new ShopManager();
        this.pinata = new Pinata(new Location(Bukkit.getWorld("world"), 19, 101, 21, 90, 0));
        this.warden = new IslandWarden(islandManager);
        assert world != null;
        world.setStorm(false);
        world.setThundering(false);
    }

    public void teleport(SkyPlayer skyPlayer) {
        skyPlayer.getPlayer().teleport(world.getSpawnLocation());
        skyPlayer.setCurrentLocation(this);
        SoundManager.playSound(Sounds.TELEPORT, skyPlayer);
        new SkyPlayerQuitIslandEvent(skyPlayer);
    }

    @Override
    public String locationName() {
        return "spawnIsland";
    }
}
