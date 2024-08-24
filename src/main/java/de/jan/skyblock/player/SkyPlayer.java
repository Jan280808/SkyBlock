package de.jan.skyblock.player;

import de.jan.skyblock.island.Island;
import de.jan.skyblock.location.Locations;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

@Getter @Setter
public class SkyPlayer {

    private final UUID uuid;
    private Island island;
    private Locations currentLocation;

    public SkyPlayer(UUID uuid, Island island) {
        this.uuid = uuid;
        this.island = island;
    }

    public boolean teleportToIsland() {
        if(island == null) {
            getPlayer().sendMessage("Du hast noch keine Insel erstellt");
            return false;
        }
        island.teleport(this);
        return true;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }
}
