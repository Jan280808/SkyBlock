package de.jan.skyblock.player;

import de.jan.skyblock.island.Island;
import de.jan.skyblock.player.level.type.FishingLevel;
import de.jan.skyblock.player.level.type.KillEntityLevel;
import de.jan.skyblock.player.level.Level;
import de.jan.skyblock.player.level.type.LumberJackLevel;
import de.jan.skyblock.player.level.type.MiningLevel;
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
    private KillEntityLevel killEntity;
    private FishingLevel fishingLevel;
    private LumberJackLevel lumberJackLevel;

    public SkyPlayer(UUID uuid, Island island) {
        this.uuid = uuid;
        this.island = island;
        this.currentLocation = new UnknownLocation();

        this.levels = new Level[4];
        levels[0] = this.miningLevel = new MiningLevel(this);
        levels[1] = this.killEntity = new KillEntityLevel(this);
        levels[2] = this.fishingLevel = new FishingLevel(this);
        levels[3] = this.lumberJackLevel = new LumberJackLevel(this);
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
