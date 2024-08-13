package de.jan.skyblock.island;

import de.jan.skyblock.player.SkyPlayer;
import lombok.Getter;
import org.bukkit.Location;
import java.util.UUID;

@Getter
public class Island {

    private final int id;
    private final UUID owner;
    private final Location center;
    private int maxRadius;

    public Island(int id, UUID owner, Location center) {
        this.id = id;
        this.owner = owner;
        this.center = center;
        this.maxRadius = 50;
    }

    public void teleport(SkyPlayer skyPlayer) {
        skyPlayer.getPlayer().teleport(center);
    }
}
