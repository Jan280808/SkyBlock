package de.jan.skyblock.player;

import de.jan.skyblock.island.Island;
import de.jan.skyblock.player.stats.type.FishingStats;
import de.jan.skyblock.player.stats.type.KillHostileStats;
import de.jan.skyblock.player.stats.Stats;
import de.jan.skyblock.player.stats.type.LumberJackStats;
import de.jan.skyblock.player.stats.type.MiningStats;
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

    private Stats[] stats;
    private MiningStats miningStats;
    private KillHostileStats killEntityStats;
    private FishingStats fishingStats;
    private LumberJackStats lumberJackStats;

    public SkyPlayer(UUID uuid, Island island) {
        this.uuid = uuid;
        this.island = island;
        this.currentLocation = new UnknownLocation();

        this.stats = new Stats[4];
        stats[0] = this.miningStats = new MiningStats(this);
        stats[1] = this.killEntityStats = new KillHostileStats(this);
        stats[2] = this.fishingStats = new FishingStats(this);
        stats[3] = this.lumberJackStats = new LumberJackStats(this);
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
        return currentLocation != island;
    }

    public static class UnknownLocation implements Locations {

        @Override
        public String locationName() {
            return "unKnown";
        }
    }
}
