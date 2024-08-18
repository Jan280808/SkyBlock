package de.jan.skyblock.spawn;

import de.jan.skyblock.player.SkyPlayer;
import org.bukkit.Location;

public class SpawnIsland {

    private final Location location;

    public SpawnIsland() {
        this.location = readJson();
    }

    public void teleport(SkyPlayer skyPlayer) {
        if(location == null) return;
        skyPlayer.getPlayer().teleport(location);
    }

    private Location readJson() {
        return null;
    }

}
