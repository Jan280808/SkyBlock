package de.jan.skyblock.island;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

import java.util.UUID;

@Getter
public class Island {

    private final UUID owner;
    private final Location center;
    private final int id;

    public Island(UUID owner, int id, Location center) {
        this.owner = owner;
        this.id = id;
        this.center = center;
    }

    public void teleport() {

    }
}
