package de.jan.skyblock.island;

import lombok.Getter;
import org.bukkit.Location;
import java.util.UUID;

@Getter
public class Island {

    private final UUID owner;
    private final Location center;
    private final int id;
    private int maxRadius;

    public Island(UUID owner, int id, Location center) {
        this.owner = owner;
        this.id = id;
        this.center = center;
        this.maxRadius = 50;
    }

    public void teleport() {

    }
}
