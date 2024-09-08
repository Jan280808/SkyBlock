package de.jan.skyblock.island;

import de.jan.skyblock.location.Locations;
import de.jan.skyblock.player.SkyPlayer;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.text.SimpleDateFormat;
import java.util.*;

@Getter
public class Island implements Locations {

    private final int id;
    private final UUID owner;
    private final Location center;
    private final World world;
    private final List<UUID> members;
    private final IslandLevel islandLevel;
    private final String createDate;

    //create completely new island
    public Island(int id, UUID owner, Location center)  {
        this.id = id;
        this.owner = owner;
        this.center = center;
        this.world = center.getWorld();
        this.members = new ArrayList<>();
        this.islandLevel = new IslandLevel(this);
        this.createDate = new SimpleDateFormat("dd,MM,yy").format(new Date());
    }

    //load island from .json
    public Island(int id, UUID owner, Location center, List<UUID> members, String islandLevel, String createDate) {
        this.id = id;
        this.owner = owner;
        this.center = center;
        this.world = center.getWorld();
        this.members = members;
        this.islandLevel = new IslandLevel(this, islandLevel);
        this.createDate = createDate;
    }

    public void teleport(SkyPlayer skyPlayer) {
        skyPlayer.getPlayer().teleport(center);
        skyPlayer.setCurrentLocation(this);
    }

    @Override
    public String locationName() {
        return Objects.requireNonNull(Bukkit.getPlayer(owner)).getName() + "-island";
    }
}
