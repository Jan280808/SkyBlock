package de.jan.skyblock.player;

import de.jan.skyblock.island.Island;
import de.jan.skyblock.player.level.Level;
import de.jan.skyblock.player.level.MiningLevel;
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

    private Level[] levels;
    private MiningLevel miningLevel;

    public SkyPlayer(UUID uuid, Island island) {
        this.uuid = uuid;
        this.island = island;
        this.currentLocation = new UnknownLocation();

        this.levels = new Level[1];
        levels[0] = this.miningLevel = new MiningLevel(this);
    }

    public boolean teleportToIsland() {
        if(island == null) return false;
        island.teleport(this);
        return true;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    public boolean isOnline() {
        return getPlayer().isOnline();
    }

    public boolean isOnIsland() {
        if(island == null) return false;
        return currentLocation == island;
    }

    public static class UnknownLocation implements Locations {

        @Override
        public String locationName() {
            return "unKnown";
        }
    }
}
