package de.jan.skyblock.player;

import de.jan.skyblock.island.Island;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

@Getter @Setter
public class SkyPlayer {

    private final UUID uuid;
    private Island island;

    public SkyPlayer(UUID uuid, Island island) {
        this.uuid = uuid;
        this.island = island;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }
}
